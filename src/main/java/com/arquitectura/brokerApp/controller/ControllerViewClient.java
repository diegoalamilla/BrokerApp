package com.arquitectura.brokerApp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.arquitectura.brokerApp.model.Client;
import com.arquitectura.brokerApp.view.*;

public class ControllerViewClient implements ActionListener {
    private ViewClient view;

    public ControllerViewClient(ViewClient view) {
        this.view = view;

        initializeView();

        this.view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(this.view.getButtonEnlist() == e.getSource()){
            this.view.getButtonExecuteService().setVisible(true);

            sendRequestList();
        }else if(this.view.getButtonExecuteService() == e.getSource()){
            JsonObjectBuilder requestBuilder = Json.createObjectBuilder();
                requestBuilder.add("servicio", "contar")
                                .add("variables", "0");
            JsonObject requestJSONObject = requestBuilder.build();
            try{
                Client.conexion(requestJSONObject);

            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }

    private void initializeView(){
        this.view.getButtonEnlist().addActionListener(this);
        this.view.getButtonExecuteService().addActionListener(this);
        this.view.getButtonExecuteService().hide();
        this.view.getTableServices().setCellSelectionEnabled(true);
    }

    private void sendRequestList(){
        JsonObjectBuilder requestBuilder = Json.createObjectBuilder();
            requestBuilder.add("servicio", "listar")
                            .add("variables", "1")
                                .add("variable1", "palabra")
                                    .add("valor1", this.view.getFieldService().getText());
 

        JsonObject requestJSONObject = requestBuilder.build();
        
        try {
            Client.conexion(requestJSONObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
