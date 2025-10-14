package DTO;
import  lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomTypeDTO {

    private Long id;
    private String model;
    private Double basePrice;
    private Double cardPrice;


}
