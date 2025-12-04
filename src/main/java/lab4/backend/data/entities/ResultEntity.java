package lab4.backend.data.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "results")
public class ResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;
    private Boolean result;
    private Long time;
    private LocalDateTime currentTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private UserEntity user;
}
