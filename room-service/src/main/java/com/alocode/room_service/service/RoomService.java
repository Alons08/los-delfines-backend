package com.alocode.room_service.service;

import java.util.*;
import com.alocode.room_service.dto.response.*;
import com.alocode.room_service.dto.request.*;

public interface RoomService {

    List<RoomResponseDTO> findAll();

    RoomResponseDTO findById(Long id);

    RoomResponseDTO create(RoomRequestDTO dto);

    RoomResponseDTO update(Long id, RoomRequestDTO dto);

    void delete(Long id);

    // Extra: Filtrar por tipo
    List<RoomResponseDTO> findByRoomType(Long roomTypeId);

    // Extra: Filtrar por estado
    List<RoomResponseDTO> findByState(String state);

}
