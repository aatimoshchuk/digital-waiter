package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.RestaurantTable;
import nsu.sber.domain.model.operation.OperationState;
import nsu.sber.domain.model.operation.OperationStatusRequest;
import nsu.sber.domain.model.operation.OperationStatusResponse;
import nsu.sber.domain.port.pos.PosOperationPort;
import nsu.sber.domain.port.websocket.OperationStatusNotifierPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OperationService {
    private final CartService cartService;
    private final RestaurantTableService restaurantTableService;

    private final PosOperationPort posOperationPort;

    private final OperationStatusNotifierPort operationStatusNotifier;
    private final TaskScheduler taskScheduler;

    @Value("${iiko.operation.status-check.max-attempts}")
    private int statusCheckMaxAttempts;

    @Value("${iiko.operation.status-check.delay-ms}")
    private int statusCheckDelayMs;

    public void trackOrderStatusAsync(String correlationId) {
        RestaurantTable restaurantTable = restaurantTableService.getCurrentRestaurantTable();

        scheduleStatusCheck(
                correlationId,
                restaurantTable.getPosTableId(),
                restaurantTable.getTerminalGroup().getOrganization().getPosOrganizationId(),
                0
        );
    }

    private void scheduleStatusCheck(String correlationId, String posTableId, String posOrganizationId, int attempt) {
        taskScheduler.schedule(() -> checkStatus(correlationId, posTableId, posOrganizationId, attempt),
                Instant.now().plusMillis(statusCheckDelayMs));
    }

    private void checkStatus(String correlationId, String posTableId, String posOrganizationId, int attempt) {
        try {
            OperationStatusRequest request = buildOperationStatusRequest(
                    correlationId,
                    posOrganizationId
            );

            OperationStatusResponse status = posOperationPort.getOperationStatus(request);

            if (status.getState() == OperationState.IN_PROGRESS && attempt < statusCheckMaxAttempts) {
                scheduleStatusCheck(correlationId, posTableId, posOrganizationId, attempt + 1);
            } else {
                handleFinalStatus(correlationId, posTableId, status);
            }
        } catch (Exception e) {
            operationStatusNotifier.notifyStatus(
                    correlationId,
                    OperationState.ERROR.getExternalValue(),
                    e.getMessage()
            );

            throw new DigitalWaiterException.OrderCreationException(e.getMessage());
        }
    }

    private void handleFinalStatus(String correlationId, String posTableId, OperationStatusResponse status) {
        operationStatusNotifier.notifyStatus(
                correlationId,
                status.getState().getExternalValue(),
                status.getExceptionMessage());

        if (status.getState() == OperationState.SUCCESS) {
            cartService.clearCartByTableId(posTableId);
        }
    }

    private OperationStatusRequest buildOperationStatusRequest(String correlationId, String posOrganizationId) {
        return OperationStatusRequest
                .builder()
                .correlationId(correlationId)
                .organizationId(posOrganizationId)
                .build();
    }
}
