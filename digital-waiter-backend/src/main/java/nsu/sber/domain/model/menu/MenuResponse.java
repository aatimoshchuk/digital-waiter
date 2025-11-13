package nsu.sber.domain.model.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MenuResponse {
    private List<ItemCategory> itemCategories;

    @Data
    @Builder
    public static class ItemCategory {
        private String id;
        private String name;
        private List<MenuItem> items;
        private boolean isHidden;
    }
}
