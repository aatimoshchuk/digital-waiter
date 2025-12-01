package nsu.sber.web.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import nsu.sber.domain.model.entity.Organization;
import nsu.sber.domain.model.entity.RestaurantTable;
import nsu.sber.domain.model.entity.TableAuth;
import nsu.sber.domain.model.entity.TerminalGroup;
import nsu.sber.domain.service.OrganizationService;
import nsu.sber.domain.service.RestaurantTableService;
import nsu.sber.domain.service.TerminalGroupService;
import nsu.sber.util.SecurityUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class CustomRequestContextFilter implements Filter {

    private final RestaurantTableService restaurantTableService;
    private final TerminalGroupService terminalGroupService;
    private final OrganizationService organizationService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        TableAuth tableAuth = SecurityUtil.getCurrentTableAuth();

        if (tableAuth == null) {
            chain.doFilter(request, response);
            return;
        }

        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        RestaurantTable restaurantTable = restaurantTableService.getRestaurantTableById(tableAuth.getId());
        TerminalGroup terminalGroup = terminalGroupService.getTerminalGroupById(restaurantTable.getTerminalGroupId());
        Organization organization = organizationService.getOrganizationById(terminalGroup.getOrganizationId());

        attrs.setAttribute("posTableId", restaurantTable.getPosTableId(), RequestAttributes.SCOPE_REQUEST);
        attrs.setAttribute("posTerminalGroupId", terminalGroup.getPosTerminalGroupId(), RequestAttributes.SCOPE_REQUEST);
        attrs.setAttribute("posExternalMenuId", terminalGroup.getPosExternalMenuId(), RequestAttributes.SCOPE_REQUEST);
        attrs.setAttribute("posOrganizationId", organization.getPosOrganizationId(), RequestAttributes.SCOPE_REQUEST);
        attrs.setAttribute("apiKey", organization.getApiKey(), RequestAttributes.SCOPE_REQUEST);

        chain.doFilter(request, response);
    }
}
