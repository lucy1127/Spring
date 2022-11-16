package com.example.springhexpractice.service.applicationLayer;

import com.example.springhexpractice.controller.dto.request.TrainStopRequest;
import com.example.springhexpractice.controller.dto.response.SelectStopResponse;
import com.example.springhexpractice.controller.dto.response.SelectTrainResponse;
import com.example.springhexpractice.exception.CheckErrorException;
import com.example.springhexpractice.exception.ErrorCode;
import com.example.springhexpractice.model.TrainRepository;
import com.example.springhexpractice.model.TrainStopRepository;
import com.example.springhexpractice.model.entity.Train;
import com.example.springhexpractice.service.domainLayer.TrainDomainService;
import com.example.springhexpractice.service.domainLayer.TrainStopDomainService;
import com.example.springhexpractice.service.domainLayer.TrainTicketDomainService;
import com.example.springhexpractice.service.domainLayer.internalUtil.TrainKind;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QueryTrainService {

    @Autowired
    TrainRepository trainRepository;
    @Autowired
    TrainStopRepository trainStopRepository;
    @Autowired
    TrainDomainService trainDomainService;

    @Autowired
    TrainStopDomainService trainStopDomainService;
    private final DateFormat df = new SimpleDateFormat("HH:mm");


    public SelectTrainResponse selectTrain(Integer trainNo) {

        List<Map<String, Object>> trainStop = trainRepository.findByTrainNoAndStopAndTime(trainNo);
        List<TrainStopRequest> trains = new ArrayList<>();
        trainDomainService.checkTrainIsExist(trainStop);

        trainStop.forEach((Map<String, Object> stop) -> {
            String stopName = stop.get("NAME").toString();
            trains.add(new TrainStopRequest(stopName, df.format(stop.get("TIME"))));
        });

        SelectTrainResponse selectTrainResponse = new SelectTrainResponse(
                trainNo,
                TrainKind.getName(trainStop.get(0).get("TRAIN_KIND").toString()),
                trains
        );
        return selectTrainResponse;
    }


    public List<SelectStopResponse> selectStop(String via) throws CheckErrorException {
        CheckErrorException checkErrorException = new CheckErrorException();
        //check
        List<String> uuid = trainStopRepository.findByStop(via);
        trainStopDomainService.checkVia(checkErrorException,via);
        trainDomainService.checkUuidIsExist(uuid);
        //
        List<SelectStopResponse> trainNo = new ArrayList<>();

        uuid.forEach((String uid) -> {
            SelectStopResponse result = new SelectStopResponse();
            Train train = trainRepository.findByUuid(uid);
            result.setTrainNo(train.getTrainNo());
            result.setTrainKind(TrainKind.getName(train.getTrainKind()));
            trainNo.add(result);
        });

        if(checkErrorException.hasErrors()){
            throw checkErrorException;
        }

        return trainNo;
    }


}
