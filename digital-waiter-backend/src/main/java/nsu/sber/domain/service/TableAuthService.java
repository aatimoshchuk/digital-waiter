package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.domain.model.LoginRequest;
import nsu.sber.domain.model.TableAuth;
import nsu.sber.domain.port.TableAuthRepositoryPort;
import nsu.sber.exception.AuthException;
import nsu.sber.exception.ErrorType;
import nsu.sber.exception.ServiceException;
import nsu.sber.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TableAuthService {

    private final TableAuthRepositoryPort tableAuthRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String loginTable(LoginRequest loginRequest) {
        TableAuth tableAuth = getTableAuthByLogin(loginRequest.getLogin());

        boolean doesPasswordMatch = bCryptPasswordEncoder.matches(
                loginRequest.getPassword(),
                tableAuth.getPassword());

        if (!doesPasswordMatch) {
            throw new AuthException("Неверный пароль");
        }

        return JwtUtil.generateToken(String.valueOf(tableAuth.getId()));
    }

    public TableAuth getTableAuthById(int id) {
        TableAuth tableAuth = tableAuthRepository.findById(id);

        if (tableAuth == null) {
            String errorMessage = "Авторизация для стола с id %s не найдена".formatted(id);
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.TABLE_AUTH_NOT_FOUND);
        }

        return tableAuth;
    }

    private TableAuth getTableAuthByLogin(String login) {
        TableAuth tableAuth = tableAuthRepository.findByLogin(login);

        if (tableAuth == null) {
            String errorMessage = "Авторизация для стола по логину %s не найдена".formatted(login);
            log.error(errorMessage);
            throw new ServiceException(errorMessage, ErrorType.TABLE_AUTH_NOT_FOUND);
        }

        return tableAuth;
    }
}
