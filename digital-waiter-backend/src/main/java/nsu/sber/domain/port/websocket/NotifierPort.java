package nsu.sber.domain.port.websocket;

import java.util.List;

public interface NotifierPort {
    void notifyStopListUpdated(List<String> userLogins);

    void notifyOrderStatus(String userLogin, String orderId, String status);

    void notifyError(List<String> userLogins, String message, String reason);
}
