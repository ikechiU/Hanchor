package com.nextgendevs.hanchor.io.repository;

import com.nextgendevs.hanchor.io.entity.PasswordResetTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity, Long>{
    PasswordResetTokenEntity findByToken(String token);
}
