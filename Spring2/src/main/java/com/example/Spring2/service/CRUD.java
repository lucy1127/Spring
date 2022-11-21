package com.example.Spring2.service;

import com.example.Spring2.model.Account;
import com.example.Spring2.request.MgniRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.Spring2.Server.conn;
import static com.example.Spring2.model.Time.localDate;
import static com.example.Spring2.model.Time.localTime;
import static java.lang.Thread.sleep;


public class CRUD {
    private static BigDecimal price = BigDecimal.ZERO;
    private static int num = 0;

    public CRUD(int i) {
        this.num = i;
    }

    public static String selectMgni(Statement stmt) throws SQLException {
        String sql = "SELECT * FROM mgni";
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            //Retrieve by column name
            String id = rs.getString("id");
            String time = rs.getString("time");
            String type = rs.getString("type");
            String member = rs.getString("cm_no");
//                String kacType = rs.getString("kac_type");
//                String bankCode = rs.getString("bank_no");
//                String currency = rs.getString("ccy");
//                String pvType = rs.getString("pv_type");
//                String bicaccNo = rs.getString("bicacc_no");
//                String iType = rs.getString("i_type");
//                String reason = rs.getString("p_reason");
            BigDecimal amt = rs.getBigDecimal("amt");
//                String ctName = rs.getString("ct_name");
//                String ctPhone = rs.getString("ct_tel");
//                String status = rs.getString("status");
//                String updateTime = rs.getString("u_time");

            //Display values
            System.out.print("ID: " + id + "\n");
            System.out.print("time: " + time + "\n");
            System.out.println("type: " + type);
            System.out.println("cm_no: " + member);
            System.out.println("amt: " + amt + "\n");
        }
        rs.close();
        //conn.close()
        //stmt.close()
        //try with resource
        return "Success";
    }

    public static String selectCashi(Statement stmt) throws SQLException {
        String sql = "SELECT * FROM cashi";
        ResultSet rs = stmt.executeQuery(sql);
        //STEP 5: Extract data from result set
        while (rs.next()) {
            //Retrieve by column name
            String id = rs.getString("mgni_id");
            String acc_no = rs.getString("acc_no");
            String currency = rs.getString("ccy");
            BigDecimal amt = rs.getBigDecimal("amt");

            //Display values
            System.out.print("ID: " + id);
            System.out.print(", acc_no: " + acc_no);
            System.out.println(", ccy: " + currency);
            System.out.println(", amt: " + amt);
        }
        rs.close();
        return "Success";
    }

    public synchronized static String create(MgniRequest request) throws SQLException, InterruptedException {
//        waiting(); //connection pool thread pool

        String err = error(request);

        if (err.length() == 0) {

            List<String> queryList = new ArrayList<>();

            String id = "'" + "MGI" + localDate() + localTime() + "'" + ",";
            String currency = "'" + request.getCurrency() + "'" + ",";

            price = totalPrice(request);

            String mgni = "INSERT INTO mgni "
                    + "VALUES (" + id
                    + "'" + LocalDateTime.now() + "'" + ","
                    + "'" + request.getType() + "'" + ","
                    + "'" + request.getMemberCode() + "'" + ","
                    + "'" + request.getKacType() + "'" + ","
                    + "'" + request.getBankCode() + "'" + ","
                    + currency
                    + "'" + request.getPvType() + "'" + ","
                    + "'" + request.getAccount() + "'" + ","
                    + "'" + request.getIType() + "'" + ","
                    + "'" + request.getReason() + "'" + ","
                    + price + ","
                    + "'" + request.getContactName() + "'" + ","
                    + "'" + request.getContactPhone() + "'" + ","
                    + "'" + request.getStatus() + "'" + ","
                    + "'" + LocalDateTime.now() + "'" + ");";

            queryList.add(mgni);

            String cashi = "";
            if (request.getAccountList() != null) {
                for (Account s : request.getAccountList()) {
                    cashi = "INSERT INTO cashi VALUES (" + id + "'" + s.getAccNo() + "'" + "," + currency + s.getPrice() + ");";
                    queryList.add(cashi);
                }
            }

            for (String sql : queryList) {
                PreparedStatement st = conn.prepareStatement(sql);
                int rowInsert = st.executeUpdate();
                if (rowInsert > 0) {
                    System.out.println(sql);
                    System.out.println("Inserted  into the table...");
                }
            }

            return "Create Success";
        }
        return "Create Failed ";
    }

    public static void waiting() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            sleep(1000l);
            System.out.println("Thread:" + i);
        }
    }

    private static BigDecimal totalPrice(MgniRequest request) {
        BigDecimal price = BigDecimal.ZERO;
        if (request.getAccountList() != null) {

            for (Account s : request.getAccountList()) {
                price = price.add(s.getPrice());
            }
        } else {
            price = request.getAmt();
        }
        return price;
    }

    public synchronized static String update(MgniRequest request) throws SQLException {
        String err = error(request);
        if (err.length() == 0) {

            List<String> queryList = new ArrayList<>();
            String id = "'" + request.getId() + "'";
            String currency = "'" + request.getCurrency() + "'";

            price = totalPrice(request);

            String mgni = "UPDATE mgni SET "
                    + "type = " + "'" + request.getType() + "'" + ","
                    + "cm_no = " + "'" + request.getMemberCode() + "'" + ","
                    + "kac_type = " + "'" + request.getKacType() + "'" + ","
                    + "bank_no = " + "'" + request.getBankCode() + "'" + ","
                    + "ccy = " + "'" + request.getCurrency() + "'" + ","
                    + "pv_type = " + "'" + request.getPvType() + "'" + ","
                    + "bicacc_no = " + "'" + request.getAccount() + "'" + ","
                    + "i_type = " + "'" + request.getIType() + "'" + ","
                    + "p_reason = " + "'" + request.getReason() + "'" + ","
                    + "amt = " + price + ","
                    + "ct_name = " + "'" + request.getContactName() + "'" + ","
                    + "ct_tel = " + "'" + request.getContactPhone() + "'" + ","
                    + "status = " + "'" + request.getStatus() + "'" + ","
                    + "u_time = " + "'" + LocalDateTime.now() + "'"
                    + "WHERE id = " + "'" + request.getId() + "'";
            queryList.add(mgni);

            String cashi = "";
            if (request.getAccountList() != null) {
                for (Account s : request.getAccountList()) {
                    cashi = " UPDATE cashi SET"
                            + " acc_no = " + "'" + s.getAccNo() + "'" + ","
                            + " ccy = " + currency + ","
                            + " amt = " + s.getPrice()
                            + " WHERE mgni_id = " + id + "AND" + " acc_no = " + "'" + s.getAccNo() + "'" + "AND" + " ccy = " + currency + ";";
                    queryList.add(cashi);
                }
            }
//            System.out.println(queryList);

            for (String sql : queryList) {
                PreparedStatement st = conn.prepareStatement(sql);
                int rowInsert = st.executeUpdate();
                if (rowInsert > 0) {
                    System.out.println(sql);
                    System.out.println("Success ");
                }
            }

            conn.close();
            return "Update Success...";
        }
        return "Update Failed ";
    }

    public static String delete(Statement stmt, MgniRequest request) throws SQLException {

        try {
            String sql = "DELETE FROM mgni " +
                    "WHERE id = " + "'" + request.getId() + "'";
            int i = stmt.executeUpdate(sql);
            if (i > 0) {
                return "Delete Success...";
            }
            return "Delete Failed";
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

    }


    public static String error(MgniRequest check) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        // 根據validatorFactory拿到一個Validator
        Validator validator = validatorFactory.getValidator();
        // 使用validator對結果進行校驗
        Set<ConstraintViolation<MgniRequest>> result = validator.validate(check);
        StringBuilder stringBuilder = new StringBuilder();
        //把結果列印出來
        result.stream().map(v -> v.getPropertyPath() + " " + v.getMessage() + ": " + v.getInvalidValue())
                .forEach(System.out::println);
        for (ConstraintViolation<MgniRequest> e : result) {
            System.out.println(e.getMessage());
            stringBuilder.append(e.getMessage()).append("\n");
        }
        return stringBuilder.toString();
    }

}
