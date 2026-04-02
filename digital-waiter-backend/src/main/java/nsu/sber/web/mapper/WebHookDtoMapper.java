package nsu.sber.web.mapper;

import nsu.sber.domain.model.webhooks.BaseWebHookEvent;
import nsu.sber.domain.model.webhooks.WebHookEventType;
import nsu.sber.web.dto.BaseWebHookEventDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface WebHookDtoMapper {
    @Mapping(target = "eventType", source = "eventType", qualifiedByName = "mapEventType")
    BaseWebHookEvent dtoToBaseWebHookEvent(BaseWebHookEventDto baseWebHookEventDto);

    @Named("mapEventType")
    default WebHookEventType mapEventType(String eventType) {
        return WebHookEventType.fromValue(eventType);
    }
}
