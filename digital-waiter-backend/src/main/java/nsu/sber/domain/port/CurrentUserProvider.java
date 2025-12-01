package nsu.sber.domain.port;

import nsu.sber.domain.model.entity.User;

public interface CurrentUserProvider {
    User getCurrentUser();
}
