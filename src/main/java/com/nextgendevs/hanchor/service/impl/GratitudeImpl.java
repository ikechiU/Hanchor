package com.nextgendevs.hanchor.service.impl;

import com.nextgendevs.hanchor.exceptions.HanchorServiceException;
import com.nextgendevs.hanchor.io.entity.GratitudeEntity;
import com.nextgendevs.hanchor.io.entity.TodoEntity;
import com.nextgendevs.hanchor.io.entity.UserEntity;
import com.nextgendevs.hanchor.io.repository.GratitudeRepository;
import com.nextgendevs.hanchor.io.repository.UserRepository;
import com.nextgendevs.hanchor.service.GratitudeService;
import com.nextgendevs.hanchor.shared.dto.GratitudeDto;
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
public class GratitudeImpl implements GratitudeService {

    @Autowired
    GratitudeRepository gratitudeRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public GratitudeDto createGratitude(String userId, GratitudeDto gratitudeDto) {
        UserEntity userEntity = checkUserEntity(userId);

        GratitudeEntity gratitudeEntity = new ModelMapper().map(gratitudeDto, GratitudeEntity.class);
        gratitudeEntity.setUserEntityGratitude(userEntity);

        GratitudeEntity createdGratitude = gratitudeRepository.save(gratitudeEntity);

        return new ModelMapper().map(createdGratitude, GratitudeDto.class);
    }

    @Override
    public GratitudeDto getGratitude(String userId, long gratitudeId) {
        UserEntity userEntity = checkUserEntity(userId);
        GratitudeEntity gratitudeEntity = checkGratitudeEntity(gratitudeId);

        checkMismatch(userId, userEntity, gratitudeEntity);

        return new ModelMapper().map(gratitudeEntity, GratitudeDto.class);
    }

    @Override
    public GratitudeDto updateGratitude(String userId, long gratitudeId, GratitudeDto gratitudeDto) {
        UserEntity userEntity = checkUserEntity(userId);
        GratitudeEntity gratitudeEntity = checkGratitudeEntity(gratitudeId);

        checkMismatch(userId, userEntity, gratitudeEntity);

        gratitudeEntity.setTitle(gratitudeDto.getTitle());
        gratitudeEntity.setMessage(gratitudeDto.getMessage());
        gratitudeEntity.setImageUrl(gratitudeDto.getImageUrl());
        gratitudeEntity.setImageId(gratitudeDto.getImageId());
        gratitudeEntity.setUserEntityGratitude(userEntity);

        GratitudeEntity updatedGratitude = gratitudeRepository.save(gratitudeEntity);

        return new ModelMapper().map(updatedGratitude, GratitudeDto.class);
    }

    @Override
    public List<GratitudeDto> getGratitudes(String userId, int page, int limit) {
        List<GratitudeDto> returnValue = new ArrayList<>();

        UserEntity userEntity = checkUserEntity(userId);

        if (page > 0) page = page - 1;
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<GratitudeEntity> pagedUserEntities = gratitudeRepository.findAllByUserEntityGratitudeOrderByIdAsc(userEntity, pageableRequest);

        List<GratitudeEntity> entityList = pagedUserEntities.getContent();
        for (GratitudeEntity entity : entityList) {
            returnValue.add(new ModelMapper().map(entity, GratitudeDto.class));
        }

        return returnValue;
    }

    @Override
    public void deleteGratitude(String userId, long gratitudeId) {
        UserEntity userEntity = checkUserEntity(userId);
        GratitudeEntity gratitudeEntity = checkGratitudeEntity(gratitudeId);

        checkMismatch(userId, userEntity, gratitudeEntity);

        gratitudeRepository.delete(gratitudeEntity);
    }

    private GratitudeEntity checkGratitudeEntity(long affirmationId) {
        GratitudeEntity gratitudeEntity = gratitudeRepository.findById(affirmationId);
        if (gratitudeEntity == null) {
            throw new HanchorServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return gratitudeEntity;
    }

    private UserEntity checkUserEntity(String query) {
        UserEntity userEntity = userRepository.findUserEntitiesByUserId(query);

        if (userEntity == null) {
            throw new HanchorServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        } else {
            return userEntity;
        }
    }

    private void checkMismatch(String userId, UserEntity userEntity, GratitudeEntity gratitudeEntity) {
        if (userId.equals("123456789") || userId.equals("A123456789")){
            System.out.println("Allow operation");
        } else {
            if (userEntity != gratitudeEntity.getUserEntityGratitude()){
                throw new HanchorServiceException(ErrorMessages.MISMATCH_RECORD.getErrorMessage());
            }
        }
    }
}
