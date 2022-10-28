package com.example.Spring2.request;

import com.example.Spring2.model.Account;
import com.example.Spring2.model.MyAnnotation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.util.List;

import static com.example.Spring2.model.MyAnnotation.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MgniRequest {

    private String id;
    //    @MyAnnotation(value = Mgni_Type.TYPE,length = 7,message = "請輸入 1(入金) 且長度不超過1")
    @NotBlank(message = "type cannot be empty")
    @Length(max = 1,message = "The string length of type must be no more than 1")
    @Pattern(regexp = "^$|(1)",message = "輸入錯誤，請輸入 1(入金)")
    private String type; //1:入金
    @NotBlank(message = "memberCode cannot be empty")
    @Length(max = 7,message = "The string length of memberCode must be less than 7")
    private String memberCode;
    @NotBlank(message = "kacType cannot be empty")
    @Length(max = 2,message = "The string length of kacType must be less than 2")
    @Pattern(regexp = "^$|([12])",message = "輸入錯誤，請輸入 1(結算保證金) ,2(交割結算基金專戶)")
    private String kacType; //1:結算保證金 2:交割結算基金專戶
    @NotBlank(message = "bankCode cannot be empty")
    @Length(max = 3,message = "The string length of bankCode must be less than 3")
    @Pattern(regexp = "^$|[0-9]{3}",message = "輸入錯誤,請輸入3碼數字")
    private String bankCode;
    @NotBlank(message = "currency cannot be empty")
    @Length(max = 3,message = "The string length of currency must be less than 3")
    @Pattern(regexp = "^$|(TWD|USD)",message = "格式輸入錯誤")
    private String currency;//TWD
    @NotBlank(message = "pvType cannot be empty")
    @Length(max = 1,message = "The string length of pvType must be no more than 1")
    @Pattern(regexp = "^$|([12])",message = "格式輸入錯誤")
    private String pvType;//1:虛擬帳戶 2:實體帳戶
    @NotBlank(message = "account cannot be empty")
    @Length(max = 21,message = "The string length of account must be no more than 21")
    private String account;
    @Pattern(regexp = "^$|([1234])",message = "格式輸入錯誤")
    private String iType;

    private List<Account> accountList;
    @DecimalMin(value = "0", inclusive = false, message = "price must be greater than 0")
    @Digits(integer = 10, fraction = 4, message = "digits of price is not correct")
    private BigDecimal amt;
    private String reason;
    @NotBlank(message = "contactName cannot be empty")
    private String contactName;
    @NotBlank(message = "contactPhone cannot be empty")
    private String contactPhone;
    @Pattern(regexp = "^$|([012567])",message = "格式輸入錯誤")
    private String status;
}
