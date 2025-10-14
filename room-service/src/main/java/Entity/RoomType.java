package Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room_type")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_type_id")
    private  Long id;

    @Column(name = "room_type_name", nullable = false , length = 80)
    private String name;


    @Column(name = "rate_id", nullable = false)
    private Long rateId;    // 🔁 Simple (solo ID, no objeto)

}
