package com.alocode.room_service.service.impl;


import com.alocode.room_service.dto.request.RoomRequestDTO;
import com.alocode.room_service.dto.response.RoomResponseDTO;
import com.alocode.room_service.entity.Room;
import com.alocode.room_service.entity.RoomState;
import com.alocode.room_service.entity.RoomType;
import com.alocode.room_service.exception.NotFoundException;
import com.alocode.room_service.repository.RoomRepository;
import com.alocode.room_service.repository.RoomTypeRepository;
import com.alocode.room_service.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;

    @Override
    public List<RoomResponseDTO> findAll() {
        return roomRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoomResponseDTO findById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Habitación no encontrada"));
        return convertToDTO(room);
    }

    @Override
    public RoomResponseDTO create(RoomRequestDTO dto) {
        RoomType roomType = roomTypeRepository.findById(dto.getRoomTypeId())
                .orElseThrow(() -> new NotFoundException("Tipo de habitación no encontrado"));

        Room room = Room.builder()
                .number(dto.getNumber())
                .capacity(dto.getCapacity())
                .description(dto.getDescription())
                .state(dto.getState())
                .roomType(roomType)
                .build();

        roomRepository.save(room);
        return convertToDTO(room);
    }

    @Override
    public RoomResponseDTO update(Long id, RoomRequestDTO dto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Habitación no encontrada"));

        RoomType roomType = roomTypeRepository.findById(dto.getRoomTypeId())
                .orElseThrow(() -> new NotFoundException("Tipo de habitación no encontrado"));

        room.setNumber(dto.getNumber());
        room.setCapacity(dto.getCapacity());
        room.setDescription(dto.getDescription());
        room.setState(dto.getState());
        room.setRoomType(roomType);

        roomRepository.save(room);
        return convertToDTO(room);
    }

    @Override
    public void delete(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Habitación no encontrada"));
        roomRepository.delete(room);
    }

    @Override
    public List<RoomResponseDTO> findByRoomType(Long roomTypeId) {
        return roomRepository.findByRoomType_Id(roomTypeId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomResponseDTO> findByState(String state) {
        RoomState roomState = RoomState.valueOf(state.toUpperCase());
        return roomRepository.findByState(roomState)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método privado para evitar duplicación
    private RoomResponseDTO convertToDTO(Room room) {
        return RoomResponseDTO.builder()
                .id(room.getId())
                .number(room.getNumber())
                .capacity(room.getCapacity())
                .description(room.getDescription())
                .state(room.getState())
                .roomTypeName(room.getRoomType().getName())
                .build();
    }
}
