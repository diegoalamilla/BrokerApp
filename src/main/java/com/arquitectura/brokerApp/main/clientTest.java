package com.arquitectura.brokerApp.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class clientTest {

    public static void main(String[] args) {
        String servidorDireccion = "192.168.0.149"; // Dirección IP o nombre del servidor
        int puerto = 12345; // Puerto en el que el servidor está escuchando

        try (Socket socket = new Socket(servidorDireccion, puerto);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             ) {

            String solicitud = "servicio";

            out.println(solicitud); // Envia la solicitud al servidor

            String respuesta = in.readLine(); // Lee la respuesta del servidor
            System.out.println("Respuesta del servidor: " + respuesta);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}