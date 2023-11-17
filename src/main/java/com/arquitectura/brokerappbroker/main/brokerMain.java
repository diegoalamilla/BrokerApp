package com.arquitectura.brokerappbroker.main;

import java.io.*;
import java.net.*;

import com.arquitectura.brokerappbroker.model.Broker;


public class brokerMain {

    public static void main(String[] args) {
        //Puerto del broker
        int puerto = 25565;
        
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Broker esperando conexiones en el puerto " + puerto + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado.");

                // Crea un hilo para manejar la conexión del cliente
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                
                String solicitud;
                while ((solicitud = in.readLine()) != null) {
                    System.out.println("Solicitud recibida del cliente: " + solicitud);
                    // Procesa la solicitud y envía la respuesta
                    String respuestaDelServer = Broker.processRequest(solicitud);
                    String respuesta = respuestaDelServer ;
                    
                    out.println(respuesta);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                    System.out.println("Cliente desconectado.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

