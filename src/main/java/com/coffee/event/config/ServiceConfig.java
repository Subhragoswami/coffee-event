package com.coffee.event.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
public class ServiceConfig {

    @Value("${admin.user.username}")
    private String userName;

    @Value("${admin.user.password}")
    private String password;

    @Value("${admin.user.userRole}")
    private String userRole;

}
