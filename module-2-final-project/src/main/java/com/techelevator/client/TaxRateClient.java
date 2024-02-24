package com.techelevator.client;

import com.techelevator.model.TaxRateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class TaxRateClient {
    RestTemplate restTemplate = new RestTemplate();


    public BigDecimal getTaxRate(String stateCode) {
        TaxRateDto response = restTemplate.getForObject("https://teapi.netlify.app/api/statetax?state=" + stateCode, TaxRateDto.class);
        return response.getSalesTax().divide(new BigDecimal("100"));
    }
}
