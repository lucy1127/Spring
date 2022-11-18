package com.example.springhexpractice.domain.aggreate.valueObject;


import com.example.springhexpractice.infra.TrainRepository;
import com.example.springhexpractice.infra.TrainTicketRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UuidMaker {

    @Autowired
    TrainRepository trainRepository;
    @Autowired
    TrainTicketRepository trainTicketRepository;


    private String ans = "";
    public String getTrainUUid() {

        do {
            ans = getHex();
        } while (null != trainRepository.findByUuid(ans));

        return ans;
    }


    public String getTrainTicketUUid() {

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


}
