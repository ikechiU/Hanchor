package com.nextgendevs.hanchor.io.repository;

import com.nextgendevs.hanchor.io.entity.LifeHackEntity;
import com.nextgendevs.hanchor.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LifeHackRepository extends PagingAndSortingRepository<LifeHackEntity, Long> {

    LifeHackEntity findById(long lifeHackId);
}
