package nsu.sber.web.mapper;

import nsu.sber.domain.model.menu.MenuItem;
import nsu.sber.domain.model.menu.MenuResponse;
import nsu.sber.web.dto.MenuItemResponseDto;
import nsu.sber.web.dto.MenuResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DishDtoMapper {

    MenuResponseDto menuResponseToDto(MenuResponse menuResponse);

    MenuItemResponseDto menuItemToDishInfoResponseDto(MenuItem menuItem);

    @Mapping(target = "price", expression = "java(extractPrice(itemSize.getPrices()))")
    MenuResponseDto.ItemSize menuItemSizeToMenuResponseItemSize(MenuItem.ItemSize itemSize);

    @Mapping(target = "price", expression = "java(extractPrice(itemSize.getPrices()))")
    MenuItemResponseDto.ItemSize menuItemSizeToDishInfoResponseItemSize(MenuItem.ItemSize itemSize);

    default double extractPrice(List<MenuItem.Price> prices) {
        return prices.get(0).getPrice();
    }

}
