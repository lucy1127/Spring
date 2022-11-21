package com.example.springhexpractice.domain.aggreate.entity;


import com.example.springhexpractice.config.exception.CheckErrorException;
import com.example.springhexpractice.domain.aggreate.valueObject.ErrorCode;
import com.example.springhexpractice.domain.aggreate.valueObject.TrainKind;
import com.example.springhexpractice.config.exception.DataNotFoundException;
import com.example.springhexpractice.domain.commands.CreateTrainCommand;
import com.example.springhexpractice.iface.controller.dto.request.TrainStopRequest;
import com.example.springhexpractice.iface.controller.dto.response.SelectStopResponse;
import lombok.*;

import javax.persistence.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "train")
@Getter
@NoArgsConstructor
public class Train {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "TRAIN_NO")
    private int trainNo; //車次

    @Column(name = "TRAIN_KIND")
    private String trainKind; //車種


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "trainUuid",cascade = CascadeType.ALL)
    private List<TrainStop> trainStopList;

    public Train(String uuid, int trainNo, String trainKind, List<TrainStop> trainStopList,CheckErrorException e) {
        //check
        this.checkTrainNoIsPositive(trainNo, e);
        this.checkTrainKind(trainKind, e);
        //
        this.uuid = uuid;
        this.trainNo = trainNo;
        this.trainKind = TrainKind.getKind(trainKind);
        this.trainStopList = trainStopList;
    }

    //------------------------------------------------------------------------------------------------------------------
    public static Train create(String uuid, CreateTrainCommand request,List<TrainStopRequest> stopSorted,CheckErrorException checkErrorException){
        List<TrainStop> trainStopList = createStop(uuid,stopSorted,checkErrorException);
        return new Train(uuid, request.getTrainNo(), request.getTrainKind(),trainStopList, checkErrorException);
    }
    public static List<TrainStop> createStop(String uuid,List<TrainStopRequest> stopSorted,CheckErrorException e){
        List<TrainStop> trainStopList = new ArrayList<>();
        stopSorted.forEach((TrainStopRequest stops) -> {
            TrainStop trainStop = null;
            try {
                trainStop = new TrainStop(uuid, stopSorted.indexOf(stops) + 1, stops.getStopName()
                        , stops.getStopTime(), "N", e);
                trainStopList.add(trainStop);
            } catch (ParseException p) {
                throw new RuntimeException(p);
            }
        });
        return trainStopList;
    }
    //------------------------------------------------------------------------------------------------------------------
    public void checkTrainNoIsPositive(Integer trainNo, CheckErrorException e) {
        if (!(Math.round(trainNo) == trainNo) || trainNo < 0) {
            e.getCheckError().add(ErrorCode.TRAIN_NO_IS_POSITIVE);
        }
    }
    public void checkTrainKind(String trainKind, CheckErrorException e) {
        if (null == TrainKind.getKind(trainKind)) {
            e.getCheckError().add(ErrorCode.TRAIN_KIND);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    public static void checkTrainIsExist(Train trainStop) {
        if (null == trainStop) {
            throw new DataNotFoundException("車次不存在");
        }
    }
    public static void checkTrainListIsExist(List<SelectStopResponse> train) {
        if (train.isEmpty()) {
            throw new DataNotFoundException("車站不存在");
        }
    }
    //------------------------------------------------------------------------------------------------------------------

}
