package com.nextgendevs.hanchor.io.repository;

import com.nextgendevs.hanchor.io.entity.RolesEntity;
import org.springframework.data.repository.CrudRepository;

public interface RolesRepository extends CrudRepository<RolesEntity, Long> {
    RolesEntity findByName(String name);
}
