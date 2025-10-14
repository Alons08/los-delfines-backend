package com.alocode.room_service.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateResponseDTO {

    private  Long id;
    private Integer mode;
    private BigDecimal basePrice;
    private BigDecimal cardPrice;
}
