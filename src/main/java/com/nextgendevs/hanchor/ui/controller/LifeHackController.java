package com.nextgendevs.hanchor.ui.controller;

import com.nextgendevs.hanchor.exceptions.HanchorServiceException;
import com.nextgendevs.hanchor.service.LifeHackService;
import com.nextgendevs.hanchor.shared.Utils;
import com.nextgendevs.hanchor.shared.dto.LifeHackDto;
import com.nextgendevs.hanchor.ui.model.request.LifeHackRequest;
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
public class LifeHackController {

    @Autowired
    LifeHackService lifeHackService;

    @Autowired
    Utils utils;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "{adminKey} is required.")
    @PostMapping(path = "/{userId}/life-hacks/{adminKey}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public LifeHackRest createLifeHack(@PathVariable String userId, @PathVariable String adminKey, @RequestBody LifeHackRequest lifeHackRequest) {

        if (lifeHackRequest.getLifeHack().isEmpty()) {
            throw new HanchorServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        if (!adminKey.equals("QwErTyZxPv")){
            throw new HanchorServiceException(ErrorMessages.ACCESS_DENIED.getErrorMessage());
        }

        LifeHackDto lifeHackDto = new ModelMapper().map(lifeHackRequest, LifeHackDto.class);

        LifeHackDto lifeHack = lifeHackService.createLifeHack(userId, lifeHackDto);
        return new ModelMapper().map(lifeHack, LifeHackRest.class);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Get a life-hack. Supply the userId or email. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(path = "/{userId}/life-hacks/{lifeHackId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public LifeHackRest getLifeHack(@PathVariable String userId, @PathVariable long lifeHackId) {

        LifeHackDto lifeHack = lifeHackService.getLifeHack(userId, lifeHackId);
        return new ModelMapper().map(lifeHack, LifeHackRest.class);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "{adminKey} is required.")
    @PutMapping(path = "/{userId}/life-hacks/{lifeHackId}/{adminKey}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public LifeHackRest updateLifeHack(@PathVariable String userId,
                                     @PathVariable long lifeHackId,
                                     @PathVariable String adminKey,
                                     @RequestBody LifeHackRequest lifeHackRequest) {

        if (lifeHackRequest.getLifeHack().isEmpty()) {
            throw new HanchorServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        if (!adminKey.equals("QwErTyZxPv")){
            throw new HanchorServiceException(ErrorMessages.ACCESS_DENIED.getErrorMessage());
        }

        LifeHackDto lifeHackDto = new ModelMapper().map(lifeHackRequest, LifeHackDto.class);

        LifeHackDto lifeHack = lifeHackService.updateLifeHack(userId, lifeHackId, lifeHackDto);
        return new ModelMapper().map(lifeHack, LifeHackRest.class);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Get list of life-hacks. Supply the userId or email. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(path = "/{userId}/life-hacks", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<LifeHackRest> getLifeHacks(@PathVariable String userId,
                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "10") int limit) {

        List<LifeHackRest> returnValue = new ArrayList<>();

        List<LifeHackDto> lifeHacks = lifeHackService.getLifeHacks(userId, page, limit);

        if (lifeHacks != null && !lifeHacks.isEmpty()) {
            Type listType = new TypeToken<List<LifeHackRest>>() {}.getType();
            returnValue = new ModelMapper().map(lifeHacks, listType);
        }

        return returnValue;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "{adminKey} is required.")
    @DeleteMapping(path = "/{userId}/life-hacks/{lifeHackId}/{adminKey}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteLifeHack(@PathVariable String userId,
                                            @PathVariable long lifeHackId,
                                            @PathVariable String adminKey) {

        OperationStatusModel returnValue = new OperationStatusModel();

        if (!adminKey.equals("QwErTyZxPv")){
            throw new HanchorServiceException(ErrorMessages.ACCESS_DENIED.getErrorMessage());
        }

        lifeHackService.deleteLifeHack(userId, lifeHackId);
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }


}
