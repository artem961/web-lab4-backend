package lab4.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DotDTO {
    @NotNull
    @Min(value = -4)
    @Max(value = 4)
    private BigDecimal x;

    @NotNull
    @Min(value = -4)
    @Max(value = 4)
    private BigDecimal y;

    @NotNull
    @Min(value = -4)
    @Max(value = 4)
    private BigDecimal r;
}
