package lab4.backend.data.repositories.token.postgres;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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

        try {
            TokenEntity foundToken = em.createQuery(
                            "SELECT t FROM TokenEntity t WHERE t.token = :token",
                            TokenEntity.class
                    )
                    .setParameter("token", token)
                    .getSingleResult();

            return foundToken != null;

        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public void delete(TokenDTO tokenDTO) {
        String token = tokenDTO.getToken();
        em.createQuery("delete from TokenEntity where token=:token")
                .setParameter("token", token)
                .executeUpdate();
    }
}
