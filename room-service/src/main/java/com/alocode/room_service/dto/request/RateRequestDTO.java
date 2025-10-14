package com.alocode.room_service.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateRequestDTO {
    private  Integer mode;
    private BigDecimal basePrice;
    private BigDecimal cardPrice;
}
