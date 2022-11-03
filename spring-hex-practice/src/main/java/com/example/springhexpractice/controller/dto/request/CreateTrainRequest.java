package com.example.springhexpractice.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTrainRequest {

    @NotNull(message = "train_no cannot be empty")
    private Integer train_no; //車次

    @NotNull(message = "train_kind cannot be empty")
    private String train_kind; //車種

    @NotEmpty(message = "停靠站不可為空")
    private List<TrainStop> stops;

}
