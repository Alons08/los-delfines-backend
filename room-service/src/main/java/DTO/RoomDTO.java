package DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomDTO {

    private  Long id;
    private String number;
    private Integer capacity;
    private String description;
    private String state;
    private Long roomTypeId;

}
