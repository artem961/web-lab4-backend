package lab4.backend.api.utils.mapping;

import lab4.backend.api.models.auth.response.TokenPairResponseModel;
import lab4.backend.api.models.result.response.ResultResponseModel;
import lab4.backend.dto.ResultDTO;
import lab4.backend.dto.TokenPairDTO;

public class ResultMapper {
    public static ResultResponseModel resultDtoToResponseModel(ResultDTO resultDTO) {
        ResultResponseModel responseModel = new ResultResponseModel();

        responseModel.setX(resultDTO.getX());
        responseModel.setY(resultDTO.getY());
        responseModel.setR(resultDTO.getR());
        responseModel.setResult(resultDTO.getResult());
        responseModel.setTime(resultDTO.getTime());
        responseModel.setCurrentTime(resultDTO.getCurrentTime());

        return responseModel;
    }
}
