package lab4.backend.api.resources;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lab4.backend.api.models.request.auth.LoginRequestModel;
import lab4.backend.api.models.request.auth.LogoutRequestModel;
import lab4.backend.api.models.request.auth.RefreshRequestModel;
import lab4.backend.api.models.request.auth.RegisterRequestModel;
import lab4.backend.api.models.response.auth.TokenPairResponseModel;

@Path("/auth")
public class AuthResource {

    @POST
    @Path("/login")
    public TokenPairResponseModel login(LoginRequestModel loginRequestModel) {
        return null;
    }

    @POST
    @Path("/register")
    public TokenPairResponseModel register(RegisterRequestModel registerRequestModel) {
        return null;
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
