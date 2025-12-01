package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.User;
import nsu.sber.domain.port.CurrentUserProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final CurrentUserProvider currentUserProvider;

    public User getCurrentUser() {
        return currentUserProvider.getCurrentUser();
    }
}
