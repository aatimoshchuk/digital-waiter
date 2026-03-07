package nsu.sber.domain.service;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.RestaurantTable;
import nsu.sber.domain.model.entity.RoleType;
import nsu.sber.domain.model.entity.User;
import nsu.sber.domain.model.restaurant_table.CreateRestaurantTableResponse;
import nsu.sber.domain.port.CurrentUserProvider;
import nsu.sber.domain.port.PasswordGeneratorPort;
import nsu.sber.domain.port.TransliteratorPort;
import nsu.sber.domain.port.repository.jpa.UserRepositoryPort;
import nsu.sber.exception.DigitalWaiterException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final CurrentUserProvider currentUserProvider;
    private final PasswordGeneratorPort passwordGenerator;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TransliteratorPort transliterator;

    private final UserRepositoryPort userRepository;

    public User getCurrentUser() {
        return currentUserProvider.getCurrentUser();
    }

    public User getUserByRestaurantTableId(Integer restaurantTableId) {
        return userRepository.findByRestaurantTableId(restaurantTableId)
                .orElseThrow(DigitalWaiterException.UserWithThisRestaurantTableIdNotFoundException::new);
    }

    @Transactional(transactionManager = "transactionManager")
    public User updateUserByRestaurantTableId(Integer restaurantTableId, String login, String password) {
        User user = getUserByRestaurantTableId(restaurantTableId);

        String targetLogin = login != null ? login : user.getLogin();
        boolean loginChanged = !Objects.equals(targetLogin, user.getLogin());

        if (loginChanged && userRepository.existsByLogin(login)) {
            throw new DigitalWaiterException.UserWithThisLoginAlreadyExistException();
        }

        user.setLogin(login);
        user.setPasswordHash(passwordEncoder.encode(password));

        return userRepository.save(user);
    }

    public CreateRestaurantTableResponse createGuestUser(RestaurantTable restaurantTable, String organizationName) {
        String password = passwordGenerator.generate();
        String login = generateLogin(
                organizationName,
                restaurantTable.getTerminalGroupId(),
                restaurantTable.getId()
        );

        User user = User
                .builder()
                .login(login)
                .passwordHash(passwordEncoder.encode(password))
                .role(RoleType.GUEST)
                .restaurantTableId(restaurantTable.getId())
                .build();

        userRepository.save(user);

        return CreateRestaurantTableResponse
                .builder()
                .login(login)
                .password(password)
                .tableName(restaurantTable.getName())
                .build();
    }

    private String generateLogin(String organizationName, Integer terminalGroupId, Integer tableId) {
        String base = toSlug(organizationName) + "_tg" + terminalGroupId + "_t" + tableId;
        String login = base;

        int counter = 1;

        while (userRepository.existsByLogin(login)) {
            login = base + "_" + counter;
            counter++;
        }

        return login;
    }

    private String toSlug(String name) {
        if (name == null) {
            return null;
        }

        String transliterated = transliterator.translit(name);
        String lower = transliterated.toLowerCase(Locale.ROOT);
        String slug = lower.replaceAll("[^a-z0-9]+", "_");
        slug = slug.replaceAll("^_+|_+$", "");

        return slug;
    }
}
