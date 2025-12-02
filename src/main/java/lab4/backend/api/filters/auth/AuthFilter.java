package lab4.backend.api.filters.auth;

import jakarta.annotation.Priority;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import lab4.backend.services.AuthService;
import lombok.extern.java.Log;

import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
@AuthorizedOnly
@Log
public class AuthFilter implements ContainerRequestFilter {
    @EJB
    private AuthService authService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        log.info("AuthFilter Called");
    }
}
