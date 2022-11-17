package com.example.springhexpractice.service.outboundService;

import com.example.springhexpractice.iface.controller.exception.CheckErrorException;
import com.example.springhexpractice.domain.aggreate.util.ErrorCode;

import com.example.springhexpractice.domain.outboundModel.CheckOutboundStatus;
import com.example.springhexpractice.domain.outboundModel.TicketPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CheckOutboundService {

    @Autowired
    RestTemplate restTemplate;
    @Value("${outbound.status.url}")
    private String statusUrl;

    @Value("${outbound.price.url}")
    private String priceUrl;

    public void checkTrainStatus(CheckErrorException e,Integer trainNo) {

        //呼叫 status-service API 檢核車次是否有效
        statusUrl = statusUrl + trainNo;

        ResponseEntity<CheckOutboundStatus> response = restTemplate.getForEntity(statusUrl, CheckOutboundStatus.class);
        int code = response.getStatusCodeValue();
        if (code == 200) {
            CheckOutboundStatus check = response.getBody();
            if (!check.getStatus().equals("available")) {
                e.getCheckError().add(ErrorCode.TRAIN_STATUS);

            }
        }
    }

    public Double getTicketPrice() {

        ResponseEntity<TicketPrice> response = restTemplate.getForEntity(priceUrl, TicketPrice.class);

        double price = response.getBody().getSold();
        return price;
    }

}
