package com.example.Spring4_1.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MgniRequest {

    private String id;
    private String type; //1:入金
    private String memberCode;
    private String kacType; //1:結算保證金 2:交割結算基金專戶
    private String bankCode;
    private String currency;//TWD
    private String pvType;//1:虛擬帳戶 2:實體帳戶
    private String account;
    private List<Account> accountList;
    private BigDecimal amt;
    private String reason;
    private String contactName;
    private String contactPhone;

}
