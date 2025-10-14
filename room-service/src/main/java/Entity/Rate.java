package Entity;

import jakarta.persistence.*;
import  lombok.*;

@Entity
@Data
@Table(name = "rate")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    private Long id;

    @Column(name = "rate_mode", nullable = false , length = 50)
    private String mode;

    @Column(name = "rate_price_base", nullable = false)
    private Double basePrice;

    @Column(name = "rate_price_card" , nullable = false)
    private Double cardPrice;

}
