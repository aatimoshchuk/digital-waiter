package nsu.sber.domain.model.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu implements Serializable {
    private List<ItemCategory> itemCategories;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemCategory {
        private String id;
        private String name;
        private List<MenuItem> items;
        private boolean isHidden;
    }
}
