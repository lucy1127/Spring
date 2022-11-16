package com.example.springhexpractice.model.entity;


import com.example.springhexpractice.exception.CheckErrorException;
import com.example.springhexpractice.exception.ErrorCode;
import com.example.springhexpractice.service.domainLayer.internalUtil.TrainKind;
import lombok.*;

import javax.persistence.*;


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
        this.checkParameter(trainNo, trainKind, e);
        this.checkTrainNoIsPositive(trainNo, e);
        this.checkTrainKind(trainKind, e);
        //
        this.uuid = uuid;
        this.trainNo = trainNo;
        this.trainKind = TrainKind.getKind(trainKind);
    }

    public void checkParameter(int trainNo, String trainKind, CheckErrorException e) {
        if (trainNo == 0) {
            e.getCheckError().add(ErrorCode.TrainNoIsEmpty);
        }
        if (trainKind.isEmpty()) {
            e.getCheckError().add(ErrorCode.TrainKindIsEmpty);
        }
    }

    public void checkTrainNoIsPositive(Integer trainNo, CheckErrorException e) {
        if (!(Math.round(trainNo) == trainNo) || trainNo < 0) {
            e.getCheckError().add(ErrorCode.TrainNoIsPositive);
        }
    }

    public void checkTrainKind(String trainKind, CheckErrorException e) {
        if (null == TrainKind.getKind(trainKind)) {
            e.getCheckError().add(ErrorCode.TrainKind);
        }
    }


}
