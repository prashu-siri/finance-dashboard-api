package com.self.financedashboard.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class Config {

    @Value("${external.api.url}")
    private String externalApiUrl;
}
