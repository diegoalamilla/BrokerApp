package com.arquitectura.brokerappbroker.model;



public class Service {
    private String name;
    private String serverIP;
    private int port;
    private int parametersQuantiy;

  
      public String getName() {
         return name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public String getServerIP() {
         return serverIP;
      }

      public void setServerIP(String serverIP) {
         this.serverIP = serverIP;
      }

      public int getPort() {
         return port;
      }

      public void setPort(int port) {
         this.port = port;
      }

      public int getParametersQuantiy() {
         return parametersQuantiy;
      }

      public void setParametersQuantiy(int parametersQuantiy) {
         this.parametersQuantiy = parametersQuantiy;
      }


    
}
