package com.arquitectura.brokerApp.main;

import com.arquitectura.brokerApp.view.*;
import com.arquitectura.brokerApp.controller.*;

public class mainClientTest {
    public static void main(String[] args) {
        //Las clases que tengan que ver con employee son pruebas para el funcionamiento de los json,
        //pero recomiendo que vean mejor el funcionamiento de la manipulacion de json desde la parte de Server (No broker)
        //Lo único que hace la parte cliente es mandar la solicitud al broker de enlistar los servicios
        ViewClient view = new ViewClient();
        ControllerViewClient controller = new ControllerViewClient(view);
    }
}
