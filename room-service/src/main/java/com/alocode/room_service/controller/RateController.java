package com.alocode.room_service.controller;
import com.alocode.room_service.dto.request.RateRequestDTO;
import com.alocode.room_service.dto.request.RoomRequestDTO;
import com.alocode.room_service.dto.request.RoomTypeRequestDTO;
import com.alocode.room_service.dto.response.RateResponseDTO;
import com.alocode.room_service.dto.response.RoomResponseDTO;
import com.alocode.room_service.dto.response.RoomTypeResponseDTO;
import com.alocode.room_service.service.RateService;
import com.alocode.room_service.service.RoomService;
import com.alocode.room_service.service.RoomTypeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RateController {

    private  final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> getAll() {
        return ResponseEntity.ok(roomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RoomResponseDTO> create(@RequestBody RoomRequestDTO dto) {
        return ResponseEntity.ok(roomService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponseDTO> update(@PathVariable Long id, @RequestBody RoomRequestDTO dto) {
        return ResponseEntity.ok(roomService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{roomTypeId}")
    public ResponseEntity<List<RoomResponseDTO>> getByType(@PathVariable Long roomTypeId) {
        return ResponseEntity.ok(roomService.findByRoomType(roomTypeId));
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<RoomResponseDTO>> getByState(@PathVariable String state) {
        return ResponseEntity.ok(roomService.findByState(state));
    }

}
