package lab4.backend.data.repositories.result.postgres;

import lab4.backend.data.entities.ResultEntity;
import lab4.backend.dto.UserDTO;

import java.util.List;

public interface ResultRepository {
    void saveResult(ResultEntity entity);
    List<ResultEntity> getAllResults();
    List<ResultEntity> getAllResultsForUser(Integer userId);
    void deleteAllResults();
    void deleteAllForUser(Integer userId);
}
