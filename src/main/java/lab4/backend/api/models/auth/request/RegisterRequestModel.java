package lab4.backend.api.models.auth.request;

import lombok.Data;

@Data
public class RegisterRequestModel {
    private String username;
    private String password;
}
