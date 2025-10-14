package com.alocode.room_service.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomTypeResponseDTO {
 private  Long id;
 private String name;
 private String rateDescription;
}
