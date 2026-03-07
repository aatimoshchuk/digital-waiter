package nsu.sber.web.security;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.User;
import nsu.sber.domain.port.repository.jpa.UserRepositoryPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepositoryPort userRepository;

    public User getByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(DigitalWaiterException.UserWithThisLoginNotFoundException::new);
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        return new CustomUserDetails(getByLogin(login));
    }
}
