package com.arquitectura.brokerApp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.arquitectura.brokerApp.view.ViewVote;
import com.arquitectura.brokerApp.model.Client;

public class ControllerViewVote implements ActionListener{
    private ViewVote view;

    public ControllerViewVote(ViewVote viewVote){
        this.view = viewVote;

        this.view.getButtonVote().addActionListener(this);

        this.view.setVisible(true);

        this.view.getFieldProduct().setText("Mordisko");
        this.view.getFieldNumberOfVotes().setText("1");
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(this.view.getButtonVote() == e.getSource()){
            JsonObject request = getRequest();
            try {
                String response = Client.conexion(request);
                JsonObject jsonObject = Json.createReader(new ByteArrayInputStream(response.getBytes())).readObject();
                int numberOfVotes = jsonObject.getInt("valor2");
                this.view.getLabelActualVotes().setText(String.valueOf(numberOfVotes));

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }
    }

    private JsonObject getRequest(){
        JsonObjectBuilder requestVoteBuilder = Json.createObjectBuilder();
        requestVoteBuilder.add("servicio","ejecutar")
                            .add("variables","2")
                            .add("variable1","servicio")
                            .add("valor1","votar")
                            .add("variable2",this.view.getFieldProduct().getText())
                            .add("valor2",Integer.parseInt(this.view.getFieldNumberOfVotes().getText()));
        return requestVoteBuilder.build();        
    }
}
