/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.arquitectura.brokerApp.controller;

import com.arquitectura.brokerApp.model.Client;
import com.arquitectura.brokerApp.view.ViewLog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Daniel Garcia
 */
public class ControllerViewLog implements ActionListener{
    private ViewLog view;

    public ControllerViewLog(ViewLog view) {
        
            this.view = view;
            this.view.getjButtonBack().addActionListener(this);
            this.view.setVisible(true);
            this.updateTable();
    }
  
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.view.getjButtonBack() == e.getSource()){ 
            this.view.dispose();
        }
    }
    
    private void updateTable(){
        DefaultTableModel tableLog = (DefaultTableModel) this.view.getjTableLog().getModel();
        JsonArray logList = this.getLogList();
        
        // Limpieza de datos
        tableLog.setRowCount(0);
        
       if (logList != null) {
            for (JsonValue value : logList) {
                String logValue = value.toString(); 
                tableLog.addRow(new Object[]{logValue});
            }
        }
    }

    private JsonObject getRequest(){
        JsonObjectBuilder requestVoteBuilder = Json.createObjectBuilder();
        requestVoteBuilder.add("servicio","ejecutar")
                            .add("variables","1")
                            .add("variable1","servicio")
                            .add("valor1","listar");
        return requestVoteBuilder.build();        
    }
    
    private JsonArray getLogList(){
        try { 
            JsonObject request = getRequest();
            String response = Client.conexion(request);
            JsonObject jsonObject = Json.createReader(new ByteArrayInputStream(response.getBytes())).readObject();
            
            JsonArrayBuilder eventArrayBuilder = Json.createArrayBuilder();

            for (int i = 1; i <= jsonObject.getInt("respuestas"); i++) {
                String respuestaKey = "respuesta" + i;
                String valorKey = "valor" + i;

                if (jsonObject.containsKey(respuestaKey) && jsonObject.getString(respuestaKey).equals("evento")
                        && jsonObject.containsKey(valorKey)) {
                    eventArrayBuilder.add(jsonObject.getString(valorKey));
                }
            }

            return eventArrayBuilder.build();
        } catch (IOException ex) {
            Logger.getLogger(ControllerViewLog.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return null;
    }
    
}
