package com.arquitectura.brokerappserver.model;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class ServiceCount extends Service{

    public ServiceCount(){
        setName("contar");
        setParameters(0);
    }

    @Override
    public String executeService(String request){
        ArrayList<Producto> listProducts = DAOProductos.getProductsList();

        JsonObjectBuilder answerBuilder = Json.createObjectBuilder();
        answerBuilder.add("servicio","contar")
                        .add("respuestas",3)
                            .add("respuesta1",listProducts.get(0).getNombre())
                                .add("valor1",String.valueOf(DAOProductos.getNumberofVotesFromProduct(listProducts.get(0).getNombre())))
                                    .add("respuesta2",listProducts.get(1).getNombre())
                                        .add("valor2",String.valueOf(DAOProductos.getNumberofVotesFromProduct(listProducts.get(1).getNombre())))
                                            .add("respuesta3",listProducts.get(2).getNombre())
                                                .add("valor3",String.valueOf(DAOProductos.getNumberofVotesFromProduct(listProducts.get(2).getNombre())));
        JsonObject requestJSONObject = answerBuilder.build();
        return requestJSONObject.toString();
    }
}
