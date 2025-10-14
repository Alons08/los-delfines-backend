package com.alocode.room_service.service;

import java.util.*;
import com.alocode.room_service.dto.response.*;
import com.alocode.room_service.dto.request.*;

public interface RoomTypeService  {

    List<RoomTypeResponseDTO> findAll();

    RoomTypeResponseDTO findById(Long id);

    RoomTypeResponseDTO create(RoomTypeRequestDTO dto);

    RoomTypeResponseDTO update(Long id, RoomTypeRequestDTO dto);

    void delete(Long id);
}
