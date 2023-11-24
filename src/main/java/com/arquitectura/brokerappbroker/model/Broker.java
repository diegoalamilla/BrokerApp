package com.arquitectura.brokerappbroker.model;


import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.SplittableRandom;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;


public class Broker {
    static ArrayList<Service> listOfServices = new ArrayList<>();
    private static String tempIp = "";
    private static int tempPort = 0;

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

        
        return processResponseToClient(jsonObject);

      }

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
                        .add("valor"+(i+1),listOfServices.get(i).getServerIP()+":"+String.valueOf(listOfServices.get(i).getPort()));
        }
        JsonObject requestJSONObject = answerBuilder.build();
        return requestJSONObject.toString();
        }else{
            Service serviceNeeded = getService(service);
            
            answerBuilder.add("servicio","listar")
                        .add("respuestas",1)
                        .add("respuesta1",serviceNeeded.getName())
                        .add("valor1",serviceNeeded.getServerIP()+":"+String.valueOf(serviceNeeded.getPort()));
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
                                    .add("valor1", request.getInt("valor2"));
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

        if(service.getName().equals("contar")){
            setTempIp(service.getServerIP());
            setTempPort(service.getPort());
        }

        JsonObject requestToServer = requestBuilder.build();

        try {
            if(service.getName().equals("votar") || service.getName().equals("registrar")){
                System.out.println("El servicio votar se ejecutará en el servidor: " + tempIp + ":" + tempPort);
                return Client.conexion(requestToServer, tempIp, tempPort);
            }

            return Client.conexion(requestToServer , service.getServerIP(), service.getPort());
        } catch (Exception e) {
            
        }
        return "";
        
    }

    private static void setTempIp(String ip){
        tempIp = ip;
    }

    private static void setTempPort(int port){
        tempPort = port;
    }

    private static String processResponseToClient(JsonObject request){
        String responseOfServer = processExecute(request);
        JsonObject jsonObject = Json.createReader(new ByteArrayInputStream(responseOfServer.getBytes())).readObject();
        JsonObjectBuilder answerBuilder = Json.createObjectBuilder();
        answerBuilder.add("servicio","ejecutar")
                        .add("respuestas",jsonObject.getInt("respuestas")+1)
                            .add("respuesta1","servicio")
                                .add("valor1",jsonObject.getString("servicio"));
        if(jsonObject.getString("servicio").equals("listar")){
            for (int i = 0; i < jsonObject.getInt("respuestas"); i++) {
                answerBuilder.add("respuesta"+(i+2),jsonObject.getString("respuesta"+(i+1)))
                                .add("valor"+(i+2),jsonObject.getString("valor"+(i+1)));
        
            }
        }else{
            for (int i = 0; i < jsonObject.getInt("respuestas"); i++) {
            answerBuilder.add("respuesta"+(i+2),jsonObject.getString("respuesta"+(i+1)))
                            .add("valor"+(i+2),jsonObject.getInt("valor"+(i+1)));
    
        }
        }
        
        
        JsonObject answerJSONObject = answerBuilder.build();
        return answerJSONObject.toString();

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
        if(servicesEquals.size() == 1){
            return servicesEquals.get(0);
        }else{
         int n = new SplittableRandom().nextInt(0, servicesEquals.size());
         return servicesEquals.get(n);
        }
    }

}
