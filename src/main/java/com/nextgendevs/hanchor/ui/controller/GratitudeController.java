package com.nextgendevs.hanchor.ui.controller;

import com.nextgendevs.hanchor.exceptions.HanchorServiceException;
import com.nextgendevs.hanchor.service.CloudinaryService;
import com.nextgendevs.hanchor.service.GratitudeService;
import com.nextgendevs.hanchor.shared.Utils;
import com.nextgendevs.hanchor.shared.dto.GratitudeDto;
import com.nextgendevs.hanchor.ui.model.request.GratitudeRequest;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hanchor/v1/users")
public class GratitudeController {

    @Autowired
    GratitudeService gratitudeService;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    Utils utils;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Create a gratitude. Supply the userId. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
//    @PreAuthorize("hasAuthority('SUPER_WRITE_AUTHORITY')")
    @PostMapping(path = "/{userId}/gratitude", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public GratitudeRest createGratitude(@PathVariable String userId,
                                         @RequestParam(name = "title") String title,
                                         @RequestParam(name = "message") String message,
                                         @RequestParam(name = "file", required = false) MultipartFile multipartFile) {

        checkGratitude(title, message);
        String generatedImageId = utils.generateImageId(5) + System.currentTimeMillis();

        GratitudeRequest gratitudeRequest = new GratitudeRequest();
        gratitudeRequest.setTitle(title);
        gratitudeRequest.setMessage(message);
        gratitudeRequest.setImageId("0");

        GratitudeDto gratitudeDto = new ModelMapper().map(gratitudeRequest, GratitudeDto.class);

        String path = multipartFile.getOriginalFilename();
        try {
            if (path != null && path != "") {
                String url = cloudinaryService.uploadFile(multipartFile, userId, generatedImageId);
                System.out.println("image url: " + url);
                gratitudeDto.setImageUrl(url);
                gratitudeDto.setImageId(generatedImageId);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        GratitudeDto gratitude = gratitudeService.createGratitude(userId, gratitudeDto);
        return new ModelMapper().map(gratitude, GratitudeRest.class);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Get a gratitude. Supply the userId. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(path = "/{userId}/gratitude/{gratitudeId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public GratitudeRest getGratitude(@PathVariable String userId, @PathVariable long gratitudeId) {

        GratitudeDto gratitude = gratitudeService.getGratitude(userId, gratitudeId);
        return new ModelMapper().map(gratitude, GratitudeRest.class);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Update a gratitude. Supply the userId, gratitudeId, and content of the gratitude to be updated. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
//    @PreAuthorize("hasAuthority('SUPER_WRITE_AUTHORITY')")
    @PutMapping(path = "/{userId}/gratitude/{gratitudeId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public GratitudeRest updateGratitude(@PathVariable String userId,
                                         @PathVariable long gratitudeId,
                                         @RequestParam(name = "title") String title,
                                         @RequestParam(name = "message") String message,
                                         @RequestParam(name = "imageId") String imageId,
                                         @RequestParam(name = "file", required = false) MultipartFile multipartFile) {

        if (imageId.isEmpty())  throw new HanchorServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        checkGratitude(title, message);

        String generatedImageId = utils.generateImageId(5) + System.currentTimeMillis();

        GratitudeRequest gratitudeRequest = new GratitudeRequest();
        gratitudeRequest.setTitle(title);
        gratitudeRequest.setMessage(message);

        if (!imageId.equals("0")) {
            try {
                String result = cloudinaryService.destroyFile(userId, imageId);
                if (result.equals("ok")) {
                    System.out.println("Old image deleted");
                    gratitudeRequest.setImageId("0");
                }
            } catch (IOException e) {
                throw new HanchorServiceException(ErrorMessages.ERROR_DELETING_IMAGE.getErrorMessage());
            }
        }

        GratitudeDto gratitudeDto = new ModelMapper().map(gratitudeRequest, GratitudeDto.class);
        String path = multipartFile.getOriginalFilename();
        try {
            if (path != null && path != "") {
                String url = cloudinaryService.uploadFile(multipartFile, userId, generatedImageId);
                System.out.println("image url: " + url);
                gratitudeDto.setImageUrl(url);
                gratitudeDto.setImageId(generatedImageId);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        GratitudeDto updatedGratitude = gratitudeService.updateGratitude(userId, gratitudeId, gratitudeDto);
        return new ModelMapper().map(updatedGratitude, GratitudeRest.class);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Get list of gratitude for a user. Supply the userId. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(path = "/{userId}/gratitude", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<GratitudeRest> getGratitudes(@PathVariable String userId,
                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "limit", defaultValue = "10") int limit) {

        List<GratitudeRest> returnValue = new ArrayList<>();

        List<GratitudeDto> gratitudes = gratitudeService.getGratitudes(userId, page, limit);

        if (gratitudes != null && !gratitudes.isEmpty()) {
            Type listType = new TypeToken<List<GratitudeRest>>() {}.getType();
            returnValue = new ModelMapper().map(gratitudes, listType);
        }

        return returnValue;
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Delete a gratitude. Supply the userId, gratitudeId && imageId  to be deleted. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
//    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_WRITE')")
    @DeleteMapping(path = "/{userId}/gratitude/{gratitudeId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteGratitude(@PathVariable String userId,
                                            @PathVariable long gratitudeId,
                                            @RequestParam(name = "imageId") String imageId) throws IOException {

        OperationStatusModel returnValue = new OperationStatusModel();

        if (imageId.isEmpty())  throw new HanchorServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        if (imageId.equals("0")) {
            gratitudeService.deleteGratitude(userId, gratitudeId);
            returnValue.setOperationName(RequestOperationName.DELETE.name());
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
            return returnValue;
        } else {
            String result = cloudinaryService.destroyFile(userId, imageId);

            if (result.equals("ok")) {
                gratitudeService.deleteGratitude(userId, gratitudeId);
                returnValue.setOperationName(RequestOperationName.DELETE.name());
                returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
                return returnValue;
            } else {
                throw new HanchorServiceException(ErrorMessages.ERROR_DELETING_IMAGE.getErrorMessage());
            }
        }
    }


    public void checkGratitude(String title, String message) {
        if (title.isEmpty() || message.isEmpty()){
            throw new HanchorServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }
    }

}
