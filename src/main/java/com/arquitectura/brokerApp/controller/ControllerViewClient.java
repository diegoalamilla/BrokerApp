package com.arquitectura.brokerApp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.arquitectura.brokerApp.model.Client;
import com.arquitectura.brokerApp.view.*;

public class ControllerViewClient implements ActionListener {
    private ViewClient view;
    private String respuestaDelBroker;
    public String getRespuestaDelBroker() {
        return respuestaDelBroker;
    }

    public void setRespuestaDelBroker(String respuestaDelBroker) {
        this.respuestaDelBroker = respuestaDelBroker;
    }

    public ControllerViewClient(ViewClient view) {
        this.view = view;

        initializeView();

        this.view.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(this.view.getButtonEnlist() == e.getSource()){
            this.view.getButtonExecuteService().setVisible(true);
            
            processResponse(sendRequestList());
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

    
    private void processResponse(String response){
        try {
        System.out.println(response);
        JsonObject jsonObject = Json.createReader(new ByteArrayInputStream(response.getBytes())).readObject();
        int numberOfResponses = jsonObject.getInt("respuestas");
        JTable table = this.view.getTableServices();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        //en este for poner las respuestas que se reciben
        //creo que se necesita aumentar el numero de columnas en la tabla
        model.setRowCount(0);
        for(int i = 0; i < numberOfResponses; i++){
            
            String [] row = {jsonObject.getString("respuesta"+(i+1)), jsonObject.getString("valor"+(i+1))};
            model.addRow(row);
           
        }
        } catch (Exception e) {
           e.printStackTrace();
        }

    }

    private String sendRequestList(){
        JsonObjectBuilder requestBuilder = Json.createObjectBuilder();
            requestBuilder.add("servicio", "listar")
                            .add("variables", 1)
                                .add("variable1", "palabra")
                                    .add("valor1", this.view.getFieldService().getText());
 

        JsonObject requestJSONObject = requestBuilder.build();
        
        try {
        return Client.conexion(requestJSONObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void updateTable(DefaultTableModel model){
        model.setRowCount(0);
    }
}
