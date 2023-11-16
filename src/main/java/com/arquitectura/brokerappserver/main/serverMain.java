package com.arquitectura.brokerappserver.main;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.arquitectura.brokerappserver.model.*;

public class serverMain {
    public static void main(String[] args) {
        //Por el momento ya está el caparazón de los servicios, y de los servicios votar y contar
        // Ya está hecha un poco de su lógica. 
        DAOProductos.readProductsFile();
        //Dirrecion y puerto que utilizarán los servicios
        String SERVER_IP = "localhost";
        int PORT = 25566;
        //mandar servicio a broker
        Service service = new ServiceCount();
        service.registerService(SERVER_IP, PORT);
        Service service2 = new ServiceList();
        service2.registerService(SERVER_IP, PORT);
        Service service3 = new ServiceRegister();
        service3.registerService(SERVER_IP, PORT);
        Service service4 = new ServiceVote();
        service4.registerService(SERVER_IP, PORT);

         try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor esperando conexiones en el puerto " + PORT + "...");
            // Crea un hilo para manejar la conexión del cliente
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
                    System.out.println("Solicitud recibida del broker: " + solicitud);
                    // Procesa la solicitud y envía la respuesta
                    String respuesta = Service.processRequest(solicitud);
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




        //Es necesario leer los productos antes de hacer algo
      

        //Se inicializa el servicio contar
        ServiceCount serviceCount = new ServiceCount();
        
        //El método registerService solo funciona con el broker abierto
        //Este método es heredado de la clase Service para todos los servicios
        //serviceCount.registerService(serverIP, PORT);
        
        

        
        
    
}


