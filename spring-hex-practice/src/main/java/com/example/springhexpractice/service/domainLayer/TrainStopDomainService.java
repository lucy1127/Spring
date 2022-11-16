package com.example.springhexpractice.service.domainLayer;

import com.example.springhexpractice.controller.dto.request.TrainStopRequest;
import com.example.springhexpractice.exception.CheckErrorException;
import com.example.springhexpractice.exception.DataNotFoundException;
import com.example.springhexpractice.exception.ErrorCode;
import com.example.springhexpractice.model.TrainRepository;
import com.example.springhexpractice.model.TrainStopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainStopDomainService {

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    TrainStopRepository trainStopRepository;

    private static final String[] places = {"屏東", "高雄", "臺南", "嘉義", "彰化", "台中", "苗粟", "新竹", "桃園", "樹林",
            "板橋", "萬華", "台北", "松山", "南港", "汐止", "基隆"};

    public void checkVia(CheckErrorException e,String via){
        if(via.isEmpty()){
            e.getCheckError().add(ErrorCode.TrainStopNo);
        }
    }
    public void checkStop(CheckErrorException e,Integer trainNo, String fromStop, String toStop) {

        String trainUid = trainRepository.findByTrainNo(trainNo).getUuid();
        //順序
        int fromSeq = trainStopRepository.findByUuidAndName(trainUid, fromStop);
        int toSeq = trainStopRepository.findByUuidAndName(trainUid, toStop);

        if (fromSeq > toSeq || fromStop.equals(toStop)) {
            e.getCheckError().add(ErrorCode.TrainStopsInvalid);
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

    public void checkTrainStopDuplicate(CheckErrorException e,List<TrainStopRequest> request, List<String> stopsName) {
        if (request.size() != stopsName.size()) {
            e.getCheckError().add(ErrorCode.TrainMultiStop);
        }
    }

    public void stopPositionNotRight(CheckErrorException e,List<TrainStopRequest> stopsList) {


        List<Integer> stopNumbers = new ArrayList<>();

        for (TrainStopRequest n : stopsList) {
            for (int j = 0; j < places.length; j++) {
                if (n.getStopName().equals(places[j])) {
                    stopNumbers.add(j);
                }
            }
        }
        if (stopNumbers.get(0) < stopNumbers.get(stopNumbers.size() - 1)) { //1  6
            for (int i = 0; i < stopNumbers.size() - 1; i++) {
                if (stopNumbers.get(i) > stopNumbers.get(i + 1))
                    e.getCheckError().add(ErrorCode.TrainStopSorted);
            }
        } else {
            for (int i = 0; i < stopNumbers.size() - 1; i++) {
                if (stopNumbers.get(i) < stopNumbers.get(i + 1))
                    e.getCheckError().add(ErrorCode.TrainStopSorted);
            }
        }

    }

}
