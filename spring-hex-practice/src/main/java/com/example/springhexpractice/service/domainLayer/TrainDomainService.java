package com.example.springhexpractice.service.domainLayer;

import com.example.springhexpractice.exception.CheckErrorException;
import com.example.springhexpractice.exception.DataNotFoundException;
import com.example.springhexpractice.exception.ErrorCode;
import com.example.springhexpractice.model.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.*;


@Component
public class TrainDomainService {

    @Autowired
    TrainRepository trainRepository;

    public  void checkTrainIsExist(CheckErrorException e,Integer trainNo) {
        if (null != trainRepository.findByTrainNo(trainNo)) {
                e.getCheckError().add(ErrorCode.TrainExist);
        }
    }
    public  void checkTrainIsExist(List<Map<String, Object>> trainStop) {
        if (trainStop.isEmpty()) {
            throw new DataNotFoundException("車次不存在");
        }
    }
    public  void checkUuidIsExist(List<String> uuid) {
        if (uuid.isEmpty()) {
            throw new DataNotFoundException("車站不存在");
        }
    }

}
