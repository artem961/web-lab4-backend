package lab4.backend.api.models.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenPairResponseModel {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
}
