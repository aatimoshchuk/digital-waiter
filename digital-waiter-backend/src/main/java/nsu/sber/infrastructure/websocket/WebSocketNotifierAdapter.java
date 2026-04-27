package nsu.sber.infrastructure.websocket;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.port.websocket.NotifierPort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketNotifierAdapter implements NotifierPort {
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void notifyStopListUpdated(List<String> userLogins) {
        Map<String, String> payload = Map.of("type", "STOP_LIST_UPDATED");

        userLogins.forEach(userLogin ->
                messagingTemplate.convertAndSendToUser(
                        userLogin,
                        "/queue/stop-list",
                        payload
                )
        );
    }

    @Override
    public void notifyOrderStatus(String userLogin, String orderId, String status) {
        messagingTemplate.convertAndSendToUser(
                userLogin,
                "/queue/order-status",
                Map.of(
                        "type", "ORDER_STATUS_CHANGED",
                        "orderId", orderId,
                        "status", status
                )
        );
    }

    @Override
    public void notifyError(String userLogin, String message, String reason) {
        messagingTemplate.convertAndSendToUser(
                userLogin,
                "/queue/errors",
                Map.of(
                        "type", "ERROR",
                        "message", message,
                        "reason", reason
                )
        );
    }
}
