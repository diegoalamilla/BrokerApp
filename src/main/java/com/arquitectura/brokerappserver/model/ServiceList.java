package com.arquitectura.brokerappserver.model;



import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class ServiceList extends Service{
    public ServiceList(){
        setName("listar");
        setParameters(0);
    }
      @Override
    public String executeService(String request){
        
        JsonObjectBuilder answerBuilder = Json.createObjectBuilder();
        answerBuilder.add("servicio","listar")
                        .add("respuestas",Bitacora.getNumberofEvents());
                        for(int i = 0; i < Bitacora.getNumberofEvents(); i++){
                          answerBuilder.add("respuesta"+(i+1), "evento")
                                .add("valor"+(i+1), Bitacora.getEvents().get(i));
                        }
        JsonObject requestJSONObject = answerBuilder.build();
        return requestJSONObject.toString();
    }
}
