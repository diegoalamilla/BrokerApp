package com.arquitectura.brokerappbroker.model;


import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Broker {
    static ArrayList<Service> listOfServices = new ArrayList<>();

    public static String processRequest(String request){
      try {
        JsonObject jsonObject = Json.createReader(new ByteArrayInputStream(request.getBytes())).readObject();
        
      if (jsonObject.getString("servicio").equals("registrar")) {
         return processRegister(request);
         
      }
      if (jsonObject.getString("servicio").equals("listar") && jsonObject.getString("variables").equals("1")) {

         return processList(jsonObject.getString("valor1"));
      }

      return Client.conexion(jsonObject);

     } catch (Exception e) {
        e.printStackTrace();
     }
        return "";
        
        
    }
    public static String processList(String service){
        JsonObjectBuilder answerBuilder = Json.createObjectBuilder();
        if(service.equals("")){
             answerBuilder.add("servicio","listar")
                        .add("respuestas",listOfServices.size());
        for (int i = 0; i < listOfServices.size(); i++) {
            answerBuilder.add("respuesta"+(i+1),listOfServices.get(i).getName())
                        .add("valor"+(i+1),listOfServices.get(i).getServerIP());
        }
        JsonObject requestJSONObject = answerBuilder.build();
        return requestJSONObject.toString();
        }else{
            Service serviceNeeded = getService(service);
            
            answerBuilder.add("servicio","listar")
                        .add("respuestas",1)
                        .add("respuesta1",serviceNeeded.getName())
                        .add("valor1",serviceNeeded.getServerIP());
            JsonObject requestJSONObject = answerBuilder.build();
            return requestJSONObject.toString();
        }
       
    }
    public static String processRegister(String request){
        try {
              JsonObject jsonObject = Json.createReader(new ByteArrayInputStream(request.getBytes())).readObject();
              String serverIP = jsonObject.getString("valor1");
              int port = jsonObject.getInt("valor2");
              String serviceName = jsonObject.getString("valor3");
              int parametersQuantity = jsonObject.getInt("valor4");
              Service service = new Service();
              service.setName(serviceName);
              service.setServerIP(serverIP);
              service.setPort(port);
              service.setParametersQuantiy(parametersQuantity);
              Broker.getServices().add(service);
            JsonObjectBuilder answerBuilder = Json.createObjectBuilder();
            answerBuilder.add("servicio","registrar")
                        .add("respuestas",1)
                        .add("respuesta1","identificador")
                        .add("valor1",Broker.getServices().size());
            JsonObject requestJSONObject = answerBuilder.build();
            return requestJSONObject.toString();
        } catch (Exception e) {
              e.printStackTrace();
        }
        return "";
     }


     public static ArrayList<Service> getServices() {
        return listOfServices;
    }
    public static Service getService(String name){
        for (int i = 0; i < listOfServices.size(); i++) {
            if(listOfServices.get(i).getName().equals(name)){
                return listOfServices.get(i);
            }
        }
        return null;
    }

    public void setServices(ArrayList<Service> services) {
        Broker.listOfServices = services;
    }

}
