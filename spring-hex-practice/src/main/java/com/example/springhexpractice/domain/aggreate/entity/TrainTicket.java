package com.example.springhexpractice.domain.aggreate.entity;

import com.example.springhexpractice.iface.controller.dto.request.CreateTrainTicketRequest;
import com.example.springhexpractice.iface.controller.exception.CheckErrorException;
import com.example.springhexpractice.domain.aggreate.util.ErrorCode;
import lombok.*;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "train_ticket")
@Getter
@Setter
@NoArgsConstructor
public class TrainTicket {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "TICKET_NO")
    private String ticketNo;//車票號碼

    @Column(name = "TRAIN_UUID")
    private String trainUuid;//車輛UUID

    @Column(name = "FROM_STOP")
    private String fromStop;//上車站名

    @Column(name = "TO_STOP")
    private String toStop;//下車站名

    @Column(name = "TAKE_DATE")
    private Date takeDate;//搭乘日期

    @Column(name = "PRICE")
    private Double price;//票價

    public TrainTicket(CreateTrainTicketRequest request, String ticketNo, String trainUuid, Double price, CheckErrorException e) throws ParseException {
        //check
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        this.checkDate(request.getTakeDate(), e);
        //
        this.ticketNo = ticketNo;
        this.trainUuid = trainUuid;
        this.fromStop = request.getFromStop();
        this.toStop = request.getToStop();
        this.takeDate = dt.parse(request.getTakeDate());
        this.price = price;
    }

    public void checkDate(String date, CheckErrorException e) {
        Pattern pattern = Pattern.compile("((((19|20)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((19|20)\\d{2})-(0?[469]|11)-(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-(0?[1-9]|[12]\\d)))$");
        Matcher matcher = pattern.matcher(date);
        if (!matcher.matches()) {
            e.getCheckError().add(ErrorCode.TAKE_DATE);
        }
    }


}
