package com.example.Spring4_1.response;

import com.example.Spring4_1.model.Mgni;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
