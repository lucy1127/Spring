//package com.example.Spring4_1.socket;
//
//import org.json.simple.JSONObject;
//
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//
//
//public class SocketClient_heart implements Runnable{
//    private Socket socket;
////    private ObjectOutputStream objectOutputStream;
//
//    public SocketClient_heart(Socket socket) {
//        this.socket = socket;
////        this.objectOutputStream = objectOutputStream;
//    }
//
//    @Override
//    public void run() {
//        try {
//            while (true) {
//                System.out.println("heart start running.");
//                Thread.sleep(5000);
////                JSONObject jsonObject = new JSONObject();
////                jsonObject.put("type", "heart");
////                jsonObject.put("msg", "heart");
////                objectOutputStream.writeObject(jsonObject);
////                objectOutputStream.flush();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                socket.close();
//                SocketClient.connection_state = false;
//                SocketClient.reconnect();
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//        }
//    }
//}