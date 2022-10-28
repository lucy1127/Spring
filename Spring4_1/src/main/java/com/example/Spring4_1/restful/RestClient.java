package com.example.Spring4_1.restful;

import com.example.Spring4_1.request.MgniRequest;
import com.example.Spring4_1.request.Request;
import com.example.Spring4_1.response.DeleteResponse;
import com.example.Spring4_1.model.Mgni;
import com.example.Spring4_1.response.MgniResponse;
import com.example.Spring4_1.response.UpdateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

public class RestClient {
    private static final Logger logger = Logger.getLogger(RestClient.class);
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void sendRestFulMessage(String message) {


        String url = "http://localhost:8080/spring1";

        String response = "";

        try {
            Request request = new Gson().fromJson(message, Request.class);

            switch (request.getType().toLowerCase()) {
                case "select":
                    url += "/getList";
                    MgniResponse mgniResponse = restTemplate.getForObject(url, MgniResponse.class);
                    response = writeResponse(mgniResponse);
                    break;
                case "create":
                    url += "/create";
                    MgniRequest createMgni = request.getRequest();
                    Mgni mgni = restTemplate.postForObject(url, createMgni, Mgni.class);
                    response = writeResponse(mgni);
                    break;
                case "update":
                    url += "/update";
                    MgniRequest updateMgniRequest = request.getRequest();
                    UpdateResponse updateResponse = restTemplate.postForObject(url, updateMgniRequest, UpdateResponse.class);
                    response = writeResponse(updateResponse);
                    break;
                case "delete":
                    url += "/delete";
                    String deleteData = request.getRequest().getId();
                    DeleteResponse deleteResponse = restTemplate.postForObject(url, deleteData, DeleteResponse.class);
                    response = writeResponse(deleteResponse);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            logger.error("Error Message: " + e.getMessage());
        } finally {
            System.out.println(response);
        }

    }

    private static String writeResponse(Object object) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }


}