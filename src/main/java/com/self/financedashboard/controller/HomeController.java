package com.self.financedashboard.controller;

import com.self.financedashboard.model.rest.helpers.GainersLosersWrapper;
import com.self.financedashboard.service.HomeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stock/")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @CrossOrigin
    @GetMapping("/topGainersLosers/{index}")
    public GainersLosersWrapper getTopGainersLosers(@PathVariable String index) {
        return homeService.getTopGainersLosers(index);
    }

}
