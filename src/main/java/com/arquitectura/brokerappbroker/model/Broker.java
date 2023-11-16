package com.arquitectura.brokerappbroker.model;


import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.omg.CORBA.Request;

public class Broker {
    static ArrayList<Service> listOfServices = new ArrayList<>();

    public static String processRequest(String request){
      try {
        JsonObject jsonObject = Json.createReader(new ByteArrayInputStream(request.getBytes())).readObject();
        
      if (jsonObject.getString("servicio").equals("registrar") && jsonObject.getInt("variables") == 4) {

        return processRegister(jsonObject);
         
      }
      if (jsonObject.getString("servicio").equals("listar") && jsonObject.getInt("variables") == 1) {

        return processList(jsonObject.getString("valor1"));

      }
      if(jsonObject.getString("servicio").equals("ejecutar")){

        return processExecute(jsonObject);

      }

      //return Client.conexion(jsonObject);

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
    public static String processRegister(JsonObject request){
        try {
              String serverIP = request.getString("valor1");
              int port = request.getInt("valor2");
              String serviceName = request.getString("valor3");
              int parametersQuantity = request.getInt("valor4");
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

    private static String processExecute(JsonObject request){
        JsonObjectBuilder requestBuilder = Json.createObjectBuilder();
        if(request.getString("valor1").equals("contar")){
          requestBuilder.add("servicio","contar")
                        .add("variables",0);  

        }else if(request.getString("valor1").equals("votar")){
            requestBuilder.add("servicio", "votar")
                            .add("variables", 1)
                                .add("variable1", request.getString("variable2"))
                                    .add("valor1", request.getString("valor2"));
        }else if(request.getString("valor1").equals("registrar")){
            requestBuilder.add("servicio","registrar")
                            .add("variables",2)
                            .add("variable1","evento")
                            .add("valor1",request.getString("valor2"))
                            .add("variable2","fecha")
                            .add("valor2",request.getString("valor3"));

        }else if(request.getString("valor1").equals("listar")){
            requestBuilder.add("servicio","listar")
                            .add("variables",0);
                            
        }
        Service service = selectServerWithService(request.getString("valor1"));
        JsonObject requestToServer = requestBuilder.build();
        try {
            return Client.conexion(requestToServer , service.getServerIP(), service.getPort());
        } catch (Exception e) {
            
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

    private static Service selectServerWithService(String serviceName){
        ArrayList<Service> servicesEquals = new ArrayList<>();
        for(Service service : listOfServices){
            if(service.getName().equals(serviceName)){
                servicesEquals.add(service);
            }
        }

        Random random = new Random();
        int numRandom = random.nextInt();

        return servicesEquals.get(numRandom);
    }

}
