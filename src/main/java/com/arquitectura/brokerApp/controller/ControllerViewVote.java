package com.arquitectura.brokerApp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.arquitectura.brokerApp.view.ViewVote;
import com.arquitectura.brokerApp.model.Client;

public class ControllerViewVote implements ActionListener{
    private ViewVote view;
    
    public ControllerViewVote(ViewVote viewVote, JsonObject request){
        this.view = viewVote;

        this.view.getButtonVote().addActionListener(this);
        this.view.getButtonBack().addActionListener(this);

        ArrayList<String> products = getProductsList(request);
        

        this.view.setVisible(true);

        this.view.getComboProducts().removeAllItems();
        this.view.getComboProducts().addItem(products.get(0));
        this.view.getComboProducts().addItem(products.get(1));
        this.view.getComboProducts().addItem(products.get(2));
        this.view.getFieldNumberOfVotes().setText("1");
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(this.view.getButtonVote() == e.getSource() && this.view.getFieldNumberOfVotes().getText() != ""){
            JsonObject request = getVoteRequest();
            try {
                String response = Client.conexion(request);
                getRegisterRequest();
                JsonObject jsonObject = Json.createReader(new ByteArrayInputStream(response.getBytes())).readObject();
                int numberOfVotes = jsonObject.getInt("valor2");
                this.view.getLabelActualVotes().setText(String.valueOf("Votos actuales: " + numberOfVotes));

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if(this.view.getButtonBack() == e.getSource()){
            this.view.dispose();
        }
    }

    private JsonObject getVoteRequest(){
        JsonObjectBuilder requestVoteBuilder = Json.createObjectBuilder();
        requestVoteBuilder.add("servicio","ejecutar")
                            .add("variables","2")
                            .add("variable1","servicio")
                            .add("valor1","votar")
                            .add("variable2",this.view.getComboProducts().getSelectedItem().toString())
                            .add("valor2",Integer.parseInt(this.view.getFieldNumberOfVotes().getText()));
        return requestVoteBuilder.build();        
    }

    private void getRegisterRequest(){
        JsonObjectBuilder requestVoteBuilder = Json.createObjectBuilder();
        Date date = new Date();
        requestVoteBuilder.add("servicio","ejecutar")
                            .add("variables","3")
                            .add("variable1","servicio")
                            .add("valor1","registrar")
                            .add("variable2","evento")
                            .add("valor2","Se ha registrado "+ this.view.getFieldNumberOfVotes().getText() +" voto(s) para el producto: "+this.view.getComboProducts().getSelectedItem().toString())
                            .add("variable3", "fecha")
                            .add("valor3", date.toString() );
        try {
            Client.conexion(requestVoteBuilder.build());
        } catch (Exception e) {
        }
    }

    private ArrayList<String> getProductsList(JsonObject request){
         try {
                String response = Client.conexion(request);
                JsonObject jsonObject = Json.createReader(new ByteArrayInputStream(response.getBytes())).readObject();
                ArrayList<String> products = new ArrayList<String>();
                for (int i = 2; i < jsonObject.getInt("respuestas")+1; i++) {
                    products.add(jsonObject.getString("respuesta"+(i)));
                }
                return products;
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

}
