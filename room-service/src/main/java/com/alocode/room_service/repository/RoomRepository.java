package com.alocode.room_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;
import com.alocode.room_service.entity.*;
import java.util.*;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByState(RoomState state);

    List<Room> findByRoomType_Id(Long roomTypeId);
}
