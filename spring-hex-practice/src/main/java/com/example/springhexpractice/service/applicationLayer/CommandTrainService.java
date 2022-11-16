package com.example.springhexpractice.service.applicationLayer;


import com.example.springhexpractice.controller.dto.request.CreateTrainRequest;
import com.example.springhexpractice.controller.dto.request.CreateTrainTicketRequest;
import com.example.springhexpractice.controller.dto.request.TrainStopRequest;
import com.example.springhexpractice.exception.CheckErrorException;
import com.example.springhexpractice.model.TrainRepository;
import com.example.springhexpractice.model.TrainStopRepository;
import com.example.springhexpractice.model.TrainTicketRepository;
import com.example.springhexpractice.model.entity.Train;
import com.example.springhexpractice.model.entity.TrainStop;
import com.example.springhexpractice.model.entity.TrainTicket;
import com.example.springhexpractice.service.domainLayer.TrainDomainService;
import com.example.springhexpractice.service.domainLayer.TrainStopDomainService;
import com.example.springhexpractice.service.domainLayer.TrainTicketDomainService;
import com.example.springhexpractice.service.domainLayer.internalUtil.UuidMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("CommandService")
@Transactional(rollbackFor = Exception.class)
public class CommandTrainService {

    @Autowired
    UuidMaker tool;
    @Autowired
    TrainRepository trainRepository;
    @Autowired
    TrainStopRepository trainStopRepository;
    @Autowired
    TrainTicketRepository trainTicketRepository;
    @Autowired
    CheckOutboundService outboundService;
    @Autowired
    TrainDomainService trainDomainService;
    @Autowired
    TrainStopDomainService trainStopDomainService;
    @Autowired
    TrainTicketDomainService trainTicketDomainService;


    public String createTrain(CreateTrainRequest request) throws CheckErrorException {
        //check
        List<String> stopsName = request.getStops().stream().map(TrainStopRequest::getStopName)
                .distinct().collect(Collectors.toList());
        List<TrainStopRequest> stopSorted = request.getStops().stream().sorted(Comparator.comparing(TrainStopRequest::getStopTime))
                .collect(Collectors.toList());

        //檢查 Train 狀態
        CheckErrorException checkErrorException = new CheckErrorException();
        outboundService.checkTrainStatus(checkErrorException,request.getTrainNo());
        //複合式檢查
        trainDomainService.checkTrainIsExist(checkErrorException,request.getTrainNo());
        trainStopDomainService.checkTrainStopDuplicate(checkErrorException,request.getStops(), stopsName);
        //車站的位置順序
        trainStopDomainService.stopPositionNotRight(checkErrorException,stopSorted);
        //create Train
        Train train = new Train(tool.getTrainUUid(), request.getTrainNo(), request.getTrainKind(),checkErrorException);
        trainRepository.save(train);

        //create TrainStop
        stopSorted.forEach((TrainStopRequest stops) -> {

            TrainStop trainStop = null;
            try {
                trainStop = new TrainStop(tool.getTrainStopUUid(),
                        train.getUuid(),
                        stopSorted.indexOf(stops) + 1,
                        stops.getStopName(),
                        stops.getStopTime(),
                        "N",
                        checkErrorException);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            trainStopRepository.save(trainStop);
        });

        if(checkErrorException.hasErrors()){
            throw checkErrorException;
        }

        return train.getUuid();
    }

    public String createTrainTicket(CreateTrainTicketRequest request) throws CheckErrorException, ParseException {

        CheckErrorException checkErrorException = new CheckErrorException();
        //check
        trainTicketDomainService.checkTrainTicket(checkErrorException,request);
        //check 車站 順序
        trainStopDomainService.checkStopIsExist(request.getTrainNo(), request.getFromStop(), request.getToStop());
        trainStopDomainService.checkStop(checkErrorException,request.getTrainNo(), request.getFromStop(), request.getToStop());
        //
        double price = outboundService.getTicketPrice();
        String trainUuid = trainRepository.findByTrainNo(request.getTrainNo()).getUuid();
        String ticketNo = tool.getTrainTicketUUid();
        //create
        TrainTicket ticket = new TrainTicket(request, ticketNo, trainUuid, price,checkErrorException);
        trainTicketRepository.save(ticket);

        if(checkErrorException.hasErrors()){
            throw checkErrorException;
        }

        return ticket.getTicketNo();
    }

}
