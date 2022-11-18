package com.example.springhexpractice.domain.aggreate.domainService;

import com.example.springhexpractice.domain.commands.CreateTrainTicketCommand;
import com.example.springhexpractice.config.exception.CheckErrorException;
import com.example.springhexpractice.domain.aggreate.valueObject.ErrorCode;
import com.example.springhexpractice.infra.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TrainDomainService {

    @Autowired
    TrainRepository trainRepository;

    public  void checkTrainIsExist(CheckErrorException e,Integer trainNo) {
        if (null != trainRepository.findByTrainNo(trainNo)) {
                e.getCheckError().add(ErrorCode.TRAIN_EXIST);
        }
    }

    public void checkTrainTicket(CheckErrorException e, CreateTrainTicketCommand request) {
        if (null == trainRepository.findByTrainNo(request.getTrainNo())) {
            e.getCheckError().add(ErrorCode.TRAIN_EXIST);
        }
    }

}
