package com.self.financedashboard.controller;

import com.self.financedashboard.model.ErrorResponse;
import com.self.financedashboard.model.rest.helpers.GainersLosersWrapper;
import com.self.financedashboard.service.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock/")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @CrossOrigin
    @GetMapping("/topGainersLosers/{index}")
    public ResponseEntity<?> getTopGainersLosers(@PathVariable String index) {
        logger.info("HomeController :: getting the top gainers and losers for {}", index);
        try {
            GainersLosersWrapper wrapper = homeService.getTopGainersLosers(index);
            return new ResponseEntity<>(wrapper, HttpStatus.OK);
        } catch (Exception exception) {
            ErrorResponse errorResponse = new ErrorResponse("No results found",
                    HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
