package com.alocode.room_service.entity;

import com.alocode.room_service.entity.RoomState;
import com.alocode.room_service.entity.RoomType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "room")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(name = "room_number", nullable = false, length = 3)
    private String number;

    @Column(name = "room_maximum_capacity", nullable = false)
    private Integer capacity;

    @Column(name = "room_description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_state", nullable = false, length = 20)
    private RoomState state;

    @ManyToOne
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomType roomType;
}
