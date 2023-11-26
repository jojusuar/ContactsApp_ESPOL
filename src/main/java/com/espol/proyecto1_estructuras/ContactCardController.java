/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.proyecto1_estructuras;

import baseClasses.Contact;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author euclasio
 */
public class ContactCardController implements Initializable {
    
    private static Contact currentContact;
    
    @FXML
    private void switchToContacts() throws IOException {
        App.setRoot("contacts");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public static void setCurrentContact(Contact c){
        currentContact = c;
    }
    
    
}
