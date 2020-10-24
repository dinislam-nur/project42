package ru.innopolis.stc27.maslakov.enterprise.project42.configurations.security;

import lombok.val;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        val defaultExpressionHandler = new DefaultMethodSecurityExpressionHandler();
        defaultExpressionHandler.setPermissionEvaluator(new UserPermissionEvaluator());
        return defaultExpressionHandler;
    }
}