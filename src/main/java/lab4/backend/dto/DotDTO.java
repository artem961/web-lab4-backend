package lab4.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DotDTO {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
}
