package com.nextgendevs.hanchor.service;

import com.nextgendevs.hanchor.shared.dto.LifeHackDto;

import java.util.List;

public interface LifeHackService {

    LifeHackDto createLifeHack(String userId, LifeHackDto lifeHackDto);

    LifeHackDto getLifeHack(String userId, long lifeHackId);

    LifeHackDto updateLifeHack(String userId, long lifeHackId, LifeHackDto lifeHackDto);

    List<LifeHackDto> getLifeHacks(String userId, int page, int limit);

    void deleteLifeHack(String userId, long lifeHackId);
}
