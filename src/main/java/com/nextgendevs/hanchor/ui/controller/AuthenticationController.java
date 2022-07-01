package com.nextgendevs.hanchor.ui.controller;

import com.nextgendevs.hanchor.ui.model.request.SwaggerLoginRequestModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Operation(summary = "Login as a user or admin using your credential, an email and password or userId and password.")
    @ApiOperation("Hanchor login")
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Response Headers",
                    responseHeaders = {
                            @ResponseHeader(name = "authorization",
                                    description = "Bearer <JWT value here>"),
                            @ResponseHeader(name = "userId",
                                    description = "<Public User Id value here>")
                    })
    })
    @PostMapping("/hanchor/login")
    public void theFakeLogin(@RequestBody SwaggerLoginRequestModel loginRequestModel)
    {
        throw new IllegalStateException("This method should not be called. This method is implemented by Spring Security");
    }
}
