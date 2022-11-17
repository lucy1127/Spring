package com.example.springhexpractice.domain.aggreate.entity;

import com.example.springhexpractice.iface.controller.dto.request.TrainStopRequest;
import com.example.springhexpractice.iface.controller.exception.CheckErrorException;
import com.example.springhexpractice.domain.aggreate.util.ErrorCode;
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
        this.checkTime(time,e);
        //
        this.uuid = uuid;
        this.trainUuid = trainUuid;
        this.seq = seq;
        this.name = name;
        this.time = df.parse(time);
        this.deleteFlag = deleteFlag;
    }
    //------------------------------------------------------------------------------------------------------------------

    public void checkTime(String time,CheckErrorException e) {
        Pattern pattern = Pattern.compile("([01]\\d|2?[0-3]):([0-5][0-9])$");
        Matcher matcher = pattern.matcher(time);

        if (!matcher.matches()) {
            e.getCheckError().add(ErrorCode.WRONG_TIME_FORMAT);
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    public static void checkTrainStopDuplicate(CheckErrorException e, List<TrainStopRequest> request, List<String> stopsName) {
        if (request.size() != stopsName.size()) {
            e.getCheckError().add(ErrorCode.TRAIN_MULTI_STOP);
        }
    }

    public static void stopPositionNotRight(CheckErrorException e, List<TrainStopRequest> stopsList) {
        String[] places = {"屏東", "高雄", "臺南", "嘉義", "彰化", "台中", "苗粟", "新竹", "桃園", "樹林",
                "板橋", "萬華", "台北", "松山", "南港", "汐止", "基隆"};

        List<Integer> stopNumbers = new ArrayList<>();

        for (TrainStopRequest n : stopsList) {
            for (int j = 0; j < places.length; j++) {
                if (n.getStopName().equals(places[j])) {
                    stopNumbers.add(j);
                }
            }
        }
        if (stopNumbers.get(0) < stopNumbers.get(stopNumbers.size() - 1)) {
            for (int i = 0; i < stopNumbers.size() - 1; i++) {
                if (stopNumbers.get(i) > stopNumbers.get(i + 1))
                    e.getCheckError().add(ErrorCode.TRAIN_STOP_SORTED);
            }
        } else {
            for (int i = 0; i < stopNumbers.size() - 1; i++) {
                if (stopNumbers.get(i) < stopNumbers.get(i + 1))
                    e.getCheckError().add(ErrorCode.TRAIN_STOP_SORTED);
            }
        }

    }


}
