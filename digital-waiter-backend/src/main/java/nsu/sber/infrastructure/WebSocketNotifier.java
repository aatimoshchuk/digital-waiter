package nsu.sber.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketNotifier {
    private static final String CORRELATION_ID = "correlationId";
    private static final String STATUS = "status";
    private static final String MESSAGE = "message";
    private static final String ORDER_STATUS_DESTINATION = "/topic/order-status";

    private final SimpMessagingTemplate messagingTemplate;

    public void notifyStatus(String correlationId, String status, String message) {
        Map<String, String> payload = new HashMap<>();

        payload.put(CORRELATION_ID, correlationId);
        payload.put(STATUS, status);

        if (message != null) payload.put(MESSAGE, message);

        messagingTemplate.convertAndSend(ORDER_STATUS_DESTINATION, payload);
    }

}
