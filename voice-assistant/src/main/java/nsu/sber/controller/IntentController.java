package nsu.sber.controller;

import lombok.RequiredArgsConstructor;
import nsu.sber.model.CommandIntent;
import nsu.sber.service.IntentExecutor;
import nsu.sber.service.parser.CommandParser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/intent")
public class IntentController {

    private final CommandParser parser;
    private final IntentExecutor executor;

    @PostMapping
    public Object handleIntent(@RequestBody String nluJson) {
        CommandIntent intent = parser.parse(nluJson);
        return executor.execute(intent);
    }
}