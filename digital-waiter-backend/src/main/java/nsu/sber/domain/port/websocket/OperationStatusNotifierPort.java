package nsu.sber.domain.port.websocket;

public interface OperationStatusNotifierPort {
    void notifyStatus(String correlationId, String status, String message);
}
