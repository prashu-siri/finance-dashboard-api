package com.self.financedashboard.service;

import com.self.financedashboard.model.rest.helpers.GainersLosersWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class HomeService {

    public GainersLosersWrapper getTopGainersLosers(String index) {
        WebClient webClient = WebClient.builder().baseUrl("https://stock-nse-india.herokuapp.com/").build();

        return webClient.get().uri(uriBuilder -> uriBuilder
                .path("api/gainersAndLosers/{index}")
                .build(index))
                .retrieve()
                .bodyToMono(GainersLosersWrapper.class)
                .block();
    }
}
