package com.alocode.room_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    private  long Id;

    @Column(name = "rate_mode" , nullable = false)
    private Integer mode;

    @Column(name = "rate_price_base" ,  precision = 10 , scale = 2)
    private BigDecimal basePrice;

    @Column(name = "rate_price_card" , precision = 10 , scale = 2)
    private BigDecimal cardPrice;

}
