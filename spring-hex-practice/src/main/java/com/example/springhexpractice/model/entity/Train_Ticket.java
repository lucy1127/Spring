package com.example.springhexpractice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "train_ticket")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Train_Ticket {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "TICKET_NO")
    private String ticket_no;//車票號碼

    @Column(name = "TRAIN_UUID")
    private String train_uuid;//車輛UUID

    @Column(name = "FROM_STOP")
    private String from_stop;//上車站名

    @Column(name = "TO_STOP")
    private String to_stop;//下車站名

    @Column(name = "TAKE_DATE")
    private Date take_date;//搭乘日期

    @Column(name = "PRICE")
    private Double price;//票價
}
