package nsu.sber.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.domain.model.entity.TerminalGroup;
import nsu.sber.domain.model.webhooks.BaseWebHookEvent;
import nsu.sber.domain.model.webhooks.StopListUpdateEventInfo;
import nsu.sber.domain.model.webhooks.StopListUpdateEventInfo.TerminalGroupsStopListsUpdate;
import nsu.sber.domain.model.webhooks.TableOrderEventInfo;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebHookService {
    private final ObjectMapper objectMapper;
    private final TerminalGroupService terminalGroupService;
    private final MenuService menuService;

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
            case UNKNOWN -> log.warn("Unprocessable webhook event type");
        }
    }

    private void handleStopListUpdateEvent(StopListUpdateEventInfo eventInfo) {
        for (TerminalGroupsStopListsUpdate update : eventInfo.getTerminalGroupsStopListsUpdates()) {
            TerminalGroup terminalGroup = terminalGroupService.getTerminalGroupByPosId(update.getId());
            menuService.loadMenu(terminalGroup);

            log.info(
                    "Webhook StopListUpdate for terminal group {} was handled: the menu has been updated",
                    terminalGroup.getPosTerminalGroupId()
            );
        }
    }

    private void handleTableOrderUpdateEvent(TableOrderEventInfo eventInfo) {
        log.info(
                "Order {} was updated: current order status is '{}'",
                eventInfo.getId(),
                eventInfo.getOrder().getStatus()
        );
    }

    private void handleTableOrderErrorEvent(TableOrderEventInfo eventInfo) {
        log.warn(
                "An error '{}' occurs while saving order {} for organization {}",
                eventInfo.getErrorInfo().getCode(),
                eventInfo.getId(),
                eventInfo.getOrganizationId()
        );
    }
}
