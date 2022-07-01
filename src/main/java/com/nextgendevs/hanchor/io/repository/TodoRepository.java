package com.nextgendevs.hanchor.io.repository;

import com.nextgendevs.hanchor.io.entity.TodoEntity;
import com.nextgendevs.hanchor.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends PagingAndSortingRepository<TodoEntity, Long> {

    List<TodoEntity> findAllByUserEntityTodoOrderByIdAsc(UserEntity userEntity);

    Page<TodoEntity> findAllByUserEntityTodoOrderByIdAsc(UserEntity userEntity, Pageable pageable);

    TodoEntity findById(long todoId);
}
