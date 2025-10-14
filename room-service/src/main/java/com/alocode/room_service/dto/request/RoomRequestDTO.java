package com.alocode.room_service.dto.request;

import com.alocode.room_service.entity.RoomState;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRequestDTO {
    private  String number;
    private Integer capacity;
    private String description;
    private RoomState state;
    private Long roomTypeId;
}
