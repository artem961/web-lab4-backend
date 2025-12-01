package lab4.backend.data.repositories.result.postgres;

import lab4.backend.dto.ResultDTO;

import java.util.List;

public interface PostgresResultRepository {
    public void saveResult(ResultDTO resultDTO);
    public List<ResultDTO> getAllResults();
    public void deleteAllResults();
}
