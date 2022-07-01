package com.nextgendevs.hanchor.ui.controller;

import com.nextgendevs.hanchor.service.UserService;
import com.nextgendevs.hanchor.ui.model.response.OperationStatusModel;
import com.nextgendevs.hanchor.ui.model.response.RequestOperationName;
import com.nextgendevs.hanchor.ui.model.response.RequestOperationStatus;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hanchor")
public class VerificationEmailController {

    @Autowired
    UserService userService;

    @Operation(summary = "VERIFY EMAIL. Supply the token sent to your email upon account registration to verify your email.")
    @GetMapping(path = "/email-verification", produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());

        boolean isVerified = userService.verifyEmailToken(token);

        if(isVerified)
        {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } else {
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        }

        return returnValue;
    }

}
