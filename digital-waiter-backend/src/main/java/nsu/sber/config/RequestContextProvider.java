package nsu.sber.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@RequiredArgsConstructor
public class RequestContextProvider {

    private final HttpServletRequest request;

    public String getOrganizationId() {
        return (String) request.getAttribute("posOrganizationId");
    }

    public String getExternalMenuId() {
        return (String) request.getAttribute("posExternalMenuId");
    }

    public String getTableId() {
        return (String) request.getAttribute("posTableId");
    }

    public String getTerminalGroupId() {
        return (String) request.getAttribute("posTerminalGroupId");
    }

    public String getApiKey() {
        return (String) request.getAttribute("apiKey");
    }
}
