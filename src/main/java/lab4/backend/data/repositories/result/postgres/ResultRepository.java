package lab4.backend.data.repositories.result.postgres;

import lab4.backend.data.entities.ResultEntity;

import java.util.List;

public interface ResultRepository {
    void saveResult(ResultEntity entity);
    List<ResultEntity> getAllResults();
    void deleteAllResults();
}
