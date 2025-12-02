package lab4.backend.data.entities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "refresh_tokens")
@Data
public class TokenEntity {
    @Id
    private String token;
}
