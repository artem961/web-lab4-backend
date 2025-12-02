package lab4.backend.services;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import lab4.backend.data.repositories.result.postgres.PostgresResultRepository;
import lab4.backend.dto.ResultDTO;
import lab4.backend.dto.DotDTO;
import lab4.backend.services.utils.HitChecker;
import lab4.backend.utils.mapping.ResultMapper;

import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ResultService {
    @EJB
    private PostgresResultRepository postgresResultRepository;

    public ResultDTO checkHit(DotDTO dotDTO){
        ResultDTO resultDTO = HitChecker.checkHit(dotDTO);
        postgresResultRepository.saveResult(
                ResultMapper.dtoToEntity(resultDTO));
        return resultDTO;
    }

    public List<ResultDTO> getAllResults(){
        return postgresResultRepository.getAllResults().stream()
                .map(ResultMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    public void deleteAllResults(){
        postgresResultRepository.deleteAllResults();
    }
}
