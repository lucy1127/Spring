package com.example.Spring4.controller.dto.response;


import com.example.Spring4.model.entity.Mgni;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement(name = "MgniResponse")
public class MgniResponse {
    private List<Mgni> mgniList;
    private String message;
}
