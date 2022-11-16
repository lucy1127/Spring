package com.example.springhexpractice.model.entity;

import com.example.springhexpractice.exception.CheckErrorException;
import com.example.springhexpractice.exception.ErrorCode;
import lombok.*;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "train_stop")
@Getter
@Setter
@NoArgsConstructor
public class TrainStop {

    @Id
    @Column(name = "UUID")
    private String uuid;

    @Column(name = "TRAIN_UUID")
    private String trainUuid;//車輛UUID

    @Column(name = "SEQ")
    private int seq;//停靠順序

    @Column(name = "NAME")
    private String name;//停靠站名

    @Column(name = "TIME")
    private Date time;//停靠時間

    @Column(name = "DELETE_FLAG")
    private String deleteFlag;//是否刪除

    public TrainStop(String uuid, String trainUuid, int seq, String name, String time, String deleteFlag, CheckErrorException e) throws ParseException {
        //check
        DateFormat df = new SimpleDateFormat("HH:mm");
        this.checkNameAndTime(name, time,e);
        this.checkTime(time,e);
        //
        this.uuid = uuid;
        this.trainUuid = trainUuid;
        this.seq = seq;
        this.name = name;
        this.time = df.parse(time);
        this.deleteFlag = deleteFlag;
    }

    public void checkNameAndTime(String name, String time,CheckErrorException e) {
        if (name.isEmpty()) {
            e.getCheckError().add(ErrorCode.Stops);
        }
        if (time.isEmpty()) {
            e.getCheckError().add(ErrorCode.TrainStopTimeIsEmpty);
        }
        if (name.length() > 20) {
            e.getCheckError().add(ErrorCode.TrainStopNameTooLong);
        }
    }

    public void checkTime(String time,CheckErrorException e) {
        Pattern pattern = Pattern.compile("([01]\\d|2?[0-3]):([0-5][0-9])$");
        Matcher matcher = pattern.matcher(time);

        if (!matcher.matches()) {
            e.getCheckError().add(ErrorCode.WrongTimeFormat);
        }

    }

}
