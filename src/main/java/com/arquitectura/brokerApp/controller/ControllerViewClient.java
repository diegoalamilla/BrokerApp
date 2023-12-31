package com.arquitectura.brokerApp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.arquitectura.brokerApp.model.Client;
import com.arquitectura.brokerApp.view.*;

public class ControllerViewClient implements ActionListener {
    private ViewClient view;
    private String respuestaDelBroker;
    private DefaultTableModel model;

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
            executeSelectedService();
        }
    }

    private void executeSelectedService(){
        if(this.model.getValueAt(this.view.getTableServices().getSelectedRow(), 0).equals("votar")){
        
            ViewVote viewVote = new ViewVote();
            ControllerViewVote controllerViewVote = new ControllerViewVote(viewVote, getCountRequest());
        }
        if(this.model.getValueAt(this.view.getTableServices().getSelectedRow(), 0).equals("listar")){
            ViewLog viewLog = new ViewLog();
            ControllerViewLog controllerViewLog = new ControllerViewLog(viewLog);
        }
        if(this.model.getValueAt(this.view.getTableServices().getSelectedRow(), 0).equals("contar")){

            GraficaBarrasVista graficaBarrasVista = new GraficaBarrasVista();
            GraficaPastelVista graficaPastelVista = new GraficaPastelVista();
            countService(graficaBarrasVista, graficaPastelVista);

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
        this.model = (DefaultTableModel) table.getModel();

        this.model.setRowCount(0);
        for(int i = 0; i < numberOfResponses; i++){
            
            String [] row = {jsonObject.getString("respuesta"+(i+1)), jsonObject.getString("valor"+(i+1))};
            this.model.addRow(row);
           
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

    private void countService(GraficaBarrasVista graficaBarrasVista, GraficaPastelVista graficaPastelVista){
            JsonObject request = getCountRequest();
            try {
                String response = Client.conexion(request);
                JsonObject jsonObject = Json.createReader(new ByteArrayInputStream(response.getBytes())).readObject();
                ArrayList<String> products = new ArrayList<String>();
                ArrayList<Integer> numberOfVotes = new ArrayList<Integer>();
                for (int i = 2; i < jsonObject.getInt("respuestas")+1; i++) {
                    products.add(jsonObject.getString("respuesta"+(i)));
                    numberOfVotes.add(jsonObject.getInt("valor"+(i)));
                }
                graficaBarrasVista.updateChart(products, numberOfVotes);
                graficaPastelVista.updateChart(products, numberOfVotes);

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    

    private JsonObject getCountRequest(){
        JsonObjectBuilder requestVoteBuilder = Json.createObjectBuilder();
        requestVoteBuilder.add("servicio","ejecutar")
                            .add("variables",1)
                            .add("variable1","servicio")
                            .add("valor1","contar");
        return requestVoteBuilder.build();        
    }

    
}
