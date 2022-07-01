package com.nextgendevs.hanchor.ui.controller;

import com.nextgendevs.hanchor.exceptions.HanchorServiceException;
import com.nextgendevs.hanchor.service.QuoteService;
import com.nextgendevs.hanchor.shared.Utils;
import com.nextgendevs.hanchor.shared.dto.QuoteDto;
import com.nextgendevs.hanchor.ui.model.request.QuoteRequest;
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
public class QuoteController {

    @Autowired
    QuoteService quoteService;

    @Autowired
    Utils utils;



    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "{adminKey} is required.")
    @PostMapping(path = "/{userId}/quotes/{adminKey}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public QuoteRest createQuote(@PathVariable String userId, @PathVariable String adminKey, @RequestBody QuoteRequest quoteRequest) {

        if (quoteRequest.getQuote().isEmpty()) {
            throw new HanchorServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        if (!adminKey.equals("QwErTyZxPv")){
            throw new HanchorServiceException(ErrorMessages.ACCESS_DENIED.getErrorMessage());
        }

        QuoteDto quoteDto = new ModelMapper().map(quoteRequest, QuoteDto.class);

        QuoteDto quote = quoteService.createQuote(userId, quoteDto);
        return new ModelMapper().map(quote, QuoteRest.class);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Get a quote. Supply the userId or email. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(path = "/{userId}/quotes/{quoteId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public QuoteRest getQuote(@PathVariable String userId, @PathVariable long quoteId) {

        QuoteDto quote = quoteService.getQuote(userId, quoteId);
        return new ModelMapper().map(quote, QuoteRest.class);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "{adminKey} is required.")
    @PutMapping(path = "/{userId}/quotes/{quoteId}/{adminKey}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public QuoteRest updateQuote(@PathVariable String userId,
                                     @PathVariable long quoteId,
                                     @PathVariable String adminKey,
                                     @RequestBody QuoteRequest quoteRequest) {

        if (quoteRequest.getQuote().isEmpty()) {
            throw new HanchorServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        if (!adminKey.equals("QwErTyZxPv")){
            throw new HanchorServiceException(ErrorMessages.ACCESS_DENIED.getErrorMessage());
        }

        QuoteDto quoteDto = new ModelMapper().map(quoteRequest, QuoteDto.class);

        QuoteDto quote = quoteService.updateQuote(userId, quoteId, quoteDto);
        return new ModelMapper().map(quote, QuoteRest.class);

    }



    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "Get list of quotes. Supply the userId or email. This action must be authorized.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(path = "/{userId}/quotes", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<QuoteRest> getQuotes(@PathVariable String userId,
                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "10") int limit) {

        List<QuoteRest> returnValue = new ArrayList<>();

        List<QuoteDto> quotes = quoteService.getQuotes(userId, page, limit);

        if (quotes != null && !quotes.isEmpty()) {
            Type listType = new TypeToken<List<QuoteRest>>() {}.getType();
            returnValue = new ModelMapper().map(quotes, listType);
        }

        return returnValue;
    }


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "This is the token [Bearer xyzabc] received from the response header after successful login.",
                    paramType = "header", required = true)
    })
    @Operation(summary = "{adminKey} is required.")
    @DeleteMapping(path = "/{userId}/quotes/{quoteId}/{adminKey}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel deleteQuote(@PathVariable String userId,
                                            @PathVariable long quoteId,
                                            @PathVariable String adminKey) {

        OperationStatusModel returnValue = new OperationStatusModel();

        if (!adminKey.equals("QwErTyZxPv")){
            throw new HanchorServiceException(ErrorMessages.ACCESS_DENIED.getErrorMessage());
        }

        quoteService.deleteQuote(userId, quoteId);
        returnValue.setOperationName(RequestOperationName.DELETE.name());
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }


}
