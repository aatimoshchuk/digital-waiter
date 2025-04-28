package nsu.sber.web.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.sber.domain.model.TableAuth;
import nsu.sber.domain.service.TableAuthService;
import nsu.sber.exception.AuthException;
import nsu.sber.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final TableAuthService tableAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IOException, ServletException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        try {
            String id = JwtUtil.extractId(token);
            request.setAttribute("id", id);

            TableAuth tableAuth = tableAuthService.getTableAuthById(Integer.parseInt(id));

            Authentication auth = new UsernamePasswordAuthenticationToken(tableAuth, null,
                    List.of(new SimpleGrantedAuthority("ROLE_USER")));
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (ExpiredJwtException | MalformedJwtException | SignatureException e) {
            String errorMessage = "Неверный или истекший токен";
            log.error(errorMessage);
            throw new AuthException(errorMessage, e);
        }

        filterChain.doFilter(request, response);
    }
}
