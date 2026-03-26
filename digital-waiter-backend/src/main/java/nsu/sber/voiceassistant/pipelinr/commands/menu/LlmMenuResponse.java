package nsu.sber.voiceassistant.pipelinr.commands.menu;

import lombok.Data;

import java.util.List;

@Data
public class LlmMenuResponse {
    private List<LlmMenuItem> items;
}