package lab4.backend.api.resources;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lab4.backend.api.models.auth.request.LoginRequestModel;
import lab4.backend.api.models.auth.request.LogoutRequestModel;
import lab4.backend.api.models.auth.request.RefreshRequestModel;
import lab4.backend.api.models.auth.request.RegisterRequestModel;
import lab4.backend.api.models.auth.response.TokenPairResponseModel;
import lab4.backend.dto.UserDTO;
import lab4.backend.services.UserService;
import lombok.extern.java.Log;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Log
public class AuthResource {
    @EJB
    private UserService userService;

    @POST
    @Path("/login")
    public TokenPairResponseModel login(LoginRequestModel loginRequestModel) {
        return null;
    }

    @POST
    @Path("/register")
    public TokenPairResponseModel register(RegisterRequestModel registerRequestModel) {
        UserDTO userDTO = UserDTO.builder()
                .username(registerRequestModel.getUsername())
                .password(registerRequestModel.getPassword())
                .build();
        userDTO = userService.createUser(userDTO);
        return userDTO;
    }

    @POST
    @Path("/refresh")
    public TokenPairResponseModel refresh(RefreshRequestModel refreshRequestModel) {
        return null;
    }

    @POST
    @Path("/logout")
    public void logout(LogoutRequestModel logoutRequestModel) {
        return;
    }
}
