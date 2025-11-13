package nsu.sber.util;

import lombok.experimental.UtilityClass;
import nsu.sber.domain.model.entity.TableAuth;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class SecurityUtil {
    public static TableAuth getCurrentTableAuth() {
        try {
            return (TableAuth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            return null;
        }
    }
}
