package lab4.backend.services;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lab4.backend.data.repositories.result.postgres.PostgresResultRepository;
import lab4.backend.dto.ResultDTO;
import lab4.backend.dto.DotDTO;
import lab4.backend.services.utils.HitChecker;

import java.util.List;

@Stateless
public class ResultService {
    @EJB
    private PostgresResultRepository postgresResultRepository;

    public ResultDTO checkHit(DotDTO dotDTO){
        ResultDTO resultDTO = HitChecker.checkHit(dotDTO);
        postgresResultRepository.saveResult(resultDTO);
        return resultDTO;
    }

    public List<ResultDTO> getAllResults(){
        return postgresResultRepository.getAllResults();
    }

    public void deleteAllResults(){
        postgresResultRepository.deleteAllResults();
    }
}
