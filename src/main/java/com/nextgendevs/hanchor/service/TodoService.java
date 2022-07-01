package com.nextgendevs.hanchor.service;

import com.nextgendevs.hanchor.shared.dto.TodoDto;

import java.util.List;

public interface TodoService {

    TodoDto createTodo(String userId, TodoDto todoDto);

    TodoDto getTodo(String userId, long todoId);

    TodoDto updateTodo(String userId, long todoId, TodoDto todoDto);

    List<TodoDto> getTodos(String userId, int page, int limit);

    void deleteTodo(String userId, long todoId);
}
