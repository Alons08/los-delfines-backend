package com.alocode.room_service.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomTypeRequestDTO {
    private  String name;
    private Long rateId;
}
