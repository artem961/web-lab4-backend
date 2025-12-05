package lab4.backend.data.repositories.result.postgres;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lab4.backend.data.entities.ResultEntity;

import java.util.List;


@Singleton
public class SQLResultRepository implements ResultRepository {
    @PersistenceContext
    private EntityManager em;


    @Override
    public void saveResult(ResultEntity entity) {
        em.persist(entity);
    }

    @Override
    public List<ResultEntity> getAllResults() {
        List<ResultEntity> results = em.createQuery(
                "SELECT r FROM ResultEntity r",
                ResultEntity.class).getResultList();

        return results;
    }

    @Override
    public List<ResultEntity> getAllResultsForUser(Integer userId) {
        List<ResultEntity> results = em.createQuery(
                        "SELECT r FROM ResultEntity r WHERE r.user.id=:userId", ResultEntity.class)
                .setParameter("userId", userId)
                .getResultList();

        return results;
    }

    @Override
    public void deleteAllResults() {
        em.createQuery("DELETE FROM ResultEntity").executeUpdate();
    }

    @Override
    public void deleteAllForUser(Integer userId) {
        em.createQuery("DELETE FROM ResultEntity r WHERE r.user.id=:userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }
}
