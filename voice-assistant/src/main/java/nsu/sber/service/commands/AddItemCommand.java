package nsu.sber.service.commands;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddItemCommand implements AppCommand<Void> {
    //private ModifyCartItemRequestDto item;
}