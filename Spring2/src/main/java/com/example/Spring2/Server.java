package com.example.Spring2;


import com.example.Spring2.request.Request;
import com.example.Spring2.service.CRUD;
import com.google.gson.Gson;
import org.apache.log4j.Logger;


import java.net.*;
import java.io.*;
import java.sql.*;


public class Server extends Thread {

    // JDBC driver name and database URL
//    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://localhost:3306/mgn";
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";
    public static Connection conn = null;
    static Statement stmt = null;

    private static final Logger logger = Logger.getLogger(Server.class);

    private final Socket s;

    public Server(Socket socket) {
        this.s = socket;
    }

    @Override
    public void run() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            System.out.println("Client Connected");

            BufferedReader bu = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String str = bu.readLine(); //close
            Request request = new Gson().fromJson(str, Request.class);

            String temp = "";

            switch (request.getType()) {
                case "selectMgni":
                    temp = CRUD.selectMgni(stmt);
                    break;
                case "selectCashi":
                    temp = CRUD.selectCashi(stmt);
                    break;
                case "create":
                    temp = CRUD.create(request.getRequestList());
                    break;
                case "update":
                    temp = CRUD.update(request.getRequestList());
                    break;
                case "delete":
                    temp = CRUD.delete(stmt, request.getRequestList());
                    break;
                case "exit":
                    System.out.println("Disconnection..... ");
                    temp = "Disconnection";
                    s.close();
                    break;
                default:
                    break;

            }

            temp = "{\"message\":\"" + temp + "\"}";
            writeOutput(temp, s);

//            logger.info("Ok !");
            s.close();
        } catch (SQLException e) {
            logger.error("Error Message: ", new SQLException(e.getMessage()));
        } catch (SocketException e) {
            logger.error("Error Message: ", new SocketException("Socket is closed"));
        } catch (NullPointerException e) {
            logger.error("Error Message: ", new NullPointerException(e.getMessage()));
        } catch (Exception e) {
            logger.error("Error Message: ", new Exception("Internal server problem"));
        }
    }

    public static void writeOutput(String message, Socket s) throws IOException {
        PrintWriter pr = new PrintWriter(s.getOutputStream());
        pr.println(message);
        pr.flush();
    }
}



