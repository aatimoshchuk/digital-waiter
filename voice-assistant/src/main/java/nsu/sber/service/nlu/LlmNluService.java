package nsu.sber.service.nlu;

public class LlmNluService implements NluService {

    @Override
    public String parse(String text, String context) {
        return "The type that can be parsed into the command";
    }


}
