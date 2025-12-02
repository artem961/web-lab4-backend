package lab4.backend.api.utils.mapping;

import lab4.backend.api.models.auth.response.TokenPairResponseModel;
import lab4.backend.dto.TokenPairDTO;

public class AuthMapper {
    public static TokenPairResponseModel tokenPairDtoToTokenPairResponseModel(TokenPairDTO tokenPairDTO){
        return TokenPairResponseModel.builder()
                .accessToken(tokenPairDTO.getAccessToken().getToken())
                .refreshToken(tokenPairDTO.getRefreshToken().getToken())
                .tokenType(tokenPairDTO.getTokenType())
                .build();
    }

}
