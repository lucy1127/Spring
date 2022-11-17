package com.example.springhexpractice.iface.controller.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTrainTicketRequest {


    @NotNull(message = "車次不可為空")
    @JsonProperty("train_no")
    private Integer trainNo;

    @NotEmpty(message = "上車站不可為空")
    @JsonProperty("from_stop")
    private String fromStop;

    @NotEmpty(message = "下車站不可為空")
    @JsonProperty("to_stop")
    private String toStop;

    @NotEmpty(message = "日期不可為空")
    @JsonProperty("take_date")
    private String takeDate;

}
