package nsu.sber.web.mapper;

import nsu.sber.domain.model.DishInfoResponse;
import nsu.sber.domain.model.MenuResponse;
import nsu.sber.web.dto.DishInfoResponseDto;
import nsu.sber.web.dto.MenuResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DishDtoMapper {

    MenuResponseDto menuResponseToDto(MenuResponse menuResponse);

    DishInfoResponseDto dishInfoResponseToDto(DishInfoResponse dishInfoResponse);

}
