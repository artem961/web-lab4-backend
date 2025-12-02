package lab4.backend.data.repositories.result.postgres;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lab4.backend.data.entities.ResultEntity;
import java.util.List;


@Singleton
public class StandartPostgresResultRepository implements PostgresResultRepository {
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
    public void deleteAllResults() {
        em.createQuery("DELETE FROM ResultEntity").executeUpdate();
    }
}
