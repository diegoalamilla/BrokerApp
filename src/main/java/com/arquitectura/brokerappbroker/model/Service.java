package com.arquitectura.brokerappbroker.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;



public class Service {
    private String name;
    private String serverIP;
    private int port;
    private int parametersQuantiy;
     

    public static String processRequest(String request){
     try {
        JsonObject jsonObject = Json.createReader(new ByteArrayInputStream(request.getBytes())).readObject();
        String respuesta = Client.conexion(jsonObject);
           return respuesta;
     } catch (Exception e) {
        e.printStackTrace();
     }
        return "";
        
        
    }
}
