package com.arquitectura.brokerappserver.model;

import java.io.ByteArrayInputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class ServiceRegister extends Service {


    public ServiceRegister(){
        setName("registrar");
        setParameters(2);

    }

    @Override
    public String executeService(String request){
         JsonObject jsonObject = Json.createReader(new ByteArrayInputStream(request.getBytes())).readObject();
        
        String action = jsonObject.getString("valor1");
        String date = jsonObject.getString("valor2");
         Bitacora.registerAction(action, date);
         
          JsonObjectBuilder answerBuilder = Json.createObjectBuilder();
          answerBuilder.add("servicio","registrar")
                          .add("respuestas",1)
                            .add("respuesta1", "eventos")
                                .add("valor1", Bitacora.getNumberofEvents() );            
        JsonObject requestJSONObject = answerBuilder.build();
            return requestJSONObject.toString();
    }


}
