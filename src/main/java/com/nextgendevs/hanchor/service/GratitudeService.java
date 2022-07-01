package com.nextgendevs.hanchor.service;

import com.nextgendevs.hanchor.shared.dto.GratitudeDto;

import java.util.List;

public interface GratitudeService {

    GratitudeDto createGratitude(String userId, GratitudeDto gratitudeDto);

    GratitudeDto getGratitude(String userId, long gratitudeId);

    GratitudeDto updateGratitude(String userId, long gratitudeId, GratitudeDto gratitudeDto);

    List<GratitudeDto> getGratitudes(String userId, int page, int limit);

    void deleteGratitude(String userId, long gratitudeId);
}
