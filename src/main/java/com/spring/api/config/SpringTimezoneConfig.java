package com.spring.api.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class SpringTimezoneConfig {

    @PostConstruct // o primeiro método executado apos a inicializacao da classe
    public void timezoneConfig() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
}
