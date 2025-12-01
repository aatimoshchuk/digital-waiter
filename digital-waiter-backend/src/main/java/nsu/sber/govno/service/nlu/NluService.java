package nsu.sber.govno.service.nlu;


import nsu.sber.govno.model.NluResult;

public interface NluService {

    // TODO: подумать над возвращаемым типом
    NluResult parse(String text, String context);

}
