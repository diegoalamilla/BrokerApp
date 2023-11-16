package com.arquitectura.brokerApp.main;

import com.arquitectura.brokerApp.view.*;
import com.arquitectura.brokerApp.controller.*;

public class clientMain {
    public static void main(String[] args) {
        ViewClient view = new ViewClient();
        ControllerViewClient controller = new ControllerViewClient(view);
    }
}
