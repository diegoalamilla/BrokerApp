# BrokerApp

Para generar los JAR utiliza los siguientes comandos en la terminal

~mvn package -Pbroker
~mvn package -Pserver
~mvn package -Pclient

Para ejecutar cada uno:

~java -jar target/broker-app.jar
~java -jar target/server-app.jar
~java -jar target/client-app.jar

Ejecutándolos en orden respectivamente
