package lab4.backend.api.models;

import lab4.backend.dto.ResultDTO;
import lab4.backend.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultResponseModel {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private Boolean result;
    private Long time;
    private LocalDateTime currentTime;

    public static ResultResponseModel fromResultDTO(ResultDTO resultDTO) {
       return ResultResponseModel.builder()
                .x(resultDTO.getX())
                .y(resultDTO.getY())
                .r(resultDTO.getR())
                .result(resultDTO.getResult())
                .currentTime(resultDTO.getCurrentTime())
                .time(resultDTO.getTime())
                .build();
    }
}
