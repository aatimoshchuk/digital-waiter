package nsu.sber.govno.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NluResult {
    private String text;
    private IntentType intent;
    private double confidence;
    private Map<String, String> entities;
    private String response;

    public static NluResult error(String text) {
        return NluResult.builder()
                .text(text)
                .response("Sorry, I didn't understand your request.")
                .build();
    }
}