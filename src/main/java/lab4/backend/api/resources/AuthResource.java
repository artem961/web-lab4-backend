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
import lab4.backend.api.utils.mapping.AuthMapper;
import lab4.backend.dto.TokenDTO;
import lab4.backend.dto.TokenPairDTO;
import lab4.backend.dto.UserDTO;
import lab4.backend.services.AuthService;
import lombok.extern.java.Log;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Log
public class AuthResource {
    @EJB
    private AuthService authService;

    @POST
    @Path("/login")
    public TokenPairResponseModel login(LoginRequestModel requestModel) {
        UserDTO userDTO = AuthMapper.loginRequestModelToUserDto(requestModel);
        TokenPairDTO tokenPairDTO = authService.authenticate(userDTO);
        return AuthMapper.tokenPairDtoToTokenPairResponseModel(tokenPairDTO);
    }

    @POST
    @Path("/register")
    public TokenPairResponseModel register(RegisterRequestModel requestModel) {
        UserDTO userDTO = AuthMapper.registerRequestModelToUserDto(requestModel);
        TokenPairDTO tokenPairDTO = authService.register(userDTO);
        return AuthMapper.tokenPairDtoToTokenPairResponseModel(tokenPairDTO);
    }

    @POST
    @Path("/refresh")
    public TokenPairResponseModel refresh(RefreshRequestModel requestModel) {
        TokenDTO tokenDTO = AuthMapper.refreshRequestModelToTokenDTO(requestModel);
        TokenPairDTO tokenPairDTO = authService.refreshToken(tokenDTO);
        return AuthMapper.tokenPairDtoToTokenPairResponseModel(tokenPairDTO);
    }

    @POST
    @Path("/logout")
    public void logout(LogoutRequestModel requestModel) {
        TokenDTO tokenDTO = new TokenDTO(requestModel.getRefreshToken());
        authService.logout(tokenDTO);
    }
}
