package com.arquitectura.brokerappserver.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;


public class ServiceVote extends Service{

    public ServiceVote(){
        setName("votar");
        setParameters(1);
    }

    @Override
    public String executeService(String request){
        byte[] bytes = request.getBytes();
        InputStream is = new ByteArrayInputStream(bytes);
        JsonReader jsonReader = Json.createReader(is);
		JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        String productToVote = jsonObject.getString("variable1");
        int numberOfVotes = jsonObject.getInt("valor1");

        
        for(int i=0;i<numberOfVotes;i++){
            DAOProductos.addVoteToProduct(productToVote);
        }
        JsonObjectBuilder answerBuilder = Json.createObjectBuilder();
        answerBuilder.add("servicio","votar")
                        .add("respuestas",1)
                           .add("respuesta1",productToVote)
                                .add("valor1",DAOProductos.getNumberofVotesFromProduct(productToVote));
        JsonObject requestJSONObject = answerBuilder.build();
        return requestJSONObject.toString();
        
    }
}
