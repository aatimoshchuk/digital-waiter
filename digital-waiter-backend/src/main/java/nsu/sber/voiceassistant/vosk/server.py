#!/usr/bin/env python3

import asyncio
import websockets
import json
import sys
import os
import wave
import logging
from vosk import Model, KaldiRecognizer
from aiohttp import web
import concurrent.futures
import functools

logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

try:
    sys.stdout.reconfigure(encoding='utf-8')
    sys.stderr.reconfigure(encoding='utf-8')
except Exception:
    pass

_json_dumps = functools.partial(json.dumps, ensure_ascii=False)

MODEL_PATH = os.environ.get('VOSK_MODEL_PATH', '/opt/vosk-model')

logger.info(f"Loading model from {MODEL_PATH}")
model = Model(MODEL_PATH)
logger.info("Model loaded successfully")

executor = concurrent.futures.ThreadPoolExecutor(max_workers=4)

async def http_recognize(request):
    try:
        data = await request.read()
        sample_rate = int(request.headers.get('X-Sample-Rate', '16000'))
        loop = asyncio.get_event_loop()
        result = await loop.run_in_executor(
            executor,
            recognize_audio,
            data,
            sample_rate
        )
        return web.json_response(result, dumps=_json_dumps)
    except Exception as e:
        logger.error(f"Error in HTTP recognize: {e}", exc_info=True)
        return web.json_response(
            {'error': str(e)},
            status=500,
            dumps=_json_dumps
        )

def recognize_audio(audio_data, sample_rate=16000):
    rec = KaldiRecognizer(model, sample_rate)
    rec.SetMaxAlternatives(3)
    rec.SetWords(True)

    rec.AcceptWaveform(audio_data)

    result = json.loads(rec.FinalResult())
    alternatives = result.get('alternatives', [])
    if len(alternatives) > 0:
            text = alternatives[0].get('text', '')
            confidence = alternatives[0].get('confidence', 0)
    else:
        text = result.get('text', '')
        confidence = 0

    return {
        'text': text,
        'confidence': confidence,
        'alternatives': alternatives
    }

async def health_check(request):
    return web.json_response({
        'status': 'healthy',
        'model': MODEL_PATH,
        'version': '1.0.0'
    }, dumps=_json_dumps)

async def info(request):
    return web.json_response({
        'name': 'Vosk Speech Recognition Server',
        'version': '1.0.0',
        'model': MODEL_PATH,
        'endpoints': {
            'websocket': 'ws://localhost:2700/ws',
            'http': 'http://localhost:2701/recognize',
            'health': 'http://localhost:2701/health'
        }
    }, dumps=_json_dumps)

app = web.Application()
app.router.add_post('/recognize', http_recognize)
app.router.add_get('/health', health_check)
app.router.add_get('/', info)

async def start_server():
    runner = web.AppRunner(app)
    await runner.setup()
    http_server = web.TCPSite(runner, '0.0.0.0', 2701)
    await http_server.start()

    logger.info("Vosk server started:")
    logger.info("  HTTP: http://0.0.0.0:2701")

    await asyncio.Future()

if __name__ == '__main__':
    try:
        asyncio.run(start_server())
    except KeyboardInterrupt:
        logger.info("Server stopped by user")
    except Exception as e:
        logger.error(f"Server error: {e}", exc_info=True)
        sys.exit(1)