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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import persistence.Memory;
import persistence.User;

/**
 * FXML Controller class
 *
 * @author euclasio
 */
public class LoginController implements Initializable {
    @FXML
    private Button loginButton;
    
    @FXML
    private Button signupButton;
    

    
    @FXML
    private void login() throws IOException {
        Memory.load();
        VBox fields = new VBox(40);
        TextField username = new TextField("Usuario");
        TextField password = new TextField("Contraseña");
        Label advice = new Label("");
        Button enter = new Button("Ingresar");
        Button cancel = new Button("Cancelar");
        fields.getChildren().addAll(username, password, advice, enter, cancel);
        Scene loginScene = new Scene(fields, 450, 450);
        Stage loginStage = new Stage();
        loginStage.setScene(loginScene);
        loginStage.show();
        enter.setOnAction(ev -> {
            boolean found = false;
            for(User user: Memory.getUsers()){
                if(username.getText().equals(user.getUsername())){
                    found = true;
                    if(password.getText().equals(user.getPassword())){
                        ContactsController.setCurrentUser(user);
                        loginStage.close();
                        try{
                            switchToContacts();
                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                    else{
                        advice.setText("contraseña incorrecta!");
                    }
                }
            }
            if(!found){
                advice.setText("Usuario no encontrado!");
            }
        });
        cancel.setOnAction(ev -> {
            loginStage.close();
        });
    }
    
    @FXML
    private void signup(){
        VBox fields = new VBox(10);
        TextField username = new TextField("Ingrese un nombre de usuario");
        TextField password = new TextField("Ingrese una contraseña");
        Button save = new Button("Guardar usuario");
        fields.getChildren().addAll(username, password, save);
        Scene signupScene = new Scene(fields, 450, 450);
        Stage signupStage = new Stage();
        signupStage.setScene(signupScene);
        signupStage.show();
        save.setOnAction(ev -> {
            Memory.load();
            User user = new User(username.getText(), password.getText());
            Memory.addUser(user);
            Memory.save();
            signupStage.close();
        });
    }
    
    @FXML
    private void switchToContacts() throws IOException {
        App.setRoot("contacts");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
