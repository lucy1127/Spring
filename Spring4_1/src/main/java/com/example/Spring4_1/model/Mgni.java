package com.example.Spring4_1.model;

import com.example.Spring4_1.model.Cashi;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


//@Builder
//@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "mgni")
////@XmlRootElement(name = "MgniResponse")
////@XmlAccessorType(XmlAccessType.FIELD)
//@EntityListeners(AuditingEntityListener.class)
public class Mgni implements Serializable {

    private String id;  //MGI+yyyymmdd+hhmmssfff

    //    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime time;

    private String type; //1:入金

    private String memberCode;

    private String kacType; //1:結算保證金 2:交割結算基金專戶

    private String bankCode;

    private String currency;

    private String pvType;//1:虛擬帳戶 2:實體帳戶

    private String bicaccNo;// 存放實體帳號或是虛擬帳號x

    private String iType; //1:開業 2:續繳 3:其他 4:額外分擔金額

    private String reason;

    private BigDecimal amt;

    private String contactName;


    private String contactPhone;

    private String status;

    //    @LastModifiedDate //更新時間
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    private List<Cashi> cashiList;
}
