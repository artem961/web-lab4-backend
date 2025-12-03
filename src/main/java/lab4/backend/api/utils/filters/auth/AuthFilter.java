package lab4.backend.api.utils.filters.auth;

import jakarta.annotation.Priority;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import lab4.backend.dto.TokenDTO;
import lab4.backend.dto.TokenPayloadDTO;
import lab4.backend.services.AuthService;
import lab4.backend.services.exceptions.ServiceException;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.List;

@Provider
@Priority(Priorities.AUTHENTICATION)
@AuthorizedOnly
@Log
public class AuthFilter implements ContainerRequestFilter {
    @EJB
    private AuthService authService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) {
        AuthorizationHeader authHeader = new AuthorizationHeader(
                containerRequestContext.getHeaderString("Authorization"));

        if (authHeader.getTokenType().equalsIgnoreCase("Bearer")) {
            processBearerToken(containerRequestContext, authHeader.getToken());
        } else {
            abortWithUnauthorized(containerRequestContext);
        }
    }

    private void processBearerToken(ContainerRequestContext containerRequestContext, String bearerToken) {
        TokenPayloadDTO payloadDTO = authService.authorize(new TokenDTO(bearerToken));
        log.info(payloadDTO.toString());
    }

    private void abortWithUnauthorized(ContainerRequestContext containerRequestContext) {
        containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }

    @Getter
    private class AuthorizationHeader{
        private String tokenType;
        private String token;

        public AuthorizationHeader(String header) {
            try {
                List<String> parsed = List.of(header.split("\\s"));
                this.tokenType = parsed.get(0);
                this.token = parsed.get(1);

                if (parsed.size() != 2 || this.tokenType == null || this.token == null) {
                    throw new Exception();
                }
            } catch (Exception e) {
                throw new ServiceException("Authorization header is invalid");
            }
        }
    }
}
