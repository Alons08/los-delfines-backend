package com.alocode.room_service.controller;
import com.alocode.room_service.dto.request.RateRequestDTO;
import com.alocode.room_service.dto.request.RoomTypeRequestDTO;
import com.alocode.room_service.dto.response.RateResponseDTO;
import com.alocode.room_service.dto.response.RoomTypeResponseDTO;
import com.alocode.room_service.service.RateService;
import com.alocode.room_service.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rates")
@RequiredArgsConstructor
public class RoomController {

    private final RateService rateService;

    @GetMapping
    public ResponseEntity<List<RateResponseDTO>> getAll() {
        return ResponseEntity.ok(rateService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RateResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(rateService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RateResponseDTO> create(@RequestBody RateRequestDTO dto) {
        return ResponseEntity.ok(rateService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RateResponseDTO> update(@PathVariable Long id, @RequestBody RateRequestDTO dto) {
        return ResponseEntity.ok(rateService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
