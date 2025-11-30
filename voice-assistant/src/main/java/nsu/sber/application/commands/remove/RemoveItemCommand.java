package nsu.sber.application.commands.remove;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsu.sber.dto.ProcessingResponse;
import nsu.sber.web.dto.ModifyCartItemRequestDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoveItemCommand implements Command<ProcessingResponse> {
    private ModifyCartItemRequestDto modifyRequest;
    private String userText;

    public RemoveItemCommand(String userText) {
        this.userText = userText;
    }
}
