package lab4.backend.data.repositories.token.postgres;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import lab4.backend.data.entities.TokenEntity;

@Singleton
public class SQLTokenRepository implements TokenRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public TokenEntity saveToken(TokenEntity token) {
        em.persist(token);
        em.flush();
        return token;
    }

    @Override
    public Boolean existsByToken(TokenEntity token) {
        String tokenString = token.getToken();

        try {
            TokenEntity foundToken = em.createQuery(
                            "SELECT t FROM TokenEntity t WHERE t.token = :token",
                            TokenEntity.class
                    )
                    .setParameter("token", tokenString)
                    .getSingleResult();

            return foundToken != null;

        } catch (NoResultException e) {
            return false;
        }
    }

    @Override
    public void delete(TokenEntity token) {
        String tokenString = token.getToken();
        em.createQuery("delete from TokenEntity where token=:token")
                .setParameter("token", tokenString)
                .executeUpdate();
    }
}
