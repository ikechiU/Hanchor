package com.nextgendevs.hanchor.ui.controller;

import com.nextgendevs.hanchor.exceptions.HanchorServiceException;
import com.nextgendevs.hanchor.service.UserService;
import com.nextgendevs.hanchor.shared.Utils;
import com.nextgendevs.hanchor.shared.dto.*;
import com.nextgendevs.hanchor.ui.model.request.UpdateUserRequest;
import com.nextgendevs.hanchor.ui.model.request.UserRequest;
import com.nextgendevs.hanchor.ui.model.response.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/hanchor/v1")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    Utils utils;

    @Operation(summary = "Create a user.")
    @PostMapping(path = "/users", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest createUser(@RequestBody UserRequest userRequest) {
        UserRest returnValue = new UserRest();

        if (userRequest.getFirstname().isEmpty() || userRequest.getLastname().isEmpty() ||
                userRequest.getUsername().isEmpty() || userRequest.getEmail().isEmpty() ||
                userRequest.getPassword().isEmpty()) {
            throw new HanchorServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        if (!utils.isEmailValid(userRequest.getEmail())) {
            throw new HanchorServiceException(ErrorMessages.INVALID_EMAIL.getErrorMessage());
        }

        if (userRequest.getEmail().equals("admin@hanchor.com") || userRequest.getEmail().equals("super@hanchor.com")) {
            throw new HanchorServiceException(ErrorMessages.RESERVED_EMAIL.getErrorMessage());
        }

        UserDto userDto = new ModelMapper().map(userRequest, UserDto.class);

        UserDto user = userService.createUser(userDto);
        return new ModelMapper().map(user, UserRest.class);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Get a user. Supply userId or email. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(path = "/users/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest getUser(@PathVariable String userId) {

        UserDto user = userService.getUser(userId);
        return new ModelMapper().map(user, UserRest.class);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Update a user. Supply userId or email and UpdateRequest. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(path = "/users/{userId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserRest updateUser(@PathVariable String userId, @RequestBody UpdateUserRequest updateUserRequest) {

        UserDto userDto = new ModelMapper().map(updateUserRequest, UserDto.class);
        UserDto user = userService.updateUser(userId, userDto);
        return new ModelMapper().map(user, UserRest.class);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Get list of users. Supply userId or email. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(path = "/users", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                      @RequestParam(value = "limit", defaultValue = "20") int limit,
                                      @RequestParam(value = "queryParam", defaultValue = "") String queryParam) {

        List<UserDto> users = userService.getUsers(page, limit, queryParam);
        Type listType = new TypeToken<List<UserRest>>() {}.getType();
        return  new ModelMapper().map(users, listType);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Delete a user. Supply the userId or email to be deleted. This action must be authorized. " +
            "Only a superAdmin or the user that created the account can delete it.", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('SUPER') or #userId == principal.userId")
    @DeleteMapping(path = "/users/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteUser(@PathVariable String userId) {
        OperationStatusModel returnValue = new OperationStatusModel();

        userService.deleteUser(userId);
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

}
