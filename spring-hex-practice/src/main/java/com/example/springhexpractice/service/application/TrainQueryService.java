package com.example.springhexpractice.service.application;

import com.example.springhexpractice.domain.aggreate.domainService.TrainStopDomainService;
import com.example.springhexpractice.domain.aggreate.entity.TrainStop;
import com.example.springhexpractice.iface.controller.dto.request.TrainStopRequest;
import com.example.springhexpractice.iface.controller.dto.response.SelectStopResponse;
import com.example.springhexpractice.iface.controller.dto.response.SelectTrainResponse;
import com.example.springhexpractice.config.exception.CheckErrorException;
import com.example.springhexpractice.infra.TrainRepository;
import com.example.springhexpractice.domain.aggreate.entity.Train;
import com.example.springhexpractice.domain.aggreate.valueObject.TrainKind;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TrainQueryService {

    @Autowired
    TrainRepository trainRepository;
    @Autowired
    TrainStopDomainService trainStopDomainService;
    private final DateFormat df = new SimpleDateFormat("HH:mm");


    public SelectTrainResponse selectTrain(Integer trainNo) {

        Train trainStop = trainRepository.findByTrainNo(trainNo);
        List<TrainStopRequest> trains = new ArrayList<>();
        //檢查 Train 是否存在
        Train.checkTrainIsExist(trainStop);

        trainStop.getTrainStopList().forEach((TrainStop stop) -> {
            String stopName = stop.getName();
            trains.add(new TrainStopRequest(stopName, df.format(stop.getTime())));
        });

        SelectTrainResponse selectTrainResponse = new SelectTrainResponse(
                trainNo,
                TrainKind.getName(trainStop.getTrainKind()),
                trains
        );

        return selectTrainResponse;
    }


    public List<SelectStopResponse> selectStop(String via) throws CheckErrorException {
        CheckErrorException checkErrorException = new CheckErrorException();
        //check
        trainStopDomainService.checkVia(checkErrorException,via);
        //如果有錯則 拋出例外
        if(checkErrorException.hasErrors()){
            throw checkErrorException;
        }
        //
        List<Train> trainList = trainRepository.findAll();

        List<SelectStopResponse> result = new ArrayList<>();
        trainList.forEach(train ->{
            train.getTrainStopList().forEach(trainStop -> {
                if(trainStop.getName().equals(via)){
                    SelectStopResponse response = new SelectStopResponse();
                    response.setTrainNo(train.getTrainNo());
                    response.setTrainKind(TrainKind.getName(train.getTrainKind()));
                    result.add(response);
                }
            });
        } );

        Train.checkTrainListIsExist(result);

        return result;
    }


}
