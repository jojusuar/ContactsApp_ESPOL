/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.proyecto1_estructuras;

import baseClasses.Company;
import baseClasses.Contact;
import baseClasses.Person;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import persistence.Memory;
import persistence.User;
import tdas.CircularLinkedList;
import tdas.DoubleLinkNode;

/**
 * FXML Controller class
 *
 * @author euclasio
 */
public class ContactsController implements Initializable {

    private static User currentUser = null;
    private static CircularLinkedList<Contact> contacts = null;
    private static DoubleLinkNode<Contact> cursor = null;

    @FXML
    private ScrollPane scroller;

    @FXML
    private VBox grid;

    @FXML
    private Button newContactButton;

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void switchToSearch() throws IOException {
        App.setRoot("search");
    }

    private void switchToContactCard() throws IOException {
        App.setRoot("contactCard");
    }

    @FXML
    private Button upButton;

    @FXML
    private Button downButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contacts = currentUser.getData().getContacts();
        cursor = contacts.getReference();
        showContacts();
        grid.setOnScroll((ScrollEvent event) -> {
            double deltaY = event.getDeltaY();
            if (deltaY > 0) {
                moveUp();
            } else if (deltaY < 0) {
                moveDown();
            }
        });
    }

    @FXML
    private void newContact() {
        VBox fields = new VBox(10);
        ToggleGroup group = new ToggleGroup();
        RadioButton button1 = new RadioButton("Persona");
        button1.setToggleGroup(group);
        RadioButton button2 = new RadioButton("Empresa");
        button2.setToggleGroup(group);
        HBox options = new HBox(10);
        VBox input = new VBox(10);
        options.getChildren().addAll(button1, button2);
        fields.getChildren().addAll(options, input);
        Scene contactScene = new Scene(fields, 450, 450);
        Stage contactStage = new Stage();
        contactStage.setScene(contactScene);
        contactStage.show();
        button1.setOnAction(ev -> {
            newPerson(input, contactStage);
        });
        button2.setOnAction(ev -> {
            newCompany(input, contactStage);
        });
    }

    @FXML
    private void newPerson(VBox input, Stage contactStage) {
        input.getChildren().clear();
        TextField firstName = new TextField("Primer nombre");
        TextField middleName = new TextField("Segundo nombre");
        TextField lastName = new TextField("Apellido");
        TextField context = new TextField("Descripción");
        input.getChildren().addAll(firstName, middleName, lastName, context);
        CircularLinkedList<String> imgPaths = vistasUtilitary.galleryWizard(input, contactStage);
        Button save = new Button("Crear contacto");
        input.getChildren().add(save);
        save.setOnAction(ev -> {
            Contact contact = new Person(context.getText(), imgPaths.get(0), firstName.getText(), middleName.getText(),
                    lastName.getText(), imgPaths);
            saveContact(contact);
            contactStage.close();
        });
    }

    @FXML
    private void newCompany(VBox input, Stage contactStage) {
        input.getChildren().clear();
        TextField name = new TextField("Empresa");
        TextField context = new TextField("Descripción");
        input.getChildren().addAll(name, context);
        CircularLinkedList<String> imgPaths = vistasUtilitary.galleryWizard(input, contactStage);
        Button save = new Button("Crear contacto");
        input.getChildren().add(save);
        save.setOnAction(ev -> {
            Contact contact = new Company(context.getText(), imgPaths.get(0), name.getText(), imgPaths);
            saveContact(contact);
            contactStage.close();
        });
    }

    

    private void saveContact(Contact c) {
        boolean empty = false;
        if (contacts.isEmpty()) {
            empty = true;
        }
        currentUser.getData().add(c);
        if (empty) {
            cursor = contacts.getReference();
        }
        Memory.save();
        System.out.println(currentUser.getData().getContacts());
        showContacts();
    }

    public static void setCurrentUser(User currentUser) {
        ContactsController.currentUser = currentUser;
    }

    private void showContacts() {
        grid.getChildren().clear();
        int contactsSize = contacts.size();
        if (contactsSize <= 9) {
            createContactSlots(contactsSize);
        } else {
            createContactSlots(10);
            for (int i = 0; i < contactsSize - 10; i++) {
                cursor = cursor.getNext();
            }
        }
    }

    private void createContactSlots(int k) {
        for (int i = 0; i < k; i++) {
            Contact contact = cursor.getContent();
            HBox card = new HBox(20);
            Label name = new Label(contact.toString());
            ImageView pfp = vistasUtilitary.loadImage(contact.getPfp());
            vistasUtilitary.cropIntoCircle(pfp, 18);
            card.getChildren().addAll(pfp, name);
            card.setOnMouseClicked(ev -> {
                ContactCardController.setCurrentContact(contact);
                try {
                    switchToContactCard();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            grid.getChildren().add(card);
            cursor = cursor.getNext();
        }
    }

    @FXML
    private void moveUp() {
        if (cursor != null) {
            cursor = cursor.getPrevious();
            showContacts();
        }

    }

    @FXML
    private void moveDown() {
        if (cursor != null) {
            cursor = cursor.getNext();
            showContacts();
        }
    }
}
