package nsu.sber.infrastructure.websocket;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.port.websocket.OperationStatusNotifierPort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketOperationNotifierAdapter implements OperationStatusNotifierPort {
    private final WebSocketNotifier webSocketNotifier;
    @Override
    public void notifyStatus(String correlationId, String status, String message) {
        webSocketNotifier.notifyStatus(correlationId, status, message);
    }
}
