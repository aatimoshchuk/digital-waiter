package nsu.sber.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NluResult {
    private String text;
    private String response;

    public static NluResult error(String text) {
        return NluResult.builder()
                .text(text)
                .response("Sorry, I didn't understand your request.")
                .build();
    }
}
