package com.arquitectura.brokerappbroker.model;
import javax.json.JsonObject;
import java.io.*;
import java.net.*;

public class Client {

    public static String conexion(JsonObject request, String ip, int port) throws IOException{
        String servidorDireccion = ip; 
        int puerto = port; 

    try (Socket socket = new Socket(servidorDireccion, puerto);
         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         ) {

        String solicitud = request.toString();

        out.println(solicitud); 

        String respuesta = in.readLine(); 
       
        System.out.println("Respuesta del servidor: " + respuesta);
        return respuesta;
    } catch (IOException e) {
        e.printStackTrace();
    }
    return "";
    }   
}

