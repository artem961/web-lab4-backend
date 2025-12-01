package lab4.backend.api.models.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class DotRequestModel {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
}
