package nsu.sber.application.commands.add;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.sber.dto.ProcessingResponse;
import nsu.sber.web.dto.ModifyCartItemRequestDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemCommand implements Command<ProcessingResponse> {
    private ModifyCartItemRequestDto modifyRequest;
    private String userText;

    public AddItemCommand(String userText) {
        this.userText = userText;
    }
}
