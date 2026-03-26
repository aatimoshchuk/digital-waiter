package nsu.sber.voiceassistant.service.menu;

import lombok.Data;

import java.util.List;

@Data
public class MenuViewItem {
    private String category;
    private String name;
    private List<String> sizes;
    private String description;
}