package lab4.backend.utils.mapping;

import lab4.backend.api.models.request.result.DotRequestModel;
import lab4.backend.dto.DotDTO;

public class DotMapper {
    public static DotDTO requestModelToDTO(DotRequestModel requestModel) {
        DotDTO dotDTO = new DotDTO();

        dotDTO.setX(requestModel.getX());
        dotDTO.setY(requestModel.getY());
        dotDTO.setR(requestModel.getR());

        return dotDTO;
    }
}
