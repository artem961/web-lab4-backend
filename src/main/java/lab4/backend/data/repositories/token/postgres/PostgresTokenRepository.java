package lab4.backend.data.repositories.token.postgres;

import lab4.backend.dto.TokenDTO;

public interface PostgresTokenRepository {
    public TokenDTO saveToken(TokenDTO tokenDTO);
    public Boolean existsByToken(TokenDTO tokenDTO);
    public void delete(TokenDTO tokenDTO);
}
