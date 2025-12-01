package lab4.backend.data.repositories.postgres;

import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lab4.backend.data.entities.ResultEntity;
import lab4.backend.dto.ResultDTO;
import lab4.backend.utils.mapping.ResultMapper;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class StandartPostgresResultRepository implements PostgresResultRepository {
    @EJB
    private ResultMapper mapper;
    @PersistenceContext
    private EntityManager em;


    @Override
    public void saveResult(ResultDTO resultDTO) {
        em.persist(mapper.dtoToEntity(resultDTO));
    }

    @Override
    public List<ResultDTO> getAllResults() {
        List<ResultEntity> results = em.createQuery(
                "SELECT r FROM ResultEntity r",
                ResultEntity.class).getResultList();

        return results.stream()
                .map(mapper::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllResults() {
        em.createQuery("DELETE FROM ResultEntity").executeUpdate();
    }
}
