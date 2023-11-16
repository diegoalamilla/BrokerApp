package com.arquitectura.brokerappserver.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

public class Service {
    private String name;
    private int parameters;

    public Service(){
        this.name = "";
        this.parameters = 0;
    }

    public Service(String name, int parameters){
        this.name = name;
        this.parameters = parameters;
    }

    public String getName(){
        return this.name;
    }


    public int getParameters(){
        return this.parameters;
    }
    public void setName(String name){
        this.name = name;
    }

    public void setParameters(int parameters){
        this.parameters = parameters;
    }

    public void registerService(String ip, int port){
        JsonObjectBuilder requestBuilder = Json.createObjectBuilder();
        requestBuilder.add("servicio","registrar")
                        .add("variables",4)
                            .add("variable1","servidor")
                                .add("valor1",ip)
                                    .add("variable2","puerto")
                                        .add("valor2", port)
                                            .add("variable3","servicio")
                                                .add("valor3",this.name)
                                                    .add("variable4","parametros")
                                                        .add("valor4",this.parameters);
        JsonObject requestJSONObject = requestBuilder.build();

        try {
            Client.conexion(requestJSONObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public String executeService(String request){return "";}

    public static String processRequest(String request){
        String response = "";
        JsonObject jsonObject = Json.createReader(new ByteArrayInputStream(request.getBytes())).readObject();
        String serviceName = jsonObject.getString("servicio");
        if(serviceName.equals("contar")){
           ServiceCount service = new ServiceCount();
              response = service.executeService(request);
        }
        if(serviceName.equals("listar")){
            ServiceList service = new ServiceList();
            response = service.executeService(request);
        }
        if(serviceName.equals("registrar")){
            ServiceRegister service = new ServiceRegister();
            response = service.executeService(request);
        }
        if(serviceName.equals("votar")){
            ServiceVote service = new ServiceVote();    
            response = service.executeService(request);
        }
        return response;
    }
}
