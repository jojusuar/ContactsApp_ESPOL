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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import tdas.ArrayList;
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
    private ComboBox filterCb;

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
        filterCb.getItems().addAll("por nombre", "por teléfono", "por ciudad");
        filterCb.getSelectionModel().selectFirst();
    }

    @FXML
    private void search() {
        CircularLinkedList<Contact> tempContactList = new CircularLinkedList<>();
        showingContacts = contacts;
        cursor = showingContacts.getReference();
        String s = searchBar.getText().toLowerCase();
        if (s.isBlank() || s.isEmpty()) {
            showingContacts = contacts;
            cursor = showingContacts.getReference();
            showContacts();
        } else {
            String filter = (String) filterCb.getValue();
            if (filter.compareTo("por nombre") == 0) {
                nameFilter(s, tempContactList);
            } else if (filter.compareTo("por teléfono") == 0) {
                phoneFilter(s, tempContactList);
            }
            else if(filter.compareTo("por ciudad") == 0){
                cityFilter(s,tempContactList);
            }
            showingContacts = tempContactList;
            cursor = showingContacts.getReference();
            showContacts();
        }
    }

    private void nameFilter(String query, CircularLinkedList<Contact> list) {
        for (int i = 0; i < showingContacts.size(); i++) {
            Contact contact = cursor.getContent();
            String fullname = "";
            if (contact instanceof Person) {
                Person p = (Person) contact;
                fullname = p.getFirstName() + " " + p.getMiddleName() + " " + p.getLastName();

            } else if (contact instanceof Company) {
                Company c = (Company) contact;
                fullname = c.getName();
            }
            if (fullname.toLowerCase().contains(query.toLowerCase())) {
                list.addLast(contact);
            }
            cursor = cursor.getNext();
        }
    }

    private void phoneFilter(String query, CircularLinkedList<Contact> list) {
        for (int i = 0; i < showingContacts.size(); i++) {
            Contact contact = cursor.getContent();
            ArrayList<PhoneNumber> phonelist = contact.getPhoneNumbers();
            if (!phonelist.isEmpty()) {
                for (PhoneNumber phone : phonelist) {
                    if (phone.toString().contains(query)) {
                        list.addLast(contact);
                    }
                }
            }
            cursor = cursor.getNext();
        }
    }
    private void cityFilter(String query, CircularLinkedList<Contact> list) {
        for (int i = 0; i < showingContacts.size(); i++) {
            Contact contact = cursor.getContent();
            ArrayList<Address> addressList = contact.getAddresses();
            boolean match = false;
            if (!addressList.isEmpty()) {
                for (Address address : addressList) {
                    if (address.getCity().contains(query)) {
                        match = true;
                    }
                }
            }
            if(match){
                list.addLast(contact);
            }
            cursor = cursor.getNext();
        }
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
        ArrayList<PhoneNumber> phoneList = vistasUtilitary.phoneWizard(input);
        ArrayList<Email> emailList = vistasUtilitary.emailWizard(input);
        ArrayList<Address> addressList = vistasUtilitary.addressWizard(input);
        ArrayList<Handle> handleList = vistasUtilitary.handleWizard(input);
        ArrayList<IconicDate> iconicDateList = vistasUtilitary.iconicDateWizard(input);
        Button save = new Button("Crear contacto");
        input.getChildren().add(save);
        save.setOnAction(ev -> {
            Contact contact = new Person(context.getText(), imgPaths.get(0), firstName.getText(), middleName.getText(),
                    lastName.getText(), imgPaths);
            contact.setPhoneNumbers(phoneList);
            contact.setEmails(emailList);
            contact.setAddresses(addressList);
            contact.setHandles(handleList);
            contact.setDates(iconicDateList);
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
        ArrayList<PhoneNumber> phoneList = vistasUtilitary.phoneWizard(input);
        ArrayList<Email> emailList = vistasUtilitary.emailWizard(input);
        ArrayList<Address> addressList = vistasUtilitary.addressWizard(input);
        ArrayList<Handle> handleList = vistasUtilitary.handleWizard(input);
        ArrayList<IconicDate> iconicDateList = vistasUtilitary.iconicDateWizard(input);
        Button save = new Button("Crear contacto");
        input.getChildren().add(save);
        save.setOnAction(ev -> {
            Contact contact = new Company(context.getText(), imgPaths.get(0), name.getText(), imgPaths);
            contact.setPhoneNumbers(phoneList);
            contact.setEmails(emailList);
            contact.setAddresses(addressList);
            contact.setHandles(handleList);
            contact.setDates(iconicDateList);
            saveContact(contact);
            contactStage.close();
        });
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
