package com.alocode.room_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_type_id")
    private Long id;


    @Column(name="room_type_name", nullable = false , length = 40)
    private String name;

    @OneToOne
    @JoinColumn(name = "rate_id")
    private Rate rate;

}

