package lab4.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckHitResultDTO {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private Boolean result;
    private Long time;
    private LocalDateTime currentTime;
}
