package com.nextgendevs.hanchor.io.repository;

import com.nextgendevs.hanchor.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findUserEntitiesByUserId(String userId);

    UserEntity findUserEntitiesByEmail(String email);

    @Query(value = "SELECT * from user_tb u WHERE u.username = :username", nativeQuery = true)
    List<UserEntity> findAllByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM user_tb u WHERE u.username LIKE '%:username%'", nativeQuery = true)
    Page<UserEntity> findAllByUsername(@Param("username") String username, Pageable pageable);

    Page<UserEntity> findAllByUsernameOrderByIdAsc(@Param("username") String username, Pageable pageable);

    Page<UserEntity> findAllByFirstnameLikeOrLastnameLikeOrUsernameLikeOrEmailLike(
            String firstname, String lastname, String username, String email, Pageable pageable);


    UserEntity findUserEntityByEmailVerificationToken(String token);

}
