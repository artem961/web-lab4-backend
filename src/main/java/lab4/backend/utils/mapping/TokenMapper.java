package lab4.backend.utils.mapping;

import lab4.backend.data.entities.TokenEntity;
import lab4.backend.dto.TokenDTO;
import lab4.backend.dto.TokenPairDTO;

public class TokenMapper {
    public static TokenDTO entityToDTO(TokenEntity tokenEntity){
        TokenDTO tokenDTO = TokenDTO.builder()
                .token(tokenEntity.getToken())
                .build();

        return tokenDTO;
    }

    public static TokenEntity dtoToEntity(TokenDTO tokenDTO){
        TokenEntity tokenEntity = new TokenEntity();

        tokenEntity.setToken(tokenDTO.getToken());

        return tokenEntity;
    }
}
