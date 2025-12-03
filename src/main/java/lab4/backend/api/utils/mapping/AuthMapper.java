package lab4.backend.api.utils.mapping;

import lab4.backend.api.models.auth.request.LoginRequestModel;
import lab4.backend.api.models.auth.request.RefreshRequestModel;
import lab4.backend.api.models.auth.request.RegisterRequestModel;
import lab4.backend.api.models.auth.response.TokenPairResponseModel;
import lab4.backend.dto.TokenDTO;
import lab4.backend.dto.TokenPairDTO;
import lab4.backend.dto.UserDTO;

public class AuthMapper {
    public static TokenPairResponseModel tokenPairDtoToTokenPairResponseModel(TokenPairDTO tokenPairDTO){
        return TokenPairResponseModel.builder()
                .accessToken(tokenPairDTO.getAccessToken().getToken())
                .refreshToken(tokenPairDTO.getRefreshToken().getToken())
                .tokenType(tokenPairDTO.getTokenType())
                .build();
    }

    public static UserDTO loginRequestModelToUserDto(LoginRequestModel requestModel){
        UserDTO userDTO =UserDTO.builder()
                .username(requestModel.getUsername())
                .password(requestModel.getPassword())
                .build();
        return userDTO;
    }

    public static UserDTO registerRequestModelToUserDto(RegisterRequestModel requestModel){
        UserDTO userDTO =UserDTO.builder()
                .username(requestModel.getUsername())
                .password(requestModel.getPassword())
                .build();
        return userDTO;
    }

    public static TokenDTO refreshRequestModelToTokenDTO(RefreshRequestModel requestModel){
        return TokenDTO.builder()
                .token(requestModel.getRefreshToken())
                .build();
    }

}
