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
import baseClasses.IconicDate;
import baseClasses.Person;
import baseClasses.PhoneNumber;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import persistence.Memory;
import tdas.ArrayList;
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
    @FXML
    private Button deleteBtn;
    @FXML
    private Button editBtn;

    private HBox gallery = new HBox(8);
    private static Contact currentContact;
    private CircularLinkedList<String> photos = currentContact.getPhotos();

    @FXML
    private void switchToContacts() throws IOException {
        App.setRoot("contacts");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startup();
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
        contactCreationArbitrator(c);
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
        showDates();
        buttons.getChildren().addAll(leftBtn, rightBtn);
        fields.getChildren().addAll(buttons, gallery);
        showGallery();
    }

    private void contactCreationArbitrator(Contact c) {
        if (c instanceof Person) {
            Person person = (Person) c;
            createPersonFields(person);
        } else if (c instanceof Company) {
            Company company = (Company) c;
            createCompanyFields(company);
        }
    }

    private void contactEditionArbitrator(Contact c, VBox input, Stage stage) {
        if (c instanceof Person) {
            Person person = (Person) c;
            editPersonFields(person, input, stage);
        } else if (c instanceof Company) {
            Company company = (Company) c;
            editCompanyFields(company, input, stage);
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
                photos.rotateDown();
            }
        }
    }

    private void showPhoneNumbers() {
        for (PhoneNumber p : currentContact.getPhoneNumbers()) {
            fields.getChildren().addAll(new Label(p.getContext() + ": "), new Label(p.toString()));
        }
    }

    private void showEmails() {
        for (Email e : currentContact.getEmails()) {
            fields.getChildren().addAll(new Label(e.getContext() + ": "), new Label(e.toString()));
        }
    }

    private void showAddresses() {
        for (Address a : currentContact.getAddresses()) {
            fields.getChildren().addAll(new Label(a.getContext() + ": "), new Label(a.toString()));
        }
    }

    private void showHandles() {
        for (Handle h : currentContact.getHandles()) {
            fields.getChildren().add(new Label(h.toString() + "\n"));
        }
    }

    private void showDates() {
        for (IconicDate i : currentContact.getDates()) {
            fields.getChildren().add(new Label(i.toString() + "\n"));
        }
    }

    private void createPhotoSlots(int k) {
        for (int i = 0; i < k; i++) {
            String imagePath = photos.get(0);
            ImageView photo = vistasUtilitary.loadImage(imagePath);
            int width = 64;
            if (i == 1) {
                width = 288;
            }
            photo.setPreserveRatio(true);
            photo.setFitWidth(width);
            photo.setSmooth(true);
            gallery.getChildren().add(photo);
            photos.rotateDown();
        }
    }

    @FXML
    private void editContact() {
        Contact c = currentContact;
        VBox input = new VBox(10);
        Scene contactScene = new Scene(input, 450, 700);
        Stage contactStage = new Stage();
        contactStage.setScene(contactScene);
        contactStage.show();
        contactEditionArbitrator(c, input, contactStage);
    }

    @FXML
    private void deleteContact(Contact c) {
        if (showConfirmationAlert()) {
            ContactsController.getContacts().remove(c);
            Memory.save();
            try {
                switchToContacts();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean showConfirmationAlert() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Eliminación del contacto");
        confirmationAlert.setHeaderText("Continuar con la eliminación?");
        confirmationAlert.setContentText("La eliminación es permamente e irreversible.");
        confirmationAlert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        java.util.Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void moveLeft() {
        if (!photos.isEmpty()) {
            photos.rotateUp();
            showGallery();
        }

    }

    private void moveRight() {
        if (!photos.isEmpty()) {
            photos.rotateDown();
            showGallery();
        }
    }

    private void editPersonFields(Person person, VBox input, Stage stage) {
        TextField firstName = new TextField(person.getFirstName());
        TextField middleName = new TextField(person.getMiddleName());
        TextField lastName = new TextField(person.getLastName());
        TextField context = new TextField(person.getContext());
        input.getChildren().addAll(firstName, middleName, lastName, context);
        CircularLinkedList<String> imgPaths = vistasUtilitary.galleryWizard(input, stage);
        ArrayList<PhoneNumber> phoneList = vistasUtilitary.phoneWizard(input);
        for (PhoneNumber p : person.getPhoneNumbers()) {
            phoneList.addLast(p);
        }
        ArrayList<Email> emailList = vistasUtilitary.emailWizard(input);
        for (Email e : person.getEmails()) {
            emailList.addLast(e);
        }
        ArrayList<Address> addressList = vistasUtilitary.addressWizard(input);
        for (Address a : person.getAddresses()) {
            addressList.addLast(a);
        }
        ArrayList<Handle> handleList = vistasUtilitary.handleWizard(input);
        for (Handle h : person.getHandles()) {
            handleList.addLast(h);
        }
        ArrayList<IconicDate> iconicDateList = vistasUtilitary.iconicDateWizard(input);
        for (IconicDate d : person.getDates()) {
            iconicDateList.addLast(d);
        }
        Button save = new Button("Guardar cambios");
        input.getChildren().add(save);
        save.setOnAction(ev -> {
            person.setFirstName(firstName.getText());
            person.setMiddleName(middleName.getText());
            person.setLastName(lastName.getText());
            person.setContext(context.getText());
            person.setPhoneNumbers(phoneList);
            person.setEmails(emailList);
            person.setAddresses(addressList);
            person.setHandles(handleList);
            person.setDates(iconicDateList);
            Memory.save();
            startup();
            stage.close();
        });
    }

    private void editCompanyFields(Company company, VBox input, Stage stage) {
        TextField name = new TextField(company.getName());
        TextField context = new TextField(company.getContext());
        input.getChildren().addAll(name, context);
        CircularLinkedList<String> imgPaths = vistasUtilitary.galleryWizard(input, stage);
        ArrayList<PhoneNumber> phoneList = vistasUtilitary.phoneWizard(input);
        for (PhoneNumber p : company.getPhoneNumbers()) {
            phoneList.addLast(p);
        }
        ArrayList<Email> emailList = vistasUtilitary.emailWizard(input);
        for (Email e : company.getEmails()) {
            emailList.addLast(e);
        }
        ArrayList<Address> addressList = vistasUtilitary.addressWizard(input);
        for (Address a : company.getAddresses()) {
            addressList.addLast(a);
        }
        ArrayList<Handle> handleList = vistasUtilitary.handleWizard(input);
        for (Handle h : company.getHandles()) {
            handleList.addLast(h);
        }
        ArrayList<IconicDate> iconicDateList = vistasUtilitary.iconicDateWizard(input);
        for (IconicDate d : company.getDates()) {
            iconicDateList.addLast(d);
        }
        Button save = new Button("Guardar cambios");
        input.getChildren().add(save);
        save.setOnAction(ev -> {
            company.setName(name.getText());
            company.setContext(context.getText());
            company.setPhoneNumbers(phoneList);
            company.setEmails(emailList);
            company.setAddresses(addressList);
            company.setHandles(handleList);
            company.setDates(iconicDateList);
            Memory.save();
            startup();
            stage.close();
        });
    }

    private void startup() {
        fields.getChildren().clear();
        createFields(currentContact);
        deleteBtn.setOnAction(ev -> {
            deleteContact(currentContact);
        });
    }

}
