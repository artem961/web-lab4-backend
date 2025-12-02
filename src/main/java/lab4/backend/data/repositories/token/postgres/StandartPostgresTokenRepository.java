package lab4.backend.data.repositories.token.postgres;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lab4.backend.data.entities.TokenEntity;
import lab4.backend.dto.TokenDTO;
import lab4.backend.utils.mapping.TokenMapper;

@Singleton
public class StandartPostgresTokenRepository implements PostgresTokenRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public TokenDTO saveToken(TokenDTO tokenDTO) {
        TokenEntity tokenEntity = TokenMapper.dtoToEntity(tokenDTO);
        em.persist(tokenEntity);
        em.flush();
        return TokenMapper.entityToDTO(tokenEntity);
    }

    @Override
    public Boolean existsByToken(TokenDTO tokenDTO) {
        String token = tokenDTO.getToken();

        Object result = em.createQuery("select 1 from TokenEntity where token=:token")
                .setParameter("token", token)
                .getSingleResult();

        return (Boolean) result != null;
    }

    @Override
    public void delete(TokenDTO tokenDTO) {
        String token = tokenDTO.getToken();
        em.createQuery("delete from TokenEntity where token=:token")
                .setParameter("token", token)
                .executeUpdate();
    }
}
