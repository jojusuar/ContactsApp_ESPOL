/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.proyecto1_estructuras;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import persistence.User;

/**
 * FXML Controller class
 *
 * @author euclasio
 */
public class ContactsController implements Initializable {
    private static User currentUser = null;
    
    @FXML
    private ScrollPane scroller;
    
    @FXML
    private VBox grid;
    
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }
    @FXML
    private void switchToSearch() throws IOException {
        App.setRoot("search");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Usuario actual: "+ currentUser);
    } 
    
    public static void setCurrentUser(User currentUser) {
        ContactsController.currentUser = currentUser;
    }
    
}
