package nsu.sber.service.nlu;

public interface NluService {

    // TODO: подумать над возвращаемым типом
    String parse(String text, String context);

}
