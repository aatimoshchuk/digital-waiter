package nsu.sber.voiceassistant.service.menu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemFlat {
    private String category;
    private String name;
    private String itemId;
    private List<String> sizes;
}