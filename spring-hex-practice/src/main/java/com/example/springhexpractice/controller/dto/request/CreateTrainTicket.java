package com.example.springhexpractice.controller.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTrainTicket {

    @NotNull(message = "車次不可為空")
    private Integer train_no;

    @NotEmpty(message = "上車站不可為空")
    private String from_stop;

    @NotEmpty(message = "下車站不可為空")
    private String to_stop;

    @NotEmpty(message = "日期不可為空")
    @Pattern(regexp ="((((19|20)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((19|20)\\d{2})-(0?[469]|11)-(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-(0?[1-9]|[12]\\d)))$"
            , message = "日期格式不正確 yyyy-mm-dd")
    private String take_date;

}
