package com.alocode.room_service.dto.response;

import com.alocode.room_service.entity.RoomState;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponseDTO {

    private Long id;
    private String number;
    private Integer capacity;
    private RoomState state;
    private String description;
    private String roomTypeName;

}
