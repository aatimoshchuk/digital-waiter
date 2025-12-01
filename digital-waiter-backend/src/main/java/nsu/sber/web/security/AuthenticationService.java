package nsu.sber.web.security;

import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.auth.JwtAuthentication;
import nsu.sber.domain.model.auth.SignInRequest;
import nsu.sber.domain.model.auth.SignInResponse;
import nsu.sber.domain.model.entity.User;
import nsu.sber.domain.port.CurrentUserProvider;
import nsu.sber.exception.DigitalWaiterException;
import nsu.sber.infrastructure.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtProvider jwtProvider;
    private final CurrentUserProvider currentUserProvider;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    @Value("${token.duration.access}")
    private int accessTokenDurationInMinutes;

    @Value("${token.duration.update}")
    private int updateTokenDurationInMinutes;

    public SignInResponse signIn(SignInRequest signInRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getLogin(),
                    signInRequest.getPassword()
            ));
        } catch (AuthenticationException e) {
            throw new DigitalWaiterException.IncorrectPasswordException();
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(signInRequest.getLogin());

        JwtAuthentication jwtAuthentication = createJwtAuthentication(userDetails);
        User currUser = currentUserProvider.getCurrentUser();

        return SignInResponse.builder()
                .jwtAuthentication(jwtAuthentication)
                .role(currUser.getRole())
                .build();
    }

    private JwtAuthentication createJwtAuthentication(UserDetails userDetails) {
        return JwtAuthentication.builder()
                .accessToken(jwtProvider.generateToken(userDetails, accessTokenDurationInMinutes))
                .refreshToken(jwtProvider.generateToken(userDetails, updateTokenDurationInMinutes))
                .build();
    }
}
