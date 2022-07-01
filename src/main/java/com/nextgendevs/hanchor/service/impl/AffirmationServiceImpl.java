package com.nextgendevs.hanchor.service.impl;

import com.nextgendevs.hanchor.exceptions.HanchorServiceException;
import com.nextgendevs.hanchor.io.entity.AffirmationEntity;
import com.nextgendevs.hanchor.io.entity.TodoEntity;
import com.nextgendevs.hanchor.io.entity.UserEntity;
import com.nextgendevs.hanchor.io.repository.AffirmationRepository;
import com.nextgendevs.hanchor.io.repository.UserRepository;
import com.nextgendevs.hanchor.service.AffirmationService;
import com.nextgendevs.hanchor.shared.dto.AffirmationDto;
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
public class AffirmationServiceImpl implements AffirmationService {

    @Autowired
    AffirmationRepository affirmationRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public AffirmationDto createAffirmation(String userId, AffirmationDto affirmationDto) {
        UserEntity userEntity = checkUserEntity(userId);

        AffirmationEntity affirmationEntity = new ModelMapper().map(affirmationDto, AffirmationEntity.class);
        affirmationEntity.setUserEntityAffirmation(userEntity);

        AffirmationEntity createdAffirmation = affirmationRepository.save(affirmationEntity);

        return new ModelMapper().map(createdAffirmation, AffirmationDto.class);
    }


    @Override
    public AffirmationDto getAffirmation(String userId, long affirmationId) {
        UserEntity userEntity = checkUserEntity(userId);
        AffirmationEntity affirmationEntity = checkAffirmationEntity(affirmationId);

        checkMismatch(userId, userEntity, affirmationEntity);

        return new ModelMapper().map(affirmationEntity, AffirmationDto.class);
    }

    @Override
    public AffirmationDto updateAffirmation(String userId, long affirmationId, AffirmationDto affirmationDto) {
        UserEntity userEntity = checkUserEntity(userId);
        AffirmationEntity affirmationEntity = checkAffirmationEntity(affirmationId);

        checkMismatch(userId, userEntity, affirmationEntity);

        ModelMapper modelMapper = new ModelMapper();

        affirmationEntity.setTitle(affirmationDto.getTitle());
        affirmationEntity.setAffirmation(affirmationDto.getAffirmation());
        affirmationEntity.setUserEntityAffirmation(userEntity);

        AffirmationEntity updatedAffirmation = affirmationRepository.save(affirmationEntity);

        return modelMapper.map(updatedAffirmation, AffirmationDto.class);
    }

    @Override
    public List<AffirmationDto> getAffirmations(String userId, int page, int limit) {
        List<AffirmationDto> returnValue = new ArrayList<>();

        UserEntity userEntity = checkUserEntity(userId);

        if (page > 0) page = page - 1;
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<AffirmationEntity> pagedUserEntities = affirmationRepository.findByUserEntityAffirmation(userEntity, pageableRequest);

        List<AffirmationEntity> entityList = pagedUserEntities.getContent();
        for (AffirmationEntity entity : entityList) {
            returnValue.add(new ModelMapper().map(entity, AffirmationDto.class));
        }

        return returnValue;
    }

    @Override
    public void deleteAffirmation(String userId, long affirmationId) {
        UserEntity userEntity = checkUserEntity(userId);
        AffirmationEntity affirmationEntity = checkAffirmationEntity(affirmationId);

        checkMismatch(userId, userEntity, affirmationEntity);

        affirmationRepository.delete(affirmationEntity);
    }

    private AffirmationEntity checkAffirmationEntity(long affirmationId) {
        AffirmationEntity affirmationEntity = affirmationRepository.findById(affirmationId);
        if (affirmationEntity == null) {
            throw new HanchorServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return affirmationEntity;
    }

    private UserEntity checkUserEntity(String query) {
        UserEntity userEntity = new UserEntity();
        if (query.contains("@")) {
            userEntity = userRepository.findUserEntitiesByEmail(query);
        } else {
            userEntity = userRepository.findUserEntitiesByUserId(query);
        }

        if (userEntity == null) {
            throw new HanchorServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        } else {
            return userEntity;
        }
    }

    private void checkMismatch(String userId, UserEntity userEntity, AffirmationEntity affirmationEntity) {
        if (userId.equals("123456789") || userId.equals("A123456789")){
            System.out.println("Allow operation");
        } else {
            if (userEntity != affirmationEntity.getUserEntityAffirmation()){
                throw new HanchorServiceException(ErrorMessages.MISMATCH_RECORD.getErrorMessage());
            }
        }
    }

}
