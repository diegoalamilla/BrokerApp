package com.arquitectura.brokerappserver.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class Bitacora {
    final static String BITACORA_PATH = "src/main/resources/resourceserver/bitacora.txt";
    public Bitacora() {
        
    }

    public static void registerAction(String action){
        Date date = new Date();
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(BITACORA_PATH, true))) {
                writer.write(action + " " + date.toString());
                writer.newLine();
                } catch (IOException e) {
                e.printStackTrace();
                }
    }

    public static ArrayList<String> getEvents(){
        ArrayList<String> events = new ArrayList<>();
         try (BufferedReader reader = new BufferedReader(new FileReader(BITACORA_PATH))) {
            String line;
            while ((line = reader.readLine()) != null)
            events.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }

    public static int getNumberofEvents(){
        int numberOfEvents = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(BITACORA_PATH))) {
            while (reader.readLine() != null) numberOfEvents++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return numberOfEvents;
    }
    
    public static void cleanRegister(){
         File file = new File(BITACORA_PATH);
            if(file.exists()){
                file.delete();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }
}
