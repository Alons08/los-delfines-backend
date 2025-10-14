package com.alocode.room_service.controller;


import com.alocode.room_service.dto.request.RoomTypeRequestDTO;
import com.alocode.room_service.dto.response.RoomTypeResponseDTO;
import com.alocode.room_service.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/room-types")
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @GetMapping
    public ResponseEntity<List<RoomTypeResponseDTO>> getAll() {
        return ResponseEntity.ok(roomTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roomTypeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RoomTypeResponseDTO> create(@RequestBody RoomTypeRequestDTO dto) {
        return ResponseEntity.ok(roomTypeService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomTypeResponseDTO> update(@PathVariable Long id, @RequestBody RoomTypeRequestDTO dto) {
        return ResponseEntity.ok(roomTypeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }




}
