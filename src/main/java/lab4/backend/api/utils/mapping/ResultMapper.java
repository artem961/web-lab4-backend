package lab4.backend.api.utils.mapping;

import lab4.backend.api.models.result.request.DotRequestModel;
import lab4.backend.api.models.result.response.ResultResponseModel;
import lab4.backend.dto.DotDTO;
import lab4.backend.dto.ResultDTO;

public class ResultMapper {
    public static ResultResponseModel resultDtoToResultResponseModel(ResultDTO resultDTO) {
        ResultResponseModel responseModel = new ResultResponseModel();

        responseModel.setX(resultDTO.getX());
        responseModel.setY(resultDTO.getY());
        responseModel.setR(resultDTO.getR());
        responseModel.setResult(resultDTO.getResult());
        responseModel.setTime(resultDTO.getTime());
        responseModel.setCurrentTime(resultDTO.getCurrentTime());

        return responseModel;
    }

    public static DotDTO dotRequestModelToDotDTO(DotRequestModel requestModel) {
        DotDTO dotDTO = DotDTO.builder()
                .x(requestModel.getX())
                .y(requestModel.getY())
                .r(requestModel.getR())
                .build();
        return dotDTO;
    }
}
