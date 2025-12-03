package lab4.backend.api.resources;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
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
import lab4.backend.services.exceptions.ServiceException;
import lombok.NonNull;
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
        try {
            UserDTO userDTO = AuthMapper.loginRequestModelToUserDto(requestModel);
            TokenPairDTO tokenPairDTO = authService.authenticate(userDTO);
            return createTokenPairResponse(tokenPairDTO);
        } catch (ServiceException e) {
            return createErrorResponse(e);
        }
    }

    @POST
    @Path("/register")
    public Response register(RegisterRequestModel requestModel) {
        try {
            UserDTO userDTO = AuthMapper.registerRequestModelToUserDto(requestModel);
            TokenPairDTO tokenPairDTO = authService.register(userDTO);
            return createTokenPairResponse(tokenPairDTO);
        } catch (ServiceException e) {
            return createErrorResponse(e);
        }
    }

    @POST
    @Path("/refresh")
    public Response refresh(@CookieParam("refresh-token") String refreshToken) {
        try {
            if (refreshToken == null) {
                throw new ServiceException("Refresh token not found");
            }
            TokenDTO tokenDTO = TokenDTO.builder().token(refreshToken).build();
            TokenPairDTO tokenPairDTO = authService.refreshToken(tokenDTO);
            return createTokenPairResponse(tokenPairDTO);
        } catch (ServiceException e) {
            return createErrorResponse(e);
        }
    }

    @POST
    @Path("/logout")
    public Response logout(@CookieParam("refresh-token") String refreshToken) {
        try {
            if (refreshToken == null) {
                throw new ServiceException("Refresh token not found");
            }
            TokenDTO tokenDTO = TokenDTO.builder()
                    .token(refreshToken)
                    .build();
            authService.logout(tokenDTO);
            return Response.noContent().build();
        } catch (ServiceException e) {
            return createErrorResponse(e);
        }
    }

    private NewCookie createCookie(TokenDTO token) {
        return new NewCookie.Builder("refresh-token")
                .value(token.getToken())
                .maxAge(token.getExpires().intValue())
                .path("/")
                .sameSite(NewCookie.SameSite.LAX)
                .httpOnly(true)
                .build();
    }

    private Response createTokenPairResponse(TokenPairDTO tokenPairDTO) {
        TokenPairResponseModel responseModel = TokenPairResponseModel.builder()
                .accessToken(tokenPairDTO.getAccessToken().getToken())
               // .refreshToken(tokenPairDTO.getRefreshToken().getToken())
                .tokenType(tokenPairDTO.getTokenType())
                .build();

        NewCookie refreshTokenCookie = createCookie(tokenPairDTO.getRefreshToken());

        return Response
                .ok(responseModel)
                .cookie(refreshTokenCookie)
                .build();
    }

    private Response createErrorResponse(ServiceException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(e.getMessage())
                .build();
    }
}
