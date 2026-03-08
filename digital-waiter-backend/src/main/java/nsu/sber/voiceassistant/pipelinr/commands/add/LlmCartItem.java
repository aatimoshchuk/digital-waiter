package nsu.sber.voiceassistant.pipelinr.commands.add;
import lombok.Data;

@Data
public class LlmCartItem {

    private String itemId;

    private String sizeId;

    private Integer quantity;

}