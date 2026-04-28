package nsu.sber.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {
    private static final int MAX_BODY_LENGTH = 5000;

    private static final List<String> EXCLUDED_PATHS = List.of(
            "/api/webhook",
            "/api/notifications/plugin/pull",
            "/api/notifications/plugin/ack"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return EXCLUDED_PATHS.stream()
                .anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);

        long startedAt = System.currentTimeMillis();

        try {
            filterChain.doFilter(wrappedRequest, response);
        } finally {
            long duration = System.currentTimeMillis() - startedAt;

            log.info(
                    "{} {} -> {} [{} ms], params = {}, body = {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    response.getStatus(),
                    duration,
                    getRequestParams(request),
                    maskSensitiveData(getRequestBody(wrappedRequest))
            );
        }
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] content = request.getContentAsByteArray();

        if (content.length == 0) {
            return "{}";
        }

        String body = new String(content, StandardCharsets.UTF_8);

        if (body.length() > MAX_BODY_LENGTH) {
            return body.substring(0, MAX_BODY_LENGTH) + "...[truncated]";
        }

        return body;
    }

    private String getRequestParams(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();

        if (params.isEmpty()) {
            return "{}";
        }

        Map<String, Object> result = new HashMap<>();
        params.forEach((key, value) -> result.put(key, value.length == 1 ? value[0] : Arrays.stream(value).toList()));

        return result.toString();
    }

    private String maskSensitiveData(String body) {
        return body
                .replaceAll("(?i)(\"password\"\\s*:\\s*\")[^\"]+\"", "$1***\"")
                .replaceAll("(?i)(\"refreshToken\"\\s*:\\s*\")[^\"]+\"", "$1***\"")
                .replaceAll("(?i)(\"pullToken\"\\s*:\\s*\")[^\"]+\"", "$1***\"")
                .replaceAll("(?i)(\"apiKey\"\\s*:\\s*\")[^\"]+\"", "$1***\"");
    }
}
