package nsu.sber.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.domain.model.entity.TerminalGroup;
import nsu.sber.domain.model.order.OrderStatus;
import nsu.sber.domain.model.webhooks.BaseWebHookEvent;
import nsu.sber.domain.model.webhooks.StopListUpdateEventInfo;
import nsu.sber.domain.model.webhooks.StopListUpdateEventInfo.TerminalGroupsStopListsUpdate;
import nsu.sber.domain.model.webhooks.TableOrderEventInfo;
import nsu.sber.domain.port.websocket.NotifierPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebHookService {
    private final ObjectMapper objectMapper;

    private final NotifierPort notifierPort;

    private final TerminalGroupService terminalGroupService;
    private final CartService cartService;
    private final MenuService menuService;
    private final UserService userService;

    public void processEvent(BaseWebHookEvent event) {
        switch (event.getEventType()) {
            case STOP_LIST_UPDATE -> handleStopListUpdateEvent(objectMapper.convertValue(
                    event.getEventInfo(),
                    StopListUpdateEventInfo.class
            ));
            case TABLE_ORDER_UPDATE -> handleTableOrderUpdateEvent(objectMapper.convertValue(
                    event.getEventInfo(),
                    TableOrderEventInfo.class
            ));
            case TABLE_ORDER_ERROR -> handleTableOrderErrorEvent(objectMapper.convertValue(
                    event.getEventInfo(),
                    TableOrderEventInfo.class
            ));
            case UNKNOWN -> log.warn("WEBHOOK: Unprocessable webhook event type");
        }
    }

    private void handleStopListUpdateEvent(StopListUpdateEventInfo eventInfo) {
        List<String> posTerminalGroupIds = eventInfo.getTerminalGroupsStopListsUpdates()
                .stream()
                .map(TerminalGroupsStopListsUpdate::getId)
                .toList();

        List<TerminalGroup> terminalGroups = terminalGroupService.findAllByPosTerminalGroupIds(posTerminalGroupIds);

        for (TerminalGroup group : terminalGroups) {
            menuService.loadMenu(group);

            log.info(
                    "WEBHOOK: The stop list for terminal group with id = {} has been updated",
                    group.getPosTerminalGroupId()
            );
        }

        notifierPort.notifyStopListUpdated(userService.findLoginsByPosTerminalGroupIds(posTerminalGroupIds));
    }

    private void handleTableOrderUpdateEvent(TableOrderEventInfo eventInfo) {
        String orderId = eventInfo.getId();
        OrderStatus orderStatus = OrderStatus.fromExternal(eventInfo.getOrder().getStatus());
        String tableId = eventInfo.getOrder().getTableIds().get(0);

        log.info("WEBHOOK: Order {} was updated: current order status is '{}'", orderId, orderStatus.toString());

        if (orderStatus == OrderStatus.NEW) {
            cartService.clearCartByTableId(tableId);
        }

        log.info("WEBHOOK: The restaurant table cart with id = {} was successfully cleared", tableId);

        String login = userService.findLoginByPosTerminalGroupIdAndPosRestaurantTableId(
                eventInfo.getOrder().getTerminalGroupId(),
                tableId
        );

        notifierPort.notifyOrderStatus(login, orderId, orderStatus.toString());
    }

    private void handleTableOrderErrorEvent(TableOrderEventInfo eventInfo) {
        log.warn(
                "WEBHOOK: An error '{}' occurs while saving order {} for organization {}",
                eventInfo.getErrorInfo().getMessage(),
                eventInfo.getId(),
                eventInfo.getOrganizationId()
        );

        notifierPort.notifyError(
                userService.findLoginsByPosOrganizationId(eventInfo.getOrganizationId()),
                eventInfo.getErrorInfo().getMessage()
        );
    }
}
