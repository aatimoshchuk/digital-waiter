package nsu.sber.messaging.pos.iiko.config;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class IikoFeignConfig {

    private final IikoAuthTokenProvider iikoAuthTokenProvider;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            boolean alreadySet = requestTemplate.headers().containsKey("Authorization");
            if (alreadySet) return;

            String token = iikoAuthTokenProvider.getToken();
            requestTemplate.header("Authorization", "Bearer " + token);
        };
    }

}
