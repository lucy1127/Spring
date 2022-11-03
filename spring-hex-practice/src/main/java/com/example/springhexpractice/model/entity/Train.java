package com.example.springhexpractice.model.entity;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "train")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Train {

    @Id
    @Column(name = "uuid")
    private String uuid;


    @Column(name = "TRAIN_NO")
    private Integer train_no; //車次

    @Column(name = "TRAIN_KIND")
    private String train_kind; //車種

}
