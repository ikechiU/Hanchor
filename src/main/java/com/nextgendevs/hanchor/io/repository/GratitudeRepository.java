package com.nextgendevs.hanchor.io.repository;

import com.nextgendevs.hanchor.io.entity.GratitudeEntity;
import com.nextgendevs.hanchor.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GratitudeRepository extends PagingAndSortingRepository<GratitudeEntity, Long> {

    List<GratitudeEntity> findAllByUserEntityGratitudeOrderByIdAsc(UserEntity userEntity);

    Page<GratitudeEntity> findAllByUserEntityGratitudeOrderByIdAsc(UserEntity userEntity, Pageable pageable);

    GratitudeEntity findById(long gratitudeId);
}
