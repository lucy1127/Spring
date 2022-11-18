package com.example.springhexpractice.service.application;


import com.example.springhexpractice.domain.commands.CreateTrainCommand;
import com.example.springhexpractice.domain.commands.CreateTrainTicketCommand;
import com.example.springhexpractice.domain.aggreate.domainService.TrainDomainService;
import com.example.springhexpractice.domain.aggreate.domainService.TrainStopDomainService;
import com.example.springhexpractice.iface.controller.dto.request.TrainStopRequest;
import com.example.springhexpractice.config.exception.CheckErrorException;
import com.example.springhexpractice.infra.TrainRepository;
import com.example.springhexpractice.infra.TrainTicketRepository;
import com.example.springhexpractice.domain.aggreate.entity.Train;
import com.example.springhexpractice.domain.aggreate.entity.TrainTicket;
import com.example.springhexpractice.domain.aggreate.valueObject.UuidMaker;
import com.example.springhexpractice.infra.outbound.CheckOutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("CommandService")
@Transactional(rollbackFor = Exception.class)
public class TrainCommandService {

    @Autowired
    UuidMaker tool;

    @Autowired
    TrainRepository trainRepository;
    @Autowired
    TrainTicketRepository trainTicketRepository;
    @Autowired
    CheckOutboundService outboundService;
    @Autowired
    TrainDomainService trainDomainService;
    @Autowired
    TrainStopDomainService trainStopDomainService;


    public Train createTrain(CreateTrainCommand request) throws CheckErrorException {
        //check
        List<String> stopsName = request.getStops().stream()
                .map(TrainStopRequest::getStopName)
                .distinct().collect(Collectors.toList());
        List<TrainStopRequest> stopSorted = request.getStops().stream()
                .sorted(Comparator.comparing(TrainStopRequest::getStopTime))
                .collect(Collectors.toList());
        //
        CheckErrorException checkErrorException = new CheckErrorException();
        //檢查 Train 狀態
        outboundService.checkTrainStatus(checkErrorException, request.getTrainNo());
        //檢查 Train 是否存在
        trainDomainService.checkTrainIsExist(checkErrorException, request.getTrainNo());
        //檢查 TrainStop 是否合理或是重複
        trainStopDomainService.checkTrainStopDuplicate(checkErrorException, request.getStops(), stopsName);
        //車站的位置順序
        trainStopDomainService.stopPositionNotRight(checkErrorException, stopSorted);
        //
        String uuid = tool.getTrainUUid();
        //create Train
        Train train = Train.create(uuid, request,stopSorted,checkErrorException);
        trainRepository.save(train);
        //如果有錯 拋錯誤
        if (checkErrorException.hasErrors()) {
            throw checkErrorException;
        }

        return train;
    }

    public TrainTicket createTrainTicket(CreateTrainTicketCommand request) throws CheckErrorException, ParseException {

        CheckErrorException checkErrorException = new CheckErrorException();
        //check 車子是否存在
        trainDomainService.checkTrainTicket(checkErrorException, request);
        //check 車站是否存在
        trainStopDomainService.checkStopIsExist(request.getTrainNo(), request.getFromStop(), request.getToStop());
        //check 車站順序
        trainStopDomainService.checkStop(checkErrorException, request.getTrainNo(), request.getFromStop(), request.getToStop());
        //
        double price = outboundService.getTicketPrice();
        String trainUuid = trainRepository.findByTrainNo(request.getTrainNo()).getUuid();
        String ticketNo = tool.getTrainTicketUUid();
        //create
        TrainTicket ticket = TrainTicket.create(request,ticketNo,trainUuid,price,checkErrorException);
        trainTicketRepository.save(ticket);

        //如果有錯 拋錯誤
        if (checkErrorException.hasErrors()) {
            throw checkErrorException;
        }
        return ticket;
    }

}
