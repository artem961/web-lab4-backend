package lab4.backend.api.resources;

import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
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
    public Response login(LoginRequestModel requestModel) {
        UserDTO userDTO = AuthMapper.loginRequestModelToUserDto(requestModel);
        TokenPairDTO tokenPairDTO = authService.authenticate(userDTO);
        return createTokenResponse(tokenPairDTO);
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
        TokenDTO tokenDTO = TokenDTO.builder()
                .token(requestModel.getRefreshToken())
                .build();
        authService.logout(tokenDTO);
    }

    private NewCookie createCookie(TokenDTO token){
        return new NewCookie.Builder("refresh-token")
                .value(token.getToken())
                .maxAge(token.getExpires().intValue())
                .path("/")
                .sameSite(NewCookie.SameSite.LAX)
                .httpOnly(true)
                .build();
    }

    private Response createTokenResponse(TokenPairDTO tokenPairDTO){
        TokenPairResponseModel responseModel = TokenPairResponseModel.builder()
                .accessToken(tokenPairDTO.getAccessToken().getToken())
                .refreshToken(tokenPairDTO.getRefreshToken().getToken())
                .tokenType(tokenPairDTO.getTokenType())
                .build();

        NewCookie refreshTokenCookie = createCookie(tokenPairDTO.getRefreshToken());

        return Response
                .ok(responseModel)
                .cookie(refreshTokenCookie)
                .build();
    }
}
