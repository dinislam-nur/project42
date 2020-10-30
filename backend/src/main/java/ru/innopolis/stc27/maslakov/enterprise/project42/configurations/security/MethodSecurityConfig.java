package ru.innopolis.stc27.maslakov.enterprise.project42.configurations.security;

import lombok.val;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
}