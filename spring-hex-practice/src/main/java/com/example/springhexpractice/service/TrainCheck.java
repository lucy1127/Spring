package com.example.springhexpractice.service;

import com.example.springhexpractice.controller.dto.response.SelectTrainResponse;
import com.example.springhexpractice.controller.error.ErrorMessage;
import com.example.springhexpractice.controller.error.StopNotFoundException;
import com.example.springhexpractice.controller.serviceAPI.CheckStatus;
import com.example.springhexpractice.controller.dto.request.TrainStop;
import com.example.springhexpractice.controller.error.CheckCombinedInspectionException;
import com.example.springhexpractice.controller.error.CheckTrainException;
import com.example.springhexpractice.model.TrainRepository;
import com.example.springhexpractice.model.TrainStopRepository;
import com.example.springhexpractice.model.entity.Train_Stop;
import com.example.springhexpractice.model.trainModel.EnumTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class TrainCheck {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TrainRepository trainRepository;

    @Autowired
    TrainStopRepository trainStopRepository;

    private final String[] places = {"屏東", "高雄", "臺南", "嘉義", "彰化", "台中", "苗粟", "新竹", "桃園", "樹林",
            "板橋", "萬華", "台北", "松山", "南港", "汐止", "基隆"};

    public void checkTrainStatus(Integer trainNo) {

        //呼叫 status-service API 檢核車次是否有效
        String url = "https://petstore.swagger.io/v2/pet/" + trainNo;

        ResponseEntity<CheckStatus> response = restTemplate.getForEntity(url, CheckStatus.class);
        int code = response.getStatusCodeValue();
        if (code == 200) {
            CheckStatus check = response.getBody();
            if (!check.getStatus().equals("available"))
                throw new CheckTrainException();
        }
    }


    public void checkCombinedInspection(Integer trainNo, String trainKind, List<TrainStop> request, List<String> stopsName) {

        List<Map<String, String>> checkErrorList = new ArrayList<>();
        Map<String, String> map;

        if (null != trainRepository.findByTrainNo(trainNo)) {
            map = new HashMap<>();
            map.put("code", "TrainNoExists");
            map.put("message", "Train No is exists");
            checkErrorList.add(map);
        } //車次是否已存在

        if (null == new EnumTest(trainKind).trainChangeType()) {
            map = new HashMap<>();
            map.put("code", "TrainKindInvalid");
            map.put("message", "Train Kind is invalid");
            checkErrorList.add(map);
        } //車種是否合法

        if (request.size() != stopsName.size()) {
            map = new HashMap<>();
            map.put("code", "TrainStopsDuplicate");
            map.put("message", "TTrain Stops is duplicate");
            checkErrorList.add(map);
        }

        if (checkErrorList.size() > 0) {
            throw new CheckCombinedInspectionException("VALIDATE_FAILED", checkErrorList);
        }
    }

    public void sortedCheck(List<TrainStop> stops) throws ParseException {
        DateFormat dt = new SimpleDateFormat("HH:mm");
        for (int i = 0; i < stops.size() - 1; i++) {
            try {
                Date date1 = dt.parse(stops.get(i).getStop_time().toString());
                Date date2 = dt.parse(stops.get(i + 1).getStop_time().toString());
                if (date1.getTime() >= date2.getTime()) {
                    List list = new ArrayList<>();
                    Map<String, String> map = new HashMap<>();
                    map.put("code", "TrainStopsNotSorted");
                    map.put("message", "Train Stops is not sorted");
                    list.add(map);
                    throw new CheckCombinedInspectionException("VALIDATE_FAILED", list);//"TrainStopsNotSorted", "Train Stops is not sorted"
                }
            } catch (ParseException e) {

            }

        }
    }

    public void stopPositionNotRight(List<TrainStop> stopsList) {

        boolean correct = false;

        List<String> stops = new ArrayList<>();
        for (TrainStop n : stopsList) {
            stops.add(n.getStop_name()); //把新增的站名  苗粟 新竹 桃園
        }

        List<Integer> stopNumbers = new ArrayList<>();

        for (String s : stops) {
            for (int j = 0; j < places.length; j++) {
                if (s.equals(places[j])) {
                    System.out.println(j + "place");
                    stopNumbers.add(j);
                    break;
                }
            }

        }

        if (stopNumbers.get(0) < stopNumbers.get(stopNumbers.size() - 1)) {
            for (int i = 0; i < stopNumbers.size() - 1; i++) {
                if (stopNumbers.get(i) > stopNumbers.get(i + 1))
                    correct = true;
            }
        } else if (stopNumbers.get(0) > stopNumbers.get(stopNumbers.size() - 1)) {
            for (int i = 0; i < stopNumbers.size() - 1; i++) {
                if (stopNumbers.get(i) < stopNumbers.get(i + 1))
                    correct = true;
            }
        }


        if (correct == true) {
            List list = new ArrayList<>();
            Map<String, String> map = new HashMap<>();
            map.put("code", "TrainStopPositionNotRight");
            map.put("message", "Train Stops position is not right");
            list.add(map);
            throw new CheckCombinedInspectionException("VALIDATE_FAILED", list);
        }
    }


    public void checkStop(Integer trainNo, String fromStop, String toStop) {

        List list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();

        String trainUid = trainRepository.findByTrainNo(trainNo).getUuid();

        //檢查是否有車站
        if (trainStopRepository.findByUuidAndStop(trainUid, fromStop) == null
                || trainStopRepository.findByUuidAndStop(trainUid, toStop) == null) {
            throw new StopNotFoundException("車站不存在");
        }

        //順序
        int fromSeq = trainStopRepository.findByUuidAndName(trainUid, fromStop);
        int toSeq = trainStopRepository.findByUuidAndName(trainUid, toStop);

        if (fromSeq > toSeq) {
            map.put("code", "TicketStopsInvalid");
            map.put("message", "Ticket From & To is invalid");
            list.add(map);
        }

        //相同
        if (fromStop.equals(toStop)) {
            map.put("code", "TicketStopsInvalid");
            map.put("message", "Ticket From & To is invalid");
            list.add(map);
        }

        if (list.size() > 0) {
            throw new CheckCombinedInspectionException("VALIDATE_FAILED", list);
        }
    }

}
