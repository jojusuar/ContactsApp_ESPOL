/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.espol.proyecto1_estructuras;

import baseClasses.Address;
import baseClasses.Company;
import baseClasses.Contact;
import baseClasses.Email;
import baseClasses.Handle;
import baseClasses.Person;
import baseClasses.PhoneNumber;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import tdas.CircularLinkedList;
import tdas.DoubleLinkNode;

/**
 * FXML Controller class
 *
 * @author euclasio
 */
public class ContactCardController implements Initializable {

    @FXML
    private VBox fields;
    private HBox gallery = new HBox(8);
    private static Contact currentContact;
    private CircularLinkedList<String> photos = currentContact.getPhotos();
    private DoubleLinkNode<String> cursor = null;

    @FXML
    private void switchToContacts() throws IOException {
        App.setRoot("contacts");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cursor = photos.getReference();
        createFields(currentContact);
    }

    public static void setCurrentContact(Contact c) {
        currentContact = c;
    }

    private void createFields(Contact c) {
        ImageView pfp = vistasUtilitary.loadImage(c.getPfp());
        vistasUtilitary.cropIntoCircle(pfp, 125);
        HBox banner = new HBox();
        Label context = new Label(c.getContext());
        banner.getChildren().addAll(pfp, context);
        banner.setAlignment(Pos.CENTER);
        fields.getChildren().addAll(banner, context);
        contactArbitrator(c);
        gallery.setOnScroll((ScrollEvent event) -> {
            double deltaX = event.getDeltaX();
            if (deltaX > 0) {
                moveLeft();
            } else if (deltaX < 0) {
                moveRight();
            }
        });
        Button leftBtn = new Button("<");
        Button rightBtn = new Button(">");
        leftBtn.setOnAction(ev -> {
            moveLeft();
        });
        rightBtn.setOnAction(ev -> {
            moveRight();
        });
        leftBtn.setWrapText(true);
        rightBtn.setWrapText(true);
        HBox buttons = new HBox(375);
        showPhoneNumbers();
        showEmails();
        showAddresses();
        showHandles();
        buttons.getChildren().addAll(leftBtn, rightBtn);
        fields.getChildren().addAll(buttons, gallery);
        showGallery();
    }
    
    private void contactArbitrator(Contact c){
        if (c instanceof Person) {
            Person person = (Person) c;
            createPersonFields(person);
        } else if (c instanceof Company) {
            Company company = (Company) c;
            createCompanyFields(company);
        }
    }
    
    private void createPersonFields(Person person) {
        Label namelbl = new Label("nombres:");
        Label name = new Label(person.getFirstName() + " " + person.getMiddleName());
        name.setFont(new Font("Times New Roman", 25));
        Label lastnamelbl = new Label("apellidos:");
        Label lastname = new Label(person.getLastName());
        lastname.setFont(new Font("Times New Roman", 25));
        fields.getChildren().addAll(namelbl, name, lastnamelbl, lastname);
    }

    private void createCompanyFields(Company company) {
        Label namelbl = new Label("nombre:");
        Label name = new Label(company.getName());
        name.setFont(new Font("Times New Roman", 25));
        fields.getChildren().addAll(namelbl, name);
    }

    private void showGallery() {
        gallery.getChildren().clear();
        int gallerySize = photos.size();
        if (gallerySize <= 3) {
            createPhotoSlots(gallerySize);
        } else {
            createPhotoSlots(3);
            for (int i = 0; i < gallerySize - 3; i++) {
                cursor = cursor.getNext();
            }
        }
    }
    
    private void showPhoneNumbers() {
        for(PhoneNumber p: currentContact.getPhoneNumbers()){
            fields.getChildren().add(new Label(p.getContext()+": \n"+p.toString()+"\n"));
        }
    }
    
    private void showEmails() {
        for(Email e: currentContact.getEmails()){
            fields.getChildren().add(new Label(e.getContext()+": \n"+e.toString()+"\n"));
        }
    }
    
    private void showAddresses() {
        for(Address a: currentContact.getAddresses()){
            fields.getChildren().add(new Label(a.getContext()+": \n"+a.toString()+"\n"));
        }
    }
    
        private void showHandles() {
        for(Handle h: currentContact.getHandles()){
            fields.getChildren().add(new Label(h.toString()+"\n"));
        }
    }

    private void createPhotoSlots(int k) {
        for (int i = 0; i < k; i++) {
            String imagePath = cursor.getContent();
            ImageView photo = vistasUtilitary.loadImage(imagePath);
            int width = 64;
            if (i == 1) {
                width = 288;
            }
            photo.setPreserveRatio(true);
            photo.setFitWidth(width);
            photo.setSmooth(true);
            gallery.getChildren().add(photo);
            cursor = cursor.getNext();
        }
    }

    private void moveLeft() {
        if (cursor != null) {
            cursor = cursor.getPrevious();
            showGallery();
        }

    }

    private void moveRight() {
        if (cursor != null) {
            cursor = cursor.getNext();
            showGallery();
        }
    }  

}
