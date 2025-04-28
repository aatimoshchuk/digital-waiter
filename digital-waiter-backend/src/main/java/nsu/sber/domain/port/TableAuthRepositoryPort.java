package nsu.sber.domain.port;

import nsu.sber.domain.model.TableAuth;

public interface TableAuthRepositoryPort {

    TableAuth findByLogin(String login);

    TableAuth findById(int id);

}
