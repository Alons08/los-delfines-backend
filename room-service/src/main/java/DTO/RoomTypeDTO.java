package DTO;
import  lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomTypeDTO {

    private Long id;
    private String name;
    private Long rateId;


}
