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
    private  Long id;

    @Column(nullable = false , length = 80)
    private String name;


    private  String description;

}
