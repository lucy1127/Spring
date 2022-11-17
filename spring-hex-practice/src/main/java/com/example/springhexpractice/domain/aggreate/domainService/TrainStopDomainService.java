package com.example.springhexpractice.domain.aggreate.domainService;


import com.example.springhexpractice.iface.controller.exception.CheckErrorException;
import com.example.springhexpractice.iface.controller.exception.DataNotFoundException;
import com.example.springhexpractice.domain.aggreate.util.ErrorCode;
import com.example.springhexpractice.infra.TrainRepository;
import com.example.springhexpractice.infra.TrainStopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class TrainStopDomainService {

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    TrainStopRepository trainStopRepository;

    public void checkVia(CheckErrorException e,String via){
        if(via.equals("")){
            e.getCheckError().add(ErrorCode.TRAIN_STOP_NO);
        }
    }
    public void checkStop(CheckErrorException e,Integer trainNo, String fromStop, String toStop) {

        String trainUid = trainRepository.findByTrainNo(trainNo).getUuid();
        //順序
        int fromSeq = trainStopRepository.findByUuidAndName(trainUid, fromStop);
        int toSeq = trainStopRepository.findByUuidAndName(trainUid, toStop);

        if (fromSeq > toSeq || fromStop.equals(toStop)) {
            e.getCheckError().add(ErrorCode.TRAIN_STOPS_INVALID);
        }
    }

    public void checkStopIsExist(Integer trainNo, String fromStop, String toStop) {
        String trainUid = trainRepository.findByTrainNo(trainNo).getUuid();
        //檢查是否有車站
        if (trainStopRepository.findByUuidAndStop(trainUid, fromStop) == null
                || trainStopRepository.findByUuidAndStop(trainUid, toStop) == null) {
            throw new DataNotFoundException("車站不存在");
        }
    }

}
