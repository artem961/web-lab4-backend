package lab4.backend.data.repositories.result.postgres;

import lab4.backend.data.entities.ResultEntity;
import lab4.backend.dto.ResultDTO;

import java.util.List;

public interface PostgresResultRepository {
    void saveResult(ResultEntity entity);
    List<ResultEntity> getAllResults();
    void deleteAllResults();
}
