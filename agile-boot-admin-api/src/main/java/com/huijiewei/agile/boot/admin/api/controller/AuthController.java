package com.huijiewei.agile.boot.admin.api.controller;

import com.huijiewei.agile.base.admin.request.AdminSignInRequest;
import com.huijiewei.agile.base.admin.response.AdminResponse;
import com.huijiewei.agile.base.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.violations.ConstraintViolationProblem;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
@Tag(name = "auth", description = "管理员登录注册")
public class AuthController {
    private final AdminService adminService;

    @Autowired
    public AuthController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(
            description = "管理员登录",
            responses = {
                    @ApiResponse(content = @Content(schema = @Schema(implementation = AdminResponse.class))),
                    @ApiResponse(responseCode = "404", description = "管理员不存在"),
                    @ApiResponse(responseCode = "422", content = @Content(schema = @Schema(implementation = ConstraintViolationProblem.class, allowableValues = {"violations"})), description = "输入验证错误")
            })
    @PostMapping(
            value = "/sign-in",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> actionSignIn(@Valid @RequestBody AdminSignInRequest request) {
        AdminResponse response = this.adminService.signIn(request);

        return ResponseEntity.ok(response);
    }

}
