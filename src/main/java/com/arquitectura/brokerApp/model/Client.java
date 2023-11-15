package com.arquitectura.brokerApp.model;

import javax.json.JsonObject;
import java.io.*;
import java.net.*;

public class Client {
    public static String conexion(JsonObject request) throws IOException{
        String servidorDireccion = "localhost"; 
        int puerto = 25565; 

    try (Socket socket = new Socket(servidorDireccion, puerto);
         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         ) {

        String solicitud = request.toString();

        out.println(solicitud); 

        String respuesta = in.readLine(); 
        System.out.println( respuesta);
        return respuesta;
    } catch (IOException e) {
        e.printStackTrace();
    }
    return "";
    }   
}

