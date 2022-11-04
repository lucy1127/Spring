package com.example.springhexpractice.service;

import com.example.springhexpractice.controller.dto.request.CreateTrainRequest;
import com.example.springhexpractice.controller.dto.request.CreateTrainTicket;
import com.example.springhexpractice.controller.dto.request.TrainStop;
import com.example.springhexpractice.controller.dto.response.CreateResponse;
import com.example.springhexpractice.controller.dto.response.SelectStopResponse;
import com.example.springhexpractice.controller.dto.response.SelectTrainResponse;
import com.example.springhexpractice.controller.error.CheckCombinedInspectionException;
import com.example.springhexpractice.controller.error.NotFoundException;
import com.example.springhexpractice.model.TrainRepository;
import com.example.springhexpractice.model.TrainStopRepository;
import com.example.springhexpractice.model.TrainTicketRepository;
import com.example.springhexpractice.model.entity.Train;
import com.example.springhexpractice.model.entity.Train_Stop;
import com.example.springhexpractice.model.entity.Train_Ticket;
import com.example.springhexpractice.model.trainModel.EnumTest;
import com.example.springhexpractice.model.trainModel.TrainKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class TrainService {

    @Autowired
    TrainRepository trainRepository;
    @Autowired
    TrainStopRepository trainStopRepository;
    @Autowired
    TrainTicketRepository trainTicketRepository;
    @Autowired
    Tools tool;
    @Autowired
    TrainCheck trainCheck;


    public SelectTrainResponse selectTrain(Integer trainNo) {

        SelectTrainResponse selectTrainResponse = new SelectTrainResponse();

        Train train = trainRepository.findByTrainNo(trainNo);

        if (null == train) {
            throw new NotFoundException("車次不存在");
        }

        List<Train_Stop> trainStopList = trainStopRepository.findByUUID(train.getUuid());
        List<TrainStop> trains = new ArrayList<>();

        for (Train_Stop trainStop : trainStopList) {
            trains.add(new TrainStop(trainStop.getName(), trainStop.getTime()));
        }

        selectTrainResponse.setTrain_no(train.getTrain_no());
        selectTrainResponse.setTrain_kind(new EnumTest(TrainKind.valueOf(train.getTrain_kind())).trainType());
        selectTrainResponse.setStops(trains);

        return selectTrainResponse;
    }

    public List<SelectStopResponse> selectStop(String via) {
        List<String> uuid = trainStopRepository.findByStop(via);
        if (uuid.isEmpty()) {
            throw new NotFoundException("車站不存在");
        }
        List<SelectStopResponse> trainNo = new ArrayList<>();

        for (String uid : uuid) {
            SelectStopResponse result = new SelectStopResponse();
            Train train = trainRepository.findByUuid(uid);
            result.setTrain_no(train.getTrain_no());
            result.setTrain_kind(new EnumTest(TrainKind.valueOf(train.getTrain_kind())).trainType());
            trainNo.add(result);
        }
        return trainNo;
    }

    public CreateResponse createTrain(CreateTrainRequest request) throws ParseException {
        //check
        List<String> stopsName = request.getStops().stream().map(e -> e.getStop_name()).distinct().collect(Collectors.toList());
        List<TrainStop> stopSorted = request.getStops().stream().sorted(Comparator.comparing(TrainStop::getStop_time)).collect(Collectors.toList());

        trainCheck.checkTrainStatus(request.getTrain_no());
        trainCheck.checkCombinedInspection(request.getTrain_no(), request.getTrain_kind(), request.getStops(), stopsName);
        trainCheck.stopPositionNotRight(stopSorted);
        //
        Train train = new Train();
        train.setUuid(tool.getTrainUUid());
        train.setTrain_no(request.getTrain_no());
        train.setTrain_kind(new EnumTest(request.getTrain_kind()).trainChangeType().name());
        //
        Train_Stop trainStop = new Train_Stop();
        for (TrainStop stops : stopSorted) {

            trainStop.setUuid(tool.getTrainStopUUid());
            trainStop.setTrain_uuid(train.getUuid());
            trainStop.setName(stops.getStop_name());
            trainStop.setTime(stops.getStop_time());
            trainStop.setDelete_flag("N");
            trainStop.setSeq(stopSorted.indexOf(stops) + 1);
            trainStopRepository.save(trainStop);
        }
        trainRepository.save(train);
        return new CreateResponse(trainRepository.findByUuid(train.getUuid()).getUuid());
    }

    public CreateResponse createTrainTicket(CreateTrainTicket request) throws ParseException {

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

        //check

        if (null == trainRepository.findByTrainNo(request.getTrain_no())) {
            List list = new ArrayList<>();
            Map<String, String> map = new HashMap<>();
            map.put("code", "TrainNoNotExists");
            map.put("message", "Train No does not exists");
            list.add(map);
            throw new CheckCombinedInspectionException("VALIDATE_FAILED",list);
        }

        trainCheck.checkStop(request.getTrain_no(),request.getFrom_stop(),request.getTo_stop());

        //create
        Train_Ticket ticket = new Train_Ticket();
        //ticket id
        ticket.setTicket_no(tool.getTrainTicketUUid());//車票號碼
        ticket.setTrain_uuid(trainRepository.findByTrainNo(request.getTrain_no()).getUuid());//車輛UUID
        ticket.setFrom_stop(request.getFrom_stop());
        ticket.setTo_stop(request.getTo_stop());
        ticket.setTake_date(dt.parse(request.getTake_date()));
        ticket.setPrice(tool.getTickPrice());
        trainTicketRepository.save(ticket);

        return new CreateResponse(ticket.getTicket_no());
    }


}
