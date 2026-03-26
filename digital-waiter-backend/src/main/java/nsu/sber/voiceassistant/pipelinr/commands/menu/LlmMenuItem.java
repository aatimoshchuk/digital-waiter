package nsu.sber.voiceassistant.pipelinr.commands.menu;

import lombok.Data;

import java.util.List;

@Data
public class LlmMenuItem {
    private String name;
    private List<String> sizes;
}