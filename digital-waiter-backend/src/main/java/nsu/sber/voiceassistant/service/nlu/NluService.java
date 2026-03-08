package nsu.sber.voiceassistant.service.nlu;


import nsu.sber.voiceassistant.model.NluResult;

public interface NluService {

    // TODO: подумать над возвращаемым типом
    NluResult parse(String text, String context);

}
