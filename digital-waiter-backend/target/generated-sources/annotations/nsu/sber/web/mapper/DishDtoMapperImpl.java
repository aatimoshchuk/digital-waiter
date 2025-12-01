package nsu.sber.web.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import nsu.sber.domain.model.menu.Menu;
import nsu.sber.domain.model.menu.MenuItem;
import nsu.sber.web.dto.MenuItemResponseDto;
import nsu.sber.web.dto.MenuResponseDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-29T20:45:23+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class DishDtoMapperImpl implements DishDtoMapper {

    @Override
    public MenuResponseDto menuResponseToDto(Menu menu) {
        if ( menu == null ) {
            return null;
        }

        MenuResponseDto.MenuResponseDtoBuilder menuResponseDto = MenuResponseDto.builder();

        menuResponseDto.itemCategories( itemCategoryListToItemCategoryList( menu.getItemCategories() ) );

        return menuResponseDto.build();
    }

    @Override
    public MenuItemResponseDto menuItemToDishInfoResponseDto(MenuItem menuItem) {
        if ( menuItem == null ) {
            return null;
        }

        MenuItemResponseDto.MenuItemResponseDtoBuilder menuItemResponseDto = MenuItemResponseDto.builder();

        menuItemResponseDto.itemId( menuItem.getItemId() );
        menuItemResponseDto.name( menuItem.getName() );
        menuItemResponseDto.description( menuItem.getDescription() );
        menuItemResponseDto.allergens( allergenListToAllergenList( menuItem.getAllergens() ) );
        menuItemResponseDto.itemSizes( itemSizeListToItemSizeList1( menuItem.getItemSizes() ) );

        return menuItemResponseDto.build();
    }

    @Override
    public MenuResponseDto.ItemSize menuItemSizeToMenuResponseItemSize(MenuItem.ItemSize itemSize) {
        if ( itemSize == null ) {
            return null;
        }

        MenuResponseDto.ItemSize.ItemSizeBuilder itemSize1 = MenuResponseDto.ItemSize.builder();

        itemSize1.sizeId( itemSize.getSizeId() );
        itemSize1.sizeName( itemSize.getSizeName() );
        itemSize1.portionWeightGrams( itemSize.getPortionWeightGrams() );
        itemSize1.measureUnitType( itemSize.getMeasureUnitType() );
        itemSize1.buttonImageUrl( itemSize.getButtonImageUrl() );

        itemSize1.price( extractPrice(itemSize.getPrices()) );

        return itemSize1.build();
    }

    @Override
    public MenuItemResponseDto.ItemSize menuItemSizeToDishInfoResponseItemSize(MenuItem.ItemSize itemSize) {
        if ( itemSize == null ) {
            return null;
        }

        MenuItemResponseDto.ItemSize.ItemSizeBuilder itemSize1 = MenuItemResponseDto.ItemSize.builder();

        itemSize1.sizeId( itemSize.getSizeId() );
        itemSize1.sizeName( itemSize.getSizeName() );
        itemSize1.portionWeightGrams( itemSize.getPortionWeightGrams() );
        itemSize1.measureUnitType( itemSize.getMeasureUnitType() );
        itemSize1.buttonImageUrl( itemSize.getButtonImageUrl() );
        itemSize1.nutritionPerHundredGrams( nutritionPerHundredGramsToNutritionPerHundredGrams( itemSize.getNutritionPerHundredGrams() ) );

        itemSize1.price( extractPrice(itemSize.getPrices()) );

        return itemSize1.build();
    }

    protected List<MenuResponseDto.ItemSize> itemSizeListToItemSizeList(List<MenuItem.ItemSize> list) {
        if ( list == null ) {
            return null;
        }

        List<MenuResponseDto.ItemSize> list1 = new ArrayList<MenuResponseDto.ItemSize>( list.size() );
        for ( MenuItem.ItemSize itemSize : list ) {
            list1.add( menuItemSizeToMenuResponseItemSize( itemSize ) );
        }

        return list1;
    }

    protected MenuResponseDto.Item menuItemToItem(MenuItem menuItem) {
        if ( menuItem == null ) {
            return null;
        }

        MenuResponseDto.Item.ItemBuilder item = MenuResponseDto.Item.builder();

        item.itemId( menuItem.getItemId() );
        item.name( menuItem.getName() );
        item.itemSizes( itemSizeListToItemSizeList( menuItem.getItemSizes() ) );

        return item.build();
    }

    protected List<MenuResponseDto.Item> menuItemListToItemList(List<MenuItem> list) {
        if ( list == null ) {
            return null;
        }

        List<MenuResponseDto.Item> list1 = new ArrayList<MenuResponseDto.Item>( list.size() );
        for ( MenuItem menuItem : list ) {
            list1.add( menuItemToItem( menuItem ) );
        }

        return list1;
    }

    protected MenuResponseDto.ItemCategory itemCategoryToItemCategory(Menu.ItemCategory itemCategory) {
        if ( itemCategory == null ) {
            return null;
        }

        MenuResponseDto.ItemCategory.ItemCategoryBuilder itemCategory1 = MenuResponseDto.ItemCategory.builder();

        itemCategory1.id( itemCategory.getId() );
        itemCategory1.name( itemCategory.getName() );
        itemCategory1.items( menuItemListToItemList( itemCategory.getItems() ) );

        return itemCategory1.build();
    }

    protected List<MenuResponseDto.ItemCategory> itemCategoryListToItemCategoryList(List<Menu.ItemCategory> list) {
        if ( list == null ) {
            return null;
        }

        List<MenuResponseDto.ItemCategory> list1 = new ArrayList<MenuResponseDto.ItemCategory>( list.size() );
        for ( Menu.ItemCategory itemCategory : list ) {
            list1.add( itemCategoryToItemCategory( itemCategory ) );
        }

        return list1;
    }

    protected MenuItemResponseDto.Allergen allergenToAllergen(MenuItem.Allergen allergen) {
        if ( allergen == null ) {
            return null;
        }

        MenuItemResponseDto.Allergen.AllergenBuilder allergen1 = MenuItemResponseDto.Allergen.builder();

        allergen1.name( allergen.getName() );

        return allergen1.build();
    }

    protected List<MenuItemResponseDto.Allergen> allergenListToAllergenList(List<MenuItem.Allergen> list) {
        if ( list == null ) {
            return null;
        }

        List<MenuItemResponseDto.Allergen> list1 = new ArrayList<MenuItemResponseDto.Allergen>( list.size() );
        for ( MenuItem.Allergen allergen : list ) {
            list1.add( allergenToAllergen( allergen ) );
        }

        return list1;
    }

    protected List<MenuItemResponseDto.ItemSize> itemSizeListToItemSizeList1(List<MenuItem.ItemSize> list) {
        if ( list == null ) {
            return null;
        }

        List<MenuItemResponseDto.ItemSize> list1 = new ArrayList<MenuItemResponseDto.ItemSize>( list.size() );
        for ( MenuItem.ItemSize itemSize : list ) {
            list1.add( menuItemSizeToDishInfoResponseItemSize( itemSize ) );
        }

        return list1;
    }

    protected MenuItemResponseDto.NutritionPerHundredGrams nutritionPerHundredGramsToNutritionPerHundredGrams(MenuItem.NutritionPerHundredGrams nutritionPerHundredGrams) {
        if ( nutritionPerHundredGrams == null ) {
            return null;
        }

        MenuItemResponseDto.NutritionPerHundredGrams.NutritionPerHundredGramsBuilder nutritionPerHundredGrams1 = MenuItemResponseDto.NutritionPerHundredGrams.builder();

        nutritionPerHundredGrams1.fats( nutritionPerHundredGrams.getFats() );
        nutritionPerHundredGrams1.proteins( nutritionPerHundredGrams.getProteins() );
        nutritionPerHundredGrams1.carbs( nutritionPerHundredGrams.getCarbs() );
        nutritionPerHundredGrams1.energy( nutritionPerHundredGrams.getEnergy() );

        return nutritionPerHundredGrams1.build();
    }
}
