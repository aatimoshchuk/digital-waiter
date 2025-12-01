package nsu.sber.govno.application.commands.add;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import nsu.sber.govno.dto.ProcessingResponse;
import nsu.sber.web.dto.ModifyCartItemRequestDto;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemCommand implements Command<ProcessingResponse> {
    private ModifyCartItemRequestDto modifyRequest;
    private Map<String, String> entities;

    public AddItemCommand(Map<String, String> entities) {
        this.entities = entities;
    }
}
