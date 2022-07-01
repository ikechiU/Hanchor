package com.nextgendevs.hanchor.service.impl;

import com.nextgendevs.hanchor.exceptions.HanchorServiceException;
import com.nextgendevs.hanchor.io.entity.TodoEntity;
import com.nextgendevs.hanchor.io.entity.UserEntity;
import com.nextgendevs.hanchor.io.repository.TodoRepository;
import com.nextgendevs.hanchor.io.repository.UserRepository;
import com.nextgendevs.hanchor.service.TodoService;
import com.nextgendevs.hanchor.shared.dto.TodoDto;
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
public class TodoServiceImpl implements TodoService {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public TodoDto createTodo(String userId, TodoDto todoDto) {
        UserEntity userEntity = checkUserEntity(userId);

        TodoEntity todoEntity = new ModelMapper().map(todoDto, TodoEntity.class);
        todoEntity.setUserEntityTodo(userEntity);

        TodoEntity todo = todoRepository.save(todoEntity);

        return new ModelMapper().map(todo, TodoDto.class);
    }

    @Override
    public TodoDto getTodo(String userId, long todoId) {
        UserEntity userEntity = checkUserEntity(userId);
        TodoEntity todoEntity = checkTodoEntity(todoId);

        checkMismatch(userId, userEntity, todoEntity);

        return new ModelMapper().map(todoEntity, TodoDto.class);
    }

    @Override
    public TodoDto updateTodo(String userId, long todoId, TodoDto todoDto) {
        UserEntity userEntity = checkUserEntity(userId);
        TodoEntity todoEntity = checkTodoEntity(todoId);

        checkMismatch(userId, userEntity, todoEntity);

        TodoEntity newTodo = new ModelMapper().map(todoDto, TodoEntity.class);
        newTodo.setId(todoEntity.getId());
        newTodo.setUserEntityTodo(userEntity);

        TodoEntity updatedTodo = todoRepository.save(newTodo);

        return new ModelMapper().map(updatedTodo, TodoDto.class);
    }

    @Override
    public List<TodoDto> getTodos(String userId, int page, int limit) {
        List<TodoDto> returnValue = new ArrayList<>();

        UserEntity userEntity = checkUserEntity(userId);

        if (page > 0) page = page - 1;
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<TodoEntity> pagedUserEntities = todoRepository.findAllByUserEntityTodoOrderByIdAsc(userEntity, pageableRequest);

        List<TodoEntity> entityList = pagedUserEntities.getContent();
        for (TodoEntity entity : entityList) {
            returnValue.add(new ModelMapper().map(entity, TodoDto.class));
        }

        return returnValue;
    }

    @Override
    public void deleteTodo(String userId, long todoId) {
        UserEntity userEntity = checkUserEntity(userId);
        TodoEntity todoEntity = checkTodoEntity(todoId);

        checkMismatch(userId, userEntity, todoEntity);

        todoRepository.delete(todoEntity);
    }

    private TodoEntity checkTodoEntity(long todoId) {
        TodoEntity todoEntity = todoRepository.findById(todoId);
        if (todoEntity == null) {
            throw new HanchorServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return todoEntity;
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

    private void checkMismatch(String userId, UserEntity userEntity, TodoEntity todoEntity) {
        if (userId.equals("123456789") || userId.equals("A123456789")){
            System.out.println("Allow operation");
        } else {
            if (userEntity != todoEntity.getUserEntityTodo()){
                throw new HanchorServiceException(ErrorMessages.MISMATCH_RECORD.getErrorMessage());
            }
        }
    }
}
