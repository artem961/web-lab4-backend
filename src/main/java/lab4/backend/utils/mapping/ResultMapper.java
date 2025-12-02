package lab4.backend.utils.mapping;

import jakarta.ejb.Singleton;
import lab4.backend.api.models.result.response.ResultResponseModel;
import lab4.backend.data.entities.ResultEntity;
import lab4.backend.dto.ResultDTO;


public class ResultMapper {
    public static ResultDTO entityToDTO(ResultEntity resultEntity) {
        ResultDTO resultDTO = new ResultDTO();

        resultDTO.setX(resultEntity.getX());
        resultDTO.setY(resultEntity.getY());
        resultDTO.setR(resultEntity.getR());
        resultDTO.setResult(resultEntity.getResult());
        resultDTO.setTime(resultEntity.getTime());
        resultDTO.setCurrentTime(resultEntity.getCurrentTime());

        return resultDTO;
    }

    public static ResultEntity dtoToEntity(ResultDTO resultDTO) {
        ResultEntity resultEntity = new ResultEntity();

        resultEntity.setX(resultDTO.getX());
        resultEntity.setY(resultDTO.getY());
        resultEntity.setR(resultDTO.getR());
        resultEntity.setResult(resultDTO.getResult());
        resultEntity.setTime(resultDTO.getTime());
        resultEntity.setCurrentTime(resultDTO.getCurrentTime());

        return resultEntity;
    }
}
