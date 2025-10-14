package com.alocode.room_service.service.impl;

import com.alocode.room_service.dto.request.RateRequestDTO;
import com.alocode.room_service.dto.response.RateResponseDTO;
import com.alocode.room_service.entity.Rate;
import com.alocode.room_service.exception.NotFoundException;
import com.alocode.room_service.repository.RateRepository;
import com.alocode.room_service.service.RateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RateServiceImpl implements RateService {

    private final RateRepository rateRepository;

    @Override
    public List<RateResponseDTO> findAll() {
        return rateRepository.findAll()
                .stream()
                .map(rate -> RateResponseDTO.builder()
                        .id(rate.getId())
                        .mode(rate.getMode())
                        .basePrice(rate.getBasePrice())
                        .cardPrice(rate.getCardPrice())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public RateResponseDTO findById(Long id) {
        Rate rate = rateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tarifa no encontrada"));
        return RateResponseDTO.builder()
                .id(rate.getId())
                .mode(rate.getMode())
                .basePrice(rate.getBasePrice())
                .cardPrice(rate.getCardPrice())
                .build();
    }

    @Override
    public RateResponseDTO create(RateRequestDTO dto) {
        Rate rate = Rate.builder()
                .mode(dto.getMode())
                .basePrice(dto.getBasePrice())
                .cardPrice(dto.getCardPrice())
                .build();

        rateRepository.save(rate);

        return RateResponseDTO.builder()
                .id(rate.getId())
                .mode(rate.getMode())
                .basePrice(rate.getBasePrice())
                .cardPrice(rate.getCardPrice())
                .build();
    }

    @Override
    public RateResponseDTO update(Long id, RateRequestDTO dto) {
        Rate rate = rateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tarifa no encontrada"));

        rate.setMode(dto.getMode());
        rate.setBasePrice(dto.getBasePrice());
        rate.setCardPrice(dto.getCardPrice());

        rateRepository.save(rate);

        return RateResponseDTO.builder()
                .id(rate.getId())
                .mode(rate.getMode())
                .basePrice(rate.getBasePrice())
                .cardPrice(rate.getCardPrice())
                .build();
    }

    @Override
    public void delete(Long id) {
        Rate rate = rateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tarifa no encontrada"));

        rateRepository.delete(rate);
    }
}
