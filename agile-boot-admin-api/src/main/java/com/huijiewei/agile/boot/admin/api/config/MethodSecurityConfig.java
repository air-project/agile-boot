package com.huijiewei.agile.boot.admin.api.config;

import com.huijiewei.agile.boot.admin.api.security.AdminPermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    private AdminPermissionEvaluator adminPermissionEvaluator;

    @Autowired
    public MethodSecurityConfig(AdminPermissionEvaluator adminPermissionEvaluator) {
        this.adminPermissionEvaluator = adminPermissionEvaluator;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(this.adminPermissionEvaluator);

        return expressionHandler;
    }
}
