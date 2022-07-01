package com.nextgendevs.hanchor.service.impl;

import com.nextgendevs.hanchor.exceptions.HanchorServiceException;
import com.nextgendevs.hanchor.io.entity.LifeHackEntity;
import com.nextgendevs.hanchor.io.entity.UserEntity;
import com.nextgendevs.hanchor.io.repository.LifeHackRepository;
import com.nextgendevs.hanchor.io.repository.UserRepository;
import com.nextgendevs.hanchor.service.LifeHackService;
import com.nextgendevs.hanchor.shared.dto.LifeHackDto;
import com.nextgendevs.hanchor.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LifeHackServiceImpl implements LifeHackService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LifeHackRepository lifeHackRepository;

    @Override
    public LifeHackDto createLifeHack(String userId, LifeHackDto lifeHackDto) {
        checkUserEntity(userId);
        LifeHackEntity lifeHackEntity = new ModelMapper().map(lifeHackDto, LifeHackEntity.class);

        LifeHackEntity lifeHack = lifeHackRepository.save(lifeHackEntity);

        return new ModelMapper().map(lifeHack, LifeHackDto.class);
    }

    @Override
    public LifeHackDto getLifeHack(String userId, long lifeHackId) {
        checkUserEntity(userId);
        LifeHackEntity todoEntity = checkLifeHackEntity(lifeHackId);

        return new ModelMapper().map(todoEntity, LifeHackDto.class);
    }

    @Override
    public LifeHackDto updateLifeHack(String userId, long lifeHackId, LifeHackDto lifeHackDto) {
        checkUserEntity(userId);
        LifeHackEntity lifeHackEntity = checkLifeHackEntity(lifeHackId);

        LifeHackEntity newLifeHack = new ModelMapper().map(lifeHackDto, LifeHackEntity.class);
        newLifeHack.setId(lifeHackEntity.getId());

        LifeHackEntity updatedLifeHack = lifeHackRepository.save(newLifeHack);

        return new ModelMapper().map(updatedLifeHack, LifeHackDto.class);
    }

    @Override
    public List<LifeHackDto> getLifeHacks(String userId, int page, int limit) {
        List<LifeHackDto> returnValue = new ArrayList<>();
        checkUserEntity(userId);

        if (page > 0) page = page - 1;
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<LifeHackEntity> pagedEntities = lifeHackRepository.findAll(pageableRequest);

        List<LifeHackEntity> entityList = pagedEntities.getContent();
        for (LifeHackEntity entity : entityList) {
            returnValue.add(new ModelMapper().map(entity, LifeHackDto.class));
        }

        return returnValue;
    }

    @Override
    public void deleteLifeHack(String userId, long lifeHackId) {
        checkUserEntity(userId);
        LifeHackEntity lifeHackEntity = checkLifeHackEntity(lifeHackId);

        lifeHackRepository.delete(lifeHackEntity);
    }

    private LifeHackEntity checkLifeHackEntity(long lifeHackId) {
        LifeHackEntity lifeHackEntity = lifeHackRepository.findById(lifeHackId);
        if (lifeHackEntity == null) {
            throw new HanchorServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return lifeHackEntity;
    }

    private void checkUserEntity(String query) {
        UserEntity userEntity = new UserEntity();
        if (query.contains("@")) {
            userEntity = userRepository.findUserEntitiesByEmail(query);
        } else {
            userEntity = userRepository.findUserEntitiesByUserId(query);
        }

        if (userEntity == null) {
            throw new HanchorServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        System.out.println(userEntity);
    }
}
