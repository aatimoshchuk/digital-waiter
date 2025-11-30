package nsu.sber.service.nlu;

import nsu.sber.model.NluResult;

public interface NluService {

    // TODO: подумать над возвращаемым типом
    NluResult parse(String text, String context);

}
