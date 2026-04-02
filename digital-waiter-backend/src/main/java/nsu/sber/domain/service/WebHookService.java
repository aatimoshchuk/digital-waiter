package nsu.sber.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.domain.model.entity.TerminalGroup;
import nsu.sber.domain.model.webhooks.BaseWebHookEvent;
import nsu.sber.domain.model.webhooks.StopListUpdateEventInfo;
import nsu.sber.domain.model.webhooks.StopListUpdateEventInfo.TerminalGroupsStopListsUpdate;
import nsu.sber.domain.model.webhooks.TableOrderUpdateEventInfo;
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
                    TableOrderUpdateEventInfo.class
            ));
            case UNKNOWN -> log.warn("Unknown event type");
        }
    }

    private void handleStopListUpdateEvent(StopListUpdateEventInfo eventInfo) {
        TerminalGroup terminalGroup = terminalGroupService.getCurrentTerminalGroup();

        for (TerminalGroupsStopListsUpdate update : eventInfo.getTerminalGroupsStopListsUpdates()) {
            if (update.getId().equals(terminalGroup.getPosTerminalGroupId())) {
                menuService.loadMenu(terminalGroup);

                log.info(
                        "Webhook StopListUpdate for terminal group {} was handled: the menu has been updated",
                        terminalGroup.getPosTerminalGroupId()
                );

                break;
            }
        }
    }

    private void handleTableOrderUpdateEvent(TableOrderUpdateEventInfo eventInfo) {
        log.info("Order with id = {} was updated", eventInfo.getId());
    }
}
