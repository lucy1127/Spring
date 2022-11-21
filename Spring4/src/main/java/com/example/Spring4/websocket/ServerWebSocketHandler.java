package com.example.Spring4.websocket;

import com.example.Spring4.controller.dto.request.Request;
import com.example.Spring4.model.entity.Mgni;
import com.example.Spring4.service.MgniService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class ServerWebSocketHandler extends TextWebSocketHandler implements SubProtocolCapable {
    @Autowired
    private MgniService mgniService;
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = Logger.getLogger(ServerWebSocketHandler.class);

    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Server connection opened");
        sessions.add(session);

        TextMessage message = new TextMessage("one-time message from server");
//        logger.info("Server sends: {}", message);
        session.sendMessage(message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
//        logger.info("Server connection closed: {}", status);
        sessions.remove(session);
    }

    @Scheduled(fixedRate = 10000)
    void sendPeriodicMessages() throws IOException {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                String broadcast = "server periodic message " + LocalTime.now();
                logger.info("Server sends: {}" + broadcast);
                session.sendMessage(new TextMessage(broadcast));
            }
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        mapper.findAndRegisterModules();
        Request request = new Gson().fromJson(message.getPayload(), Request.class);
        String response = "";

        ObjectMapper objectMapper = new ObjectMapper();
        switch (request.getType().toLowerCase()) {
            case "select":
                response = "response　：　" + mapper.writeValueAsString(mgniService.getData());
                break;
            case "create":
                response = "response　：　" + mapper.writeValueAsString(mgniService.createSettlementMargin(request.getRequest()));
                break;
            case "update":
                response = "response　：　" + mapper.writeValueAsString(mgniService.updateData(request.getRequest()));
                break;
            case "delete":
                response = "response　：　" + mapper.writeValueAsString(mgniService.deleteData(request.getRequest().getId()));
                break;
            case "exit":
                System.out.println("Disconnection..... ");
                response = "Disconnection";
                break;
            default:
                break;

        }

//        String response = String.format("response from server to '%s'", HtmlUtils.htmlEscape(request));
        logger.info("Server sends: {}" + response);
        session.sendMessage(new TextMessage(response));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.info("Server transport error: {}" + exception.getMessage());
    }

    @Override
    public List<String> getSubProtocols() {
        return Collections.singletonList("subprotocol.demo.websocket");
    }
}