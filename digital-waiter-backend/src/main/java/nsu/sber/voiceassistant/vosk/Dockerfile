FROM python:3.10-slim

RUN apt-get update && apt-get install -y \
    wget \
    unzip \
    && rm -rf /var/lib/apt/lists/*

RUN pip install --no-cache-dir \
    vosk \
    websockets \
    aiohttp

WORKDIR /opt

RUN wget https://alphacephei.com/vosk/models/vosk-model-small-ru-0.22.zip && \
    unzip vosk-model-small-ru-0.22.zip && \
    mv vosk-model-small-ru-0.22 vosk-model && \
    rm vosk-model-small-ru-0.22.zip

COPY server.py /app/server.py
WORKDIR /app

EXPOSE 2700 2701

HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
    CMD python -c "import urllib.request; urllib.request.urlopen('http://localhost:2701/health')"

CMD ["python", "-u", "server.py"]