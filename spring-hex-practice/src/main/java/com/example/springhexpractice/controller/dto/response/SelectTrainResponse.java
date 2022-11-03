package com.example.springhexpractice.controller.dto.response;

import com.example.springhexpractice.controller.dto.request.TrainStop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SelectTrainResponse {

    private Integer train_no; //車次

    private String train_kind; //車種

    private List<TrainStop> stops;

}
