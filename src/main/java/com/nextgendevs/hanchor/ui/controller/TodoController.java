package com.nextgendevs.hanchor.ui.controller;

import com.nextgendevs.hanchor.exceptions.HanchorServiceException;
import com.nextgendevs.hanchor.service.TodoService;
import com.nextgendevs.hanchor.shared.Utils;
import com.nextgendevs.hanchor.shared.dto.TodoDto;
import com.nextgendevs.hanchor.ui.model.request.TodoRequest;
import com.nextgendevs.hanchor.ui.model.response.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hanchor/v1/users")
public class TodoController {

    @Autowired
    TodoService todoService;
    
    @Autowired
    Utils utils;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Create a todo")
    @PostMapping(path = "/{userId}/todos", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public TodoRest createTodo(@PathVariable String userId, @RequestBody TodoRequest todoRequest) {

        if (todoRequest.getTitle().isEmpty() || todoRequest.getTask().isEmpty() || todoRequest.getDate() == 0L) {
            throw new HanchorServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
        
        TodoDto todoDto = new ModelMapper().map(todoRequest, TodoDto.class);

        TodoDto todo = todoService.createTodo(userId, todoDto);
        return new ModelMapper().map(todo, TodoRest.class);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Get a todo. Supply the userId or email. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(path = "/{userId}/todos/{todoId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public TodoRest getTodo(@PathVariable String userId, @PathVariable long todoId) {

        TodoDto todo = todoService.getTodo(userId, todoId);
        return new ModelMapper().map(todo, TodoRest.class);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Update a todo. Supply the userId or email. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(path = "/{userId}/todos/{todoId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public TodoRest updateTodo(@PathVariable String userId,
                               @PathVariable long todoId,
                               @RequestBody TodoRequest todoRequest) {
        
        TodoDto todoDto = new ModelMapper().map(todoRequest, TodoDto.class);

        TodoDto todo = todoService.updateTodo(userId, todoId, todoDto);
        return new ModelMapper().map(todo, TodoRest.class);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Get list of todos. Supply the userId or email. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(path = "/{userId}/todos", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<TodoRest> getTodos(@PathVariable String userId,
                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "10") int limit) {

        List<TodoRest> returnValue = new ArrayList<>();

        List<TodoDto> todos = todoService.getTodos(userId, page, limit);

        if (todos != null && !todos.isEmpty()) {
            Type listType = new TypeToken<List<TodoRest>>() {}.getType();
            returnValue = new ModelMapper().map(todos, listType);
        }

        return returnValue;
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Delete an todo. Supply the userId or email, todoId && imageId  to be deleted. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping(path = "/{userId}/todos/{todoId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteTodo(@PathVariable String userId, @PathVariable long todoId) {

        OperationStatusModel returnValue = new OperationStatusModel();
        
        todoService.deleteTodo(userId, todoId);
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }
    

}
