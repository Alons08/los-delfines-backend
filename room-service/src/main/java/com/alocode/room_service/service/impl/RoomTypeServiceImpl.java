package com.alocode.room_service.service.impl;

import com.alocode.room_service.dto.request.RoomTypeRequestDTO;
import com.alocode.room_service.dto.response.RoomTypeResponseDTO;
import com.alocode.room_service.entity.Rate;
import com.alocode.room_service.entity.RoomType;
import com.alocode.room_service.exception.NotFoundException;
import com.alocode.room_service.repository.RateRepository;
import com.alocode.room_service.repository.RoomTypeRepository;
import com.alocode.room_service.service.RoomTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomTypeServiceImpl implements RoomTypeService {

    private  final RoomTypeRepository roomTypeRepository;
    private final RateRepository rateRepository;


    @Override
    public List<RoomTypeResponseDTO> findAll() {
        return roomTypeRepository.findAll()
                .stream()
                .map(roomType -> RoomTypeResponseDTO.builder()
                        .id(roomType.getId())
                        .name(roomType.getName())
                        .rateDescription( roomType.getRate() != null  ? "Tarifa : " +roomType.getRate().getBasePrice() : "Sin tarifa"
                        )
                        .build())
                .collect(Collectors.toList());

    }

    @Override
    public RoomTypeResponseDTO findById(Long id) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de habitación no encontrado"));
        return RoomTypeResponseDTO.builder()
                .id(roomType.getId())
                .name(roomType.getName())
                .rateDescription(roomType.getRate() != null
                        ? "Tarifa: " + roomType.getRate().getBasePrice()
                        : "Sin tarifa")
                .build();
    }

    @Override
    public RoomTypeResponseDTO create(RoomTypeRequestDTO dto) {
        Rate rate = rateRepository.findById(dto.getRateId())
                .orElseThrow(() -> new NotFoundException("Tarifa no encontrada"));

        RoomType roomType = RoomType.builder()
                .name(dto.getName())
                .rate(rate)
                .build();

        roomTypeRepository.save(roomType);

        return RoomTypeResponseDTO.builder()
                .id(roomType.getId())
                .name(roomType.getName())
                .rateDescription("Tarifa: " + rate.getBasePrice())
                .build();
    }


    @Override
    public RoomTypeResponseDTO update(Long id, RoomTypeRequestDTO dto) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de habitación no encontrado"));
        Rate rate = rateRepository.findById(dto.getRateId())
                .orElseThrow(() -> new NotFoundException("Tarifa no encontrada"));

        roomType.setName(dto.getName());
        roomType.setRate(rate);

        roomTypeRepository.save(roomType);

        return RoomTypeResponseDTO.builder()
                .id(roomType.getId())
                .name(roomType.getName())
                .rateDescription("Tarifa: " + rate.getBasePrice())
                .build();
    }

    @Override
    public void delete(Long id) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tipo de habitación no encontrado"));
        roomTypeRepository.delete(roomType);
    }
}
