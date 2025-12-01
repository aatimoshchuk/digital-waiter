package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.config.RequestContextProvider;
import nsu.sber.domain.model.operation.OperationState;
import nsu.sber.domain.model.operation.OperationStatusRequest;
import nsu.sber.domain.model.operation.OperationStatusResponse;
import nsu.sber.domain.port.pos.PosOperationPort;
import nsu.sber.exception.DigitalWaiterException;
import nsu.sber.infrastructure.WebSocketNotifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final CartService cartService;
    private final PosOperationPort posOperationPort;
    private final RequestContextProvider requestContextProvider;
    private final WebSocketNotifier webSocketNotifier;
    private final TaskScheduler taskScheduler;

    @Value("${iiko.operation.status-check.max-attempts}")
    private int statusCheckMaxAttempts;

    @Value("${iiko.operation.status-check.delay-ms}")
    private int statusCheckDelayMs;

    public void trackOrderStatusAsync(String correlationId) {
        scheduleStatusCheck(
                correlationId,
                requestContextProvider.getTableId(),
                requestContextProvider.getOrganizationId(),
                0
        );
    }

    private void scheduleStatusCheck(String correlationId, String tableId, String organizationId, int attempt) {
        taskScheduler.schedule(() -> checkStatus(correlationId, tableId, organizationId, attempt),
                Instant.now().plusMillis(statusCheckDelayMs));
    }

    private void checkStatus(String correlationId, String tableId, String organizationId, int attempt) {
        try {
            OperationStatusRequest request = buildOperationStatusRequest(
                    correlationId,
                    organizationId
            );

            OperationStatusResponse status = posOperationPort.getOperationStatus(request);

            if (status.getState() == OperationState.IN_PROGRESS && attempt < statusCheckMaxAttempts) {
                scheduleStatusCheck(correlationId, tableId, organizationId, attempt + 1);
            } else {
                handleFinalStatus(correlationId, tableId, status);
            }
        } catch (Exception e) {
            webSocketNotifier.notifyStatus(
                    correlationId,
                    OperationState.ERROR.getExternalValue(),
                    e.getMessage()
            );

            throw new DigitalWaiterException.OrderCreationException(e.getMessage());
        }
    }

    private void handleFinalStatus(String correlationId, String tableId, OperationStatusResponse status) {
        webSocketNotifier.notifyStatus(
                correlationId,
                status.getState().getExternalValue(),
                status.getExceptionMessage());

        if (status.getState() == OperationState.SUCCESS) {
            cartService.clearCartByTableId(tableId);
        }
    }

    private OperationStatusRequest buildOperationStatusRequest(String correlationId, String organizationId) {
        return OperationStatusRequest
                .builder()
                .correlationId(correlationId)
                .organizationId(organizationId)
                .build();
    }
}
