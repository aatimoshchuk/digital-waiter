package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.auth.SignInRequest;
import nsu.sber.domain.model.entity.TableAuth;
import nsu.sber.domain.port.repository.jpa.TableAuthRepositoryPort;
import nsu.sber.exception.DigitalWaiterException;
import nsu.sber.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableAuthService {

    private final TableAuthRepositoryPort tableAuthRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String loginTable(SignInRequest signInRequest) {
        TableAuth tableAuth = getTableAuthByLogin(signInRequest.getLogin());

        boolean doesPasswordMatch = bCryptPasswordEncoder.matches(
                signInRequest.getPassword(),
                tableAuth.getPassword());

        if (!doesPasswordMatch) {
            throw new DigitalWaiterException.IncorrectPasswordException();
        }

        return JwtUtil.generateToken(String.valueOf(tableAuth.getId()));
    }

    public TableAuth getTableAuthById(int id) {
        return tableAuthRepository.findById(id)
                .orElseThrow(() -> new DigitalWaiterException.TableAuthWithThisIdNotFoundException(id));
    }

    private TableAuth getTableAuthByLogin(String login) {
        return tableAuthRepository.findByLogin(login)
                .orElseThrow(() -> new DigitalWaiterException.TableAuthWithThisLoginNotFoundException(login));
    }
}
