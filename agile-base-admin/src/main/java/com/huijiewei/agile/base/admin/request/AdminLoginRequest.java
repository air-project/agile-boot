package com.huijiewei.agile.base.admin.request;

import com.huijiewei.agile.base.admin.constraint.Account;
import com.huijiewei.agile.base.admin.entity.Admin;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Account(accountFieldName = "account",
        passwordFieldName = "password",
        accountTypeFieldName = "accountType",
        accountEntityFieldName = "admin",
        accountTypeMessage = "无效的帐号类型, 帐号应该是手机或者邮箱",
        accountNotExistMessage = "帐号不存在",
        passwordIncorrectMessage = "密码错误")
public class AdminLoginRequest {
    @NotBlank(message = "帐号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Hidden
    private Admin admin = null;
}

