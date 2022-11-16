package com.example.springhexpractice.service.domainLayer;

import com.example.springhexpractice.controller.dto.request.CreateTrainTicketRequest;

import com.example.springhexpractice.exception.CheckErrorException;
import com.example.springhexpractice.exception.ErrorCode;
import com.example.springhexpractice.model.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainTicketDomainService {
    @Autowired
    TrainRepository trainRepository;

    public void checkTrainTicket(CheckErrorException e,CreateTrainTicketRequest request) {
        if (null == trainRepository.findByTrainNo(request.getTrainNo())) {
            e.getCheckError().add(ErrorCode.TrainExist);
        }
    }

}
