package com.nextgendevs.hanchor.io.repository;

import com.nextgendevs.hanchor.io.entity.AffirmationEntity;
import com.nextgendevs.hanchor.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AffirmationRepository extends PagingAndSortingRepository<AffirmationEntity, Long> {

    List<AffirmationEntity> findAllByUserEntityAffirmationOrderByIdAsc(UserEntity userEntity);

    Page<AffirmationEntity> findAllByUserEntityAffirmation(UserEntity userEntity, Pageable pageable);

    Page<AffirmationEntity> findAffirmationEntityByUserEntityAffirmation(UserEntity userEntity, Pageable pageable);

    Page<AffirmationEntity> findByUserEntityAffirmation(UserEntity userEntity, Pageable pageable);

    AffirmationEntity findById(long affirmationId);
}
