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
    
    public Bitacora() {
        
    }

    public static void registerAction(String action){
        Date date = new Date();
         try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/resourceserver/bitacora.txt", true))) {
                writer.write(action + " " + date.toString());
                writer.newLine();
                } catch (IOException e) {
                e.printStackTrace();
                }
    }

    public static ArrayList<String> getEvents(){
        ArrayList<String> events = new ArrayList<>();
         try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/resourcesserver/bitacora.txt"))) {
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
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/resourcesserver/bitacora.txt"))) {
            while (reader.readLine() != null) numberOfEvents++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return numberOfEvents;
    }
    
    public static void cleanRegister(){
         File file = new File("src/main/resources/resourceserver/bitacora.txt");
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
