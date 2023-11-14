package com.arquitectura.brokerappserver.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class DAOProductos {
    private static ArrayList<Producto> productos = new ArrayList<Producto>();
    
    public static void readProductsFile(){
        
        String[] dataFromLine;
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/resourceserver/productos.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dataFromLine = line.split(",");
                
                Producto producto = new Producto();
                producto.setNombre(dataFromLine[0]);
                producto.setDescripcion(dataFromLine[1]);
                producto.setImagen(dataFromLine[2]);

                productos.add(producto);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayList<Producto> getProductsList(){
        return productos;
    }


    public static void addVoteToProduct(String productName){
        Date date = new Date();
        
        for(Producto producto : productos){
            if(producto.getNombre().equals(productName)){
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/resourceserver/" + producto.getNombre() + ".txt", true))) {
                writer.write("Se registró un nuevo voto en la fecha: " + date.toString());
                writer.newLine();
                } catch (IOException e) {
                e.printStackTrace();
                }
            }
        }
        
    }
    
    public static int getNumberofVotesFromProduct(String productName){
        int numberofVotes=0;
        for(Producto producto : productos){
            if(producto.getNombre().equals(productName)){
                try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/resourceserver/" + producto.getNombre() + ".txt"))) {
                while ((reader.readLine()) != null) {
                numberofVotes++;
                }
                } catch (IOException e) {
                e.printStackTrace();
                }
            }
        }
        return numberofVotes;
    }
    
    public static void clearVotes() throws IOException{
        for(Producto producto : productos){
            File file = new File("src/main/resources/resourceserver/"+producto.getNombre()+".txt");
            if(file.exists()){
                file.delete();
                file.createNewFile();
            }
        }
    }

}
