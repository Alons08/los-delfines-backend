package com.alocode.room_service.repository;

import com.alocode.room_service.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository  extends JpaRepository<RoomType, Long> {

}
