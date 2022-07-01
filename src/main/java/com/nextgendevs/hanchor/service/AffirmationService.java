package com.nextgendevs.hanchor.service;

import com.nextgendevs.hanchor.shared.dto.AffirmationDto;

import java.util.List;

public interface AffirmationService {

    AffirmationDto createAffirmation(String userId, AffirmationDto affirmationDto);

    AffirmationDto getAffirmation(String userId, long affirmationId);

    AffirmationDto updateAffirmation(String userId, long affirmationId, AffirmationDto affirmationDto);

    List<AffirmationDto> getAffirmations(String userId, int page, int limit);

    void deleteAffirmation(String userId, long affirmationId);
}
