package lab4.backend.data.repositories.token.postgres;

import lab4.backend.data.entities.TokenEntity;

public interface PostgresTokenRepository {
    public TokenEntity saveToken(TokenEntity token);
    public Boolean existsByToken(TokenEntity token);
    public void delete(TokenEntity token);
}
