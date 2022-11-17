package com.example.springhexpractice.service.applicationService;


import com.example.springhexpractice.domain.aggreate.domainService.TrainDomainService;
import com.example.springhexpractice.domain.aggreate.domainService.TrainStopDomainService;
import com.example.springhexpractice.iface.controller.dto.request.CreateTrainRequest;
import com.example.springhexpractice.iface.controller.dto.request.CreateTrainTicketRequest;
import com.example.springhexpractice.iface.controller.dto.request.TrainStopRequest;
import com.example.springhexpractice.iface.controller.exception.CheckErrorException;
import com.example.springhexpractice.infra.TrainRepository;
import com.example.springhexpractice.infra.TrainStopRepository;
import com.example.springhexpractice.infra.TrainTicketRepository;
import com.example.springhexpractice.domain.aggreate.entity.Train;
import com.example.springhexpractice.domain.aggreate.entity.TrainStop;
import com.example.springhexpractice.domain.aggreate.entity.TrainTicket;
import com.example.springhexpractice.domain.aggreate.util.UuidMaker;
import com.example.springhexpractice.service.outboundService.CheckOutboundService;
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


    public String createTrain(CreateTrainRequest request) throws CheckErrorException {
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
        TrainStop.checkTrainStopDuplicate(checkErrorException, request.getStops(), stopsName);
        //車站的位置順序
        TrainStop.stopPositionNotRight(checkErrorException, stopSorted);
        //create Train
        Train train = new Train(tool.getTrainUUid(), request.getTrainNo(), request.getTrainKind(), checkErrorException);
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
        //如果有錯 拋錯誤
        if (checkErrorException.hasErrors()) {
            throw checkErrorException;
        }

        return train.getUuid();
    }

    public String createTrainTicket(CreateTrainTicketRequest request) throws CheckErrorException, ParseException {

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
        TrainTicket ticket = new TrainTicket(request, ticketNo, trainUuid, price, checkErrorException);
        trainTicketRepository.save(ticket);

        //如果有錯 拋錯誤
        if (checkErrorException.hasErrors()) {
            throw checkErrorException;
        }
        return ticket.getTicketNo();
    }

}
