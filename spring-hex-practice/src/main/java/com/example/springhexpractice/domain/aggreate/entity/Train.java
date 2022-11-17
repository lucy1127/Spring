package com.example.springhexpractice.domain.aggreate.entity;


import com.example.springhexpractice.iface.controller.exception.CheckErrorException;
import com.example.springhexpractice.domain.aggreate.util.ErrorCode;
import com.example.springhexpractice.domain.aggreate.util.TrainKind;
import com.example.springhexpractice.iface.controller.exception.DataNotFoundException;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "train")
@Getter
@Setter
@NoArgsConstructor
public class Train {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "TRAIN_NO")
    private int trainNo; //車次

    @Column(name = "TRAIN_KIND")
    private String trainKind; //車種

    public Train(String uuid, int trainNo, String trainKind, CheckErrorException e) {
        //check
        this.checkTrainNoIsPositive(trainNo, e);
        this.checkTrainKind(trainKind, e);
        //
        this.uuid = uuid;
        this.trainNo = trainNo;
        this.trainKind = TrainKind.getKind(trainKind);
    }

    //-----------------------------------------------------------------------------------------------------------------
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
    //-----------------------------------------------------------------------------------------------------------------
    public static void checkTrainIsExist(List<Map<String, Object>> trainStop) {
        if (trainStop.isEmpty()) {
            throw new DataNotFoundException("車次不存在");
        }
    }
    public static void checkUuidIsExist(List<String> uuid) {
        if (uuid.isEmpty()) {
            throw new DataNotFoundException("車站不存在");
        }
    }



}
