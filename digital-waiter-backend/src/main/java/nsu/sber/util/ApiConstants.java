package nsu.sber.util;

public final class ApiConstants {

    private ApiConstants() {}

    public static final String DISH_ID_DESCRIPTION = "Unique identifier of the dish";
    public static final String DISH_NAME_DESCRIPTION = "Name of the dish as displayed to the user";
    public static final String DISH_DESCRIPTION_DESCRIPTION = """
            Detailed description of the dish, including ingredients and preparation notes
            """;
    public static final String SIZE_ID_DESCRIPTION = "Identifier of the selected size variant of the dish";
    public static final String SIZE_NAME_DESCRIPTION = "Human-readable name of the size";
    public static final String PORTION_WEIGHT_DESCRIPTION = "Weight of a single portion";
    public static final String MEASURE_UNIT_DESCRIPTION = "Unit of measurement for the portion";
    public static final String PRICE_DESCRIPTION = "Current price of the dish";
    public static final String IMAGE_URL_DESCRIPTION = "URL to the dish's image used for display in the UI";
    public static final String QUANTITY_DESCRIPTION = "Number of portions of this dish added to the cart";
    public static final String ALLERGENS_DESCRIPTION = "List of allergens associated with this dish";
    public static final String ALLERGEN_NAME_DESCRIPTION = "Name of the allergen present in the dish";
    public static final String ITEM_SIZES_DESCRIPTION = "Available size/portion variants of this dish";

    public static final String NUTRITION_DESCRIPTION = "Nutritional information per 100 grams of this dish.";
    public static final String NUTRITION_FATS_DESCRIPTION = "Amount of fats per 100 grams of the dish";
    public static final String NUTRITION_PROTEINS_DESCRIPTION = "Amount of proteins per 100 grams of the dish";
    public static final String NUTRITION_CARBS_DESCRIPTION = "Amount of carbohydrates per 100 grams of the dish";
    public static final String NUTRITION_ENERGY_DESCRIPTION = "Energy value per 100 grams of the dish";
    public static final String MENU_ITEM_CATEGORIES_DESCRIPTION = """
            List of menu categories, each containing a group of related dishes
            """;
    public static final String CATEGORY_ID_DESCRIPTION = "Unique identifier of the menu category";
    public static final String CATEGORY_NAME_DESCRIPTION = "Name of the menu category";
    public static final String ITEMS_DESCRIPTION = "List of menu items (dishes) belonging to this category";
    public static final String CORRELATION_ID_DESCRIPTION = "Identifier for tracking the status of the operation";

    public static final String CATEGORY_ID_EXAMPLE = "a1b2c3d4-e5f6-7890-g1h2-i3j4k5l6m7n8";
    public static final String CATEGORY_NAME_EXAMPLE = "Салаты";
    public static final String POS_ID_EXAMPLE = "3b9326fa-d589-48a6-ac80-515497d5c438";
    public static final String DISH_NAME_EXAMPLE = "Зеленый салат с рикоттой в клубничном соусе";
    public static final String PORTION_WEIGHT_EXAMPLE = "170.0";
    public static final String MEASURE_UNIT_EXAMPLE = "GRAM";
    public static final String PRICE_EXAMPLE = "480.0";
    public static final String IMAGE_URL_EXAMPLE = "https://clck.ru/3QJ339";
    public static final String QUANTITY_EXAMPLE = "1";
    public static final String SIZE_NAME_EXAMPLE = "Standard";

    public static final String ADMIN_ACCESS_RESTRICTION =
            "Only authenticated administrative users are allowed to perform this operation";

    public static final String API_KEY_DESCRIPTION = "API key used to access iiko API for this organization";
    public static final String ORGANIZATION_ID_DESCRIPTION = "Internal organization ID";
    public static final String POS_ORGANIZATION_ID_DESCRIPTION = "POS Organization ID from iiko API";
    public static final String ORGANIZATION_ID_EXAMPLE = "2";
    public static final String ORGANIZATION_NAME_DESCRIPTION = "Human-readable organization name";
    public static final String ORGANIZATION_NAME_EXAMPLE = "Sparks";
}
