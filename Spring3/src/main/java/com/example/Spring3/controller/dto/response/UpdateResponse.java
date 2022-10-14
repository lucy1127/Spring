package com.example.Spring3.controller.dto.response;

import com.example.Spring3.model.entity.Mgni;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "UpdateResponse")
public class UpdateResponse {
    private Mgni mgni;
    private String message;
}
