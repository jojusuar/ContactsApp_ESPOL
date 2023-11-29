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
import javafx.scene.layout.AnchorPane;
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
    private static CircularLinkedList<Contact> showingContacts = null;
    private static DoubleLinkNode<Contact> cursor = null;

    @FXML
    private ScrollPane scroller;

    @FXML
    private VBox grid;

    @FXML
    private Button newContactButton;
    
    @FXML
    private Button buscarButton;
    
    @FXML
    private TextField searchBar;

    @FXML
    private AnchorPane anchorPane;

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
        showingContacts = currentUser.getData().getContacts();
        cursor = showingContacts.getReference();
        showContacts();
        grid.setOnScroll((ScrollEvent event) -> {
            double deltaY = event.getDeltaY();
            if (deltaY > 0) {
                moveUp();
            } else if (deltaY < 0) {
                moveDown();
            }
        });
        
        //busqueda sin filtros
        buscarButton.setOnAction(ev -> {
             CircularLinkedList<Contact> tempContactList = new CircularLinkedList<Contact>();
             showingContacts = contacts;
             cursor = showingContacts.getReference();
             String s = searchBar.getText().toLowerCase();
             if(s.isBlank() || s.isEmpty() || s == null){
                 showingContacts = contacts;
                 cursor = showingContacts.getReference();
                 showContacts();
             }else{
                 for (int i = 0; i < showingContacts.size(); i++) {
                     Contact contact = cursor.getContent();
                     if(contact instanceof Person){
                         Person p = (Person) contact;
                         if(p.getFirstName().toLowerCase().contains(s) || p.getLastName().toLowerCase().contains(s) || p.getMiddleName().toLowerCase().contains(s)){
                             tempContactList.addLast(contact);
                         }
                         
                     }else if(contact instanceof Company){
                         Company c = (Company) contact;
                         if(c.getName().toLowerCase().contains(s)){
                             tempContactList.addLast(contact);
                         }
                         
                     }
                     cursor = cursor.getNext();
                 }
                 showingContacts = tempContactList;
                 cursor = showingContacts.getReference();
                 
                 showContacts();
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
        CircularLinkedList<String> imgPaths = galleryWizard(input, contactStage);
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
        CircularLinkedList<String> imgPaths = galleryWizard(input, contactStage);
        Button save = new Button("Crear contacto");
        input.getChildren().add(save);
        save.setOnAction(ev -> {
            Contact contact = new Company(context.getText(), imgPaths.get(0), name.getText(), imgPaths);
            saveContact(contact);
            contactStage.close();
        });
    }

    private CircularLinkedList<String> galleryWizard(VBox input, Stage contactStage) {
        Button loadPfp = new Button("Cargar foto de contacto");
        Label pfpInfo = new Label("foto de perfil no seleccionada");
        Button loadPhoto = new Button("Cargar imagen a la galería del contacto");
        Label photosInfo = new Label("imágenes de la galería:");
        CircularLinkedList<String> imgPaths = new CircularLinkedList<>();
        imgPaths.addLast("src/main/resources/assets/pfp.png");
        loadPfp.setOnAction(ev -> {
            imgPaths.set(0, vistasUtilitary.chooseFile(contactStage));
            pfpInfo.setText("foto de contacto: " + imgPaths.get(0).substring(25));
        });
        loadPhoto.setOnAction(ev -> {
            imgPaths.addLast(vistasUtilitary.chooseFile(contactStage));
            String infotext = "\n" + imgPaths.getReference().getPrevious().getContent().substring(25);
            photosInfo.setText(photosInfo.getText() + infotext);
        });
        input.getChildren().addAll(loadPfp, pfpInfo, loadPhoto, photosInfo);
        return imgPaths;
    }

    private void saveContact(Contact c) {
        boolean empty = false;
        if (showingContacts.isEmpty()) {
            empty = true;
        }
        currentUser.getData().add(c);
        if (empty) {
            cursor = showingContacts.getReference();
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
        int contactsSize = showingContacts.size();
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
