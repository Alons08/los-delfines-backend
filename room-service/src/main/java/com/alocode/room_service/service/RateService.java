package com.alocode.room_service.service;

import java.util.*;
import com.alocode.room_service.dto.response.*;
import com.alocode.room_service.dto.request.*;

public interface RateService {

    List<RateResponseDTO> findAll();

    RateResponseDTO findById(Long id);

    RateResponseDTO create(RateRequestDTO dto);

    RateResponseDTO update(Long id, RateRequestDTO dto);

    void delete(Long id);

}
