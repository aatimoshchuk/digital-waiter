package nsu.sber.util;

import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class PromptLoader {

    public static String loadPrompt(String filename) throws FileNotFoundException {
        try {
            var resource = new ClassPathResource("prompts/" + filename);
            return Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new FileNotFoundException("Prompt file not found: " + filename);
        }
    }
}
