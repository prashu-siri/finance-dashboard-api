package com.self.financedashboard.service;

import com.self.financedashboard.configuration.Config;
import com.self.financedashboard.model.rest.helpers.GainersLosersWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class HomeService {

    private final Config config;

    public HomeService(Config config) {
        this.config = config;
    }

    public GainersLosersWrapper getTopGainersLosers(String index) {
        WebClient webClient = WebClient.builder().baseUrl(config.getExternalApiUrl()).build();

        try {
            return webClient.get().uri(uriBuilder -> uriBuilder
                    .path("api/gainersAndLosers/{index}")
                    .build(index))
                    .retrieve()
                    .bodyToMono(GainersLosersWrapper.class)
                    .block();
        } catch(Exception exception) {
            throw  new RuntimeException("Error fetching the gainer and losers");
        }

    }
}
