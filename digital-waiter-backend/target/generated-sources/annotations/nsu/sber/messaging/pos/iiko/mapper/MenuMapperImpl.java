package nsu.sber.messaging.pos.iiko.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import nsu.sber.domain.model.menu.Menu;
import nsu.sber.domain.model.menu.MenuItem;
import nsu.sber.domain.model.menu.MenuRequest;
import nsu.sber.messaging.pos.iiko.dto.MenuRequestDto;
import nsu.sber.messaging.pos.iiko.dto.MenuResponseDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T20:45:23+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class MenuMapperImpl implements MenuMapper {

    @Override
    public Menu menuResponseDtoToMenu(MenuResponseDto menuResponseDto) {
        if ( menuResponseDto == null ) {
            return null;
        }

        Menu menu = new Menu();

        menu.setItemCategories( itemCategoryListToItemCategoryList( menuResponseDto.getItemCategories() ) );

        return menu;
    }

    @Override
    public MenuRequestDto menuRequestToDto(MenuRequest menuRequest) {
        if ( menuRequest == null ) {
            return null;
        }

        MenuRequestDto menuRequestDto = new MenuRequestDto();

        menuRequestDto.setExternalMenuId( menuRequest.getExternalMenuId() );
        List<String> list = menuRequest.getOrganizationIds();
        if ( list != null ) {
            menuRequestDto.setOrganizationIds( new ArrayList<String>( list ) );
        }

        return menuRequestDto;
    }

    protected MenuItem.Allergen allergenToAllergen(MenuResponseDto.Allergen allergen) {
        if ( allergen == null ) {
            return null;
        }

        MenuItem.Allergen.AllergenBuilder allergen1 = MenuItem.Allergen.builder();

        allergen1.name( allergen.getName() );

        return allergen1.build();
    }

    protected List<MenuItem.Allergen> allergenListToAllergenList(List<MenuResponseDto.Allergen> list) {
        if ( list == null ) {
            return null;
        }

        List<MenuItem.Allergen> list1 = new ArrayList<MenuItem.Allergen>( list.size() );
        for ( MenuResponseDto.Allergen allergen : list ) {
            list1.add( allergenToAllergen( allergen ) );
        }

        return list1;
    }

    protected MenuItem.Price priceToPrice(MenuResponseDto.Price price) {
        if ( price == null ) {
            return null;
        }

        MenuItem.Price.PriceBuilder price1 = MenuItem.Price.builder();

        price1.organizationId( price.getOrganizationId() );
        price1.price( price.getPrice() );

        return price1.build();
    }

    protected List<MenuItem.Price> priceListToPriceList(List<MenuResponseDto.Price> list) {
        if ( list == null ) {
            return null;
        }

        List<MenuItem.Price> list1 = new ArrayList<MenuItem.Price>( list.size() );
        for ( MenuResponseDto.Price price : list ) {
            list1.add( priceToPrice( price ) );
        }

        return list1;
    }

    protected MenuItem.NutritionPerHundredGrams nutritionPerHundredGramsToNutritionPerHundredGrams(MenuResponseDto.NutritionPerHundredGrams nutritionPerHundredGrams) {
        if ( nutritionPerHundredGrams == null ) {
            return null;
        }

        MenuItem.NutritionPerHundredGrams.NutritionPerHundredGramsBuilder nutritionPerHundredGrams1 = MenuItem.NutritionPerHundredGrams.builder();

        nutritionPerHundredGrams1.fats( nutritionPerHundredGrams.getFats() );
        nutritionPerHundredGrams1.proteins( nutritionPerHundredGrams.getProteins() );
        nutritionPerHundredGrams1.carbs( nutritionPerHundredGrams.getCarbs() );
        nutritionPerHundredGrams1.energy( nutritionPerHundredGrams.getEnergy() );

        return nutritionPerHundredGrams1.build();
    }

    protected MenuItem.ItemSize itemSizeToItemSize(MenuResponseDto.ItemSize itemSize) {
        if ( itemSize == null ) {
            return null;
        }

        MenuItem.ItemSize.ItemSizeBuilder itemSize1 = MenuItem.ItemSize.builder();

        itemSize1.sizeId( itemSize.getSizeId() );
        itemSize1.sizeName( itemSize.getSizeName() );
        itemSize1.portionWeightGrams( itemSize.getPortionWeightGrams() );
        itemSize1.prices( priceListToPriceList( itemSize.getPrices() ) );
        itemSize1.buttonImageUrl( itemSize.getButtonImageUrl() );
        itemSize1.nutritionPerHundredGrams( nutritionPerHundredGramsToNutritionPerHundredGrams( itemSize.getNutritionPerHundredGrams() ) );
        itemSize1.measureUnitType( itemSize.getMeasureUnitType() );

        return itemSize1.build();
    }

    protected List<MenuItem.ItemSize> itemSizeListToItemSizeList(List<MenuResponseDto.ItemSize> list) {
        if ( list == null ) {
            return null;
        }

        List<MenuItem.ItemSize> list1 = new ArrayList<MenuItem.ItemSize>( list.size() );
        for ( MenuResponseDto.ItemSize itemSize : list ) {
            list1.add( itemSizeToItemSize( itemSize ) );
        }

        return list1;
    }

    protected MenuItem itemToMenuItem(MenuResponseDto.Item item) {
        if ( item == null ) {
            return null;
        }

        MenuItem.MenuItemBuilder menuItem = MenuItem.builder();

        menuItem.itemId( item.getItemId() );
        menuItem.name( item.getName() );
        menuItem.description( item.getDescription() );
        menuItem.allergens( allergenListToAllergenList( item.getAllergens() ) );
        menuItem.itemSizes( itemSizeListToItemSizeList( item.getItemSizes() ) );

        return menuItem.build();
    }

    protected List<MenuItem> itemListToMenuItemList(List<MenuResponseDto.Item> list) {
        if ( list == null ) {
            return null;
        }

        List<MenuItem> list1 = new ArrayList<MenuItem>( list.size() );
        for ( MenuResponseDto.Item item : list ) {
            list1.add( itemToMenuItem( item ) );
        }

        return list1;
    }

    protected Menu.ItemCategory itemCategoryToItemCategory(MenuResponseDto.ItemCategory itemCategory) {
        if ( itemCategory == null ) {
            return null;
        }

        Menu.ItemCategory.ItemCategoryBuilder itemCategory1 = Menu.ItemCategory.builder();

        itemCategory1.id( itemCategory.getId() );
        itemCategory1.name( itemCategory.getName() );
        itemCategory1.items( itemListToMenuItemList( itemCategory.getItems() ) );

        return itemCategory1.build();
    }

    protected List<Menu.ItemCategory> itemCategoryListToItemCategoryList(List<MenuResponseDto.ItemCategory> list) {
        if ( list == null ) {
            return null;
        }

        List<Menu.ItemCategory> list1 = new ArrayList<Menu.ItemCategory>( list.size() );
        for ( MenuResponseDto.ItemCategory itemCategory : list ) {
            list1.add( itemCategoryToItemCategory( itemCategory ) );
        }

        return list1;
    }
}
