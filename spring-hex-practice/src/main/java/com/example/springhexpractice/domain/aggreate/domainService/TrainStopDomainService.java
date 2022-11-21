package com.example.springhexpractice.domain.aggreate.domainService;


import com.example.springhexpractice.config.exception.CheckErrorException;
import com.example.springhexpractice.config.exception.DataNotFoundException;
import com.example.springhexpractice.domain.aggreate.entity.Train;
import com.example.springhexpractice.domain.aggreate.entity.TrainStop;
import com.example.springhexpractice.domain.aggreate.valueObject.ErrorCode;
import com.example.springhexpractice.iface.controller.dto.request.TrainStopRequest;
import com.example.springhexpractice.infra.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class TrainStopDomainService {

    @Autowired
    TrainRepository trainRepository;
    public void checkVia(CheckErrorException e, String via) {
        if (via.equals("")) {
            e.getCheckError().add(ErrorCode.TRAIN_STOP_NO);
        }
    }

    public void checkStop(CheckErrorException e, Integer trainNo, String fromStop, String toStop) {

        Train train = trainRepository.findByTrainNo(trainNo);
        int  fromSeq = 0;
        int toSeq = 0;

        for(TrainStop trainStop:train.getTrainStopList()){
            if(trainStop.getName().equals(fromStop)){
                fromSeq = trainStop.getSeq();
            }
            if(trainStop.getName().equals(toStop)){
                toSeq = trainStop.getSeq();
            }
        }
        //順序
        if (fromSeq > toSeq || fromStop.equals(toStop)) {
            e.getCheckError().add(ErrorCode.TRAIN_STOPS_INVALID);
        }
    }

    public void checkStopIsExist(Integer trainNo, String fromStop, String toStop) {
        Train train = trainRepository.findByTrainNo(trainNo);
        int isExist = 0;
        //檢查是否有車站
        for(TrainStop trainStop:train.getTrainStopList()){
            if(trainStop.getName().equals(fromStop) || trainStop.getName().equals(toStop)){
                isExist++;
            }
        }
        if (isExist != 2) {
            throw new DataNotFoundException("車站不存在");
        }
    }

    public void checkTrainStopDuplicate(CheckErrorException e, List<TrainStopRequest> request, List<String> stopsName) {
        if (request.size() != stopsName.size()) {
            e.getCheckError().add(ErrorCode.TRAIN_MULTI_STOP);
        }
    }

    public void stopPositionNotRight(CheckErrorException e, List<TrainStopRequest> stopsList) {
        String[] places = {"屏東", "高雄", "臺南", "嘉義", "彰化", "台中", "苗粟", "新竹", "桃園", "樹林",
                "板橋", "萬華", "台北", "松山", "南港", "汐止", "基隆"};

        List<Integer> stopNumbers = new ArrayList<>();

        for (TrainStopRequest n : stopsList) {
            for (int j = 0; j < places.length; j++) {
                if (n.getStopName().equals(places[j])) {
                    stopNumbers.add(j);
                }
            }
        }
        if (stopNumbers.get(0) < stopNumbers.get(stopNumbers.size() - 1)) {
            for (int i = 0; i < stopNumbers.size() - 1; i++) {
                if (stopNumbers.get(i) > stopNumbers.get(i + 1))
                    e.getCheckError().add(ErrorCode.TRAIN_STOP_SORTED);
            }
        } else {
            for (int i = 0; i < stopNumbers.size() - 1; i++) {
                if (stopNumbers.get(i) < stopNumbers.get(i + 1))
                    e.getCheckError().add(ErrorCode.TRAIN_STOP_SORTED);
            }
        }

    }

}
