package lab4.backend.api.models.response.result;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResultResponseModel {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private Boolean result;
    private Long time;
    private LocalDateTime currentTime;
}
