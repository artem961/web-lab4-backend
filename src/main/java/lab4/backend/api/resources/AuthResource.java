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
import lab4.backend.dto.TokenDTO;
import lab4.backend.dto.TokenPairDTO;
import lab4.backend.dto.UserDTO;
import lab4.backend.services.AuthService;
import lab4.backend.utils.mapping.TokenMapper;
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
        UserDTO userDTO =UserDTO.builder()
                .username(requestModel.getUsername())
                .password(requestModel.getPassword())
                .build();

        TokenPairDTO tokenPairDTO = authService.authenticate(userDTO);

        return TokenMapper.tokenPairDTOtoTokenPairResponseModel(tokenPairDTO);
    }

    @POST
    @Path("/register")
    public TokenPairResponseModel register(RegisterRequestModel requestModel) {
        UserDTO userDTO = UserDTO.builder()
                .username(requestModel.getUsername())
                .password(requestModel.getPassword())
                .build();

        TokenPairDTO tokenPairDTO = authService.register(userDTO);

        return TokenMapper.tokenPairDTOtoTokenPairResponseModel(tokenPairDTO);
    }

    @POST
    @Path("/refresh")
    public TokenPairResponseModel refresh(RefreshRequestModel requestModel) {
        TokenDTO tokenDTO = new TokenDTO(requestModel.getRefreshToken());
        TokenPairDTO tokenPairDTO = authService.refreshToken(tokenDTO);

        return TokenMapper.tokenPairDTOtoTokenPairResponseModel(tokenPairDTO);
    }

    @POST
    @Path("/logout")
    public void logout(LogoutRequestModel requestModel) {
        TokenDTO tokenDTO = new TokenDTO(requestModel.getRefreshToken());
        authService.logout(tokenDTO);
    }
}
