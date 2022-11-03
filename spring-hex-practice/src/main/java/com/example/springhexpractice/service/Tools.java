package com.example.springhexpractice.service;


import com.example.springhexpractice.controller.serviceAPI.Price;
import com.example.springhexpractice.model.TrainRepository;
import com.example.springhexpractice.model.TrainStopRepository;
import com.example.springhexpractice.model.TrainTicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class Tools {

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    TrainStopRepository trainStopRepository;

    @Autowired
    TrainTicketRepository trainTicketRepository;

    @Autowired
    RestTemplate restTemplate;

    public String getTrainUUid() {
        String ans = "";

        do {
            ans = getHex();
        } while (null != trainRepository.findByUuid(ans));

        return ans;
    }

    public String getTrainStopUUid() {
        String ans = "";

        do {
            ans = getHex();
        } while (null != trainStopRepository.findByUuid(ans));

        return ans;
    }

    public String getTrainTicketUUid() {
        String ans = "";

        do {
            ans = getHex();
        } while (null != trainTicketRepository.findByTicketNo(ans));

        return ans;
    }

    public String getHex() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid.toUpperCase();
    }

    public Double getTickPrice() {

        String url = "https://petstore.swagger.io/v2/store/inventory";

        ResponseEntity<Price> response = restTemplate.getForEntity(url, Price.class);

        double price = response.getBody().getSold();
        return price;
    }


}
