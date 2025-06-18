package com.self.financedashboard.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Getter
@Setter
public class Config {

    @Value("${rapidapi.key}")
    String apiKey;

    @Value("${rapidapi.base-url}")
    String baseUrl;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Bean
    public WebClient webClient() {
        ObjectMapper webClientObjectMapper = new ObjectMapper();
        webClientObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        webClientObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs ->  {
                    codecs.defaultCodecs().maxInMemorySize(size);
                    codecs.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(webClientObjectMapper));
                })
                .build();
        return WebClient.builder()
                .exchangeStrategies(strategies)
                .defaultHeader("X-RapidAPI-Key", apiKey)
                .baseUrl(baseUrl)
                .build();
    }
}
