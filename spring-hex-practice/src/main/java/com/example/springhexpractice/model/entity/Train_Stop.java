package com.example.springhexpractice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "train_stop")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Train_Stop {

    @Id
    @Column(name = "UUID")
    private String uuid;

    @Column(name = "TRAIN_UUID")
    private String train_uuid;//車輛UUID

    @Column(name  = "SEQ")
    private int seq;//停靠順序

    @Column(name  = "NAME")

    private String name;//停靠站名

    @Column(name  = "TIME")
    private LocalTime time;//停靠時間

    @Column(name  = "DELETE_FLAG")
    private String delete_flag;//是否刪除

}
