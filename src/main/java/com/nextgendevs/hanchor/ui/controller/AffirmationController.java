package com.nextgendevs.hanchor.ui.controller;

import com.nextgendevs.hanchor.exceptions.HanchorServiceException;
import com.nextgendevs.hanchor.service.AffirmationService;
import com.nextgendevs.hanchor.shared.Utils;
import com.nextgendevs.hanchor.shared.dto.AffirmationDto;
import com.nextgendevs.hanchor.ui.model.request.AffirmationRequest;
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
public class AffirmationController {

    @Autowired
    AffirmationService affirmationService;

    @Autowired
    Utils utils;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Create an affirmation.")
    @PostMapping(path = "/{userId}/affirmations", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AffirmationRest createAffirmation(@PathVariable String userId, @RequestBody AffirmationRequest affirmationRequest) {

        if (affirmationRequest.getTitle().isEmpty() || affirmationRequest.getAffirmation().isEmpty()) {
            throw new HanchorServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        AffirmationDto affirmationDto = new ModelMapper().map(affirmationRequest, AffirmationDto.class);

        AffirmationDto affirmation = affirmationService.createAffirmation(userId, affirmationDto);
        return new ModelMapper().map(affirmation, AffirmationRest.class);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Get an affirmation. Supply the userId or email. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(path = "/{userId}/affirmations/{affirmationId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AffirmationRest getAffirmation(@PathVariable String userId, @PathVariable long affirmationId) {

        AffirmationDto affirmation = affirmationService.getAffirmation(userId, affirmationId);
        return new ModelMapper().map(affirmation, AffirmationRest.class);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Update an affirmation. Supply the userId or email. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(path = "/{userId}/affirmations/{affirmationId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public AffirmationRest updateAffirmation(@PathVariable String userId,
                                             @PathVariable long affirmationId,
                                             @RequestBody AffirmationRequest affirmationRequest) {

        AffirmationDto affirmationDto = new ModelMapper().map(affirmationRequest, AffirmationDto.class);

        AffirmationDto lifeHack = affirmationService.updateAffirmation(userId, affirmationId, affirmationDto);
        return new ModelMapper().map(lifeHack, AffirmationRest.class);
    }



    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Get list of affirmations. Supply the userId or email. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(path = "/{userId}/affirmations", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<AffirmationRest> getAffirmations(@PathVariable String userId,
                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "limit", defaultValue = "10") int limit) {

        List<AffirmationRest> returnValue = new ArrayList<>();

        List<AffirmationDto> affirmations = affirmationService.getAffirmations(userId, page, limit);

        if (affirmations != null && !affirmations.isEmpty()) {
            Type listType = new TypeToken<List<AffirmationRest>>() {
            }.getType();
            returnValue = new ModelMapper().map(affirmations, listType);
        }

        return returnValue;
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Delete an affirmation. Supply the userId or email, affirmationId && imageId  to be deleted. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping(path = "/{userId}/affirmations/{affirmationId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteAffirmation(@PathVariable String userId, @PathVariable long affirmationId) {

        OperationStatusModel returnValue = new OperationStatusModel();

        affirmationService.deleteAffirmation(userId, affirmationId);
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }


}
