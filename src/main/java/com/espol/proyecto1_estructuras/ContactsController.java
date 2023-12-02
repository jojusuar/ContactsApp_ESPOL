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
import java.util.Comparator;
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

/**
 * FXML Controller class
 *
 * @author euclasio
 */
public class ContactsController implements Initializable {

    private static User currentUser = null;
    private static CircularLinkedList<Contact> contacts = null;
    private static CircularLinkedList<Contact> showingContacts = null;

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
    private ComboBox<String> filterCb;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ComboBox<String> orderByCb;

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
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
        orderByCb.getItems().addAll("Nombre y Apellido", "Empresa", "Pais");
        orderByCb.setOnAction(e -> {
            String s = orderByCb.getValue();
            showingContacts = contacts;
            filter(s);
            sort(getComparator(s));

        });
    }

    @FXML
    private void search() {
        CircularLinkedList<Contact> tempContactList = new CircularLinkedList<>();
        showingContacts = contacts;

        String s = searchBar.getText().toLowerCase();
        if (s.isBlank() || s.isEmpty()) {
            showingContacts = contacts;

            showContacts();
        } else {
            String filter = (String) filterCb.getValue();
            if (filter.compareTo("por nombre") == 0) {
                nameFilter(s, tempContactList);
            } else if (filter.compareTo("por teléfono") == 0) {
                phoneFilter(s, tempContactList);
            } else if (filter.compareTo("por ciudad") == 0) {
                cityFilter(s, tempContactList);
            }
            showingContacts = tempContactList;
            showContacts();
        }
    }

    private void nameFilter(String query, CircularLinkedList<Contact> list) {
        for (int i = 0; i < showingContacts.size(); i++) {
            Contact contact = showingContacts.get(0);
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
            showingContacts.rotateDown();
        }
    }

    private void phoneFilter(String query, CircularLinkedList<Contact> list) {
        for (int i = 0; i < showingContacts.size(); i++) {
            Contact contact = showingContacts.get(0);
            ArrayList<PhoneNumber> phonelist = contact.getPhoneNumbers();
            if (!phonelist.isEmpty()) {
                for (PhoneNumber phone : phonelist) {
                    if (phone.toString().contains(query)) {
                        list.addLast(contact);
                    }
                }
            }
            showingContacts.rotateDown();
        }
    }

    private void cityFilter(String query, CircularLinkedList<Contact> list) {
        for (int i = 0; i < showingContacts.size(); i++) {
            Contact contact = showingContacts.get(0);
            ArrayList<Address> addressList = contact.getAddresses();
            boolean match = false;
            if (!addressList.isEmpty()) {
                for (Address address : addressList) {
                    if (address.getCity().contains(query)) {
                        match = true;
                    }
                }
            }
            if (match) {
                list.addLast(contact);
            }
            showingContacts.rotateDown();
        }
    }

    private void sort(Comparator<Contact> comparator) {
        CircularLinkedList<Contact> tempContactList = showingContacts;
        for (int i = 0; i < tempContactList.size(); i++) {
            for (int j = i + 1; j < tempContactList.size(); j++) {
                if (comparator.compare(tempContactList.get(i), tempContactList.get(j)) < 0) {
                    // se intercambian ya que el primer elemento debe estar despues del segundo
                    // elemento
                    Contact temp1 = tempContactList.get(j);
                    Contact temp2 = tempContactList.get(i);
                    tempContactList.set(i, temp1);
                    tempContactList.set(j, temp2);
                }
            }
        }
        showingContacts = tempContactList;
        showContacts();

    }

    public Comparator<Contact> getComparator(String option) {
        Comparator<Contact> comparator = null;
        if (option.compareTo("Nombre y Apellido") == 0) {
            comparator = (Contact o1, Contact o2) -> {
                Person p1 = (Person) o1;
                Person p2 = (Person) o2;
                int comparacionNombre = p2.getFirstName().compareTo(p1.getFirstName());
                if (comparacionNombre == 0) {
                    return p2.getLastName().compareTo(p1.getLastName());
                }
                return comparacionNombre;
            };
        } else if (option.compareTo("Empresa") == 0) {
            comparator = (Contact o1, Contact o2) -> {
                Company c1 = (Company) o1;
                Company c2 = (Company) o2;
                return c2.getName().compareTo(c1.getName());
            };
        } else if (option.compareTo("Pais") == 0) {
            comparator = (Contact o1, Contact o2) -> o2.getAddresses().getFirst().getCountry()
                    .compareTo(o1.getAddresses().getFirst().getCountry());
        }
        return comparator;
    }

    public void filter(String option) {
        CircularLinkedList<Contact> tempContactList = new CircularLinkedList<>();
        System.out.println(option);
        System.out.println("------------------------------------");
        if (option.compareTo("Nombre y Apellido") == 0) {
            for (int i = 0; i < showingContacts.size(); i++) {
                Contact contact = showingContacts.get(0);
                if (contact instanceof Person) {
                    Person p = (Person) contact;
                    if (p.getFirstName() != null && p.getLastName() != null) {
                        tempContactList.addLast(contact);
                        System.out.println("nombre y apellido");
                    }
                }
                showingContacts.rotateDown();
            }
        } else if (option.compareTo("Empresa") == 0) {
            for (int i = 0; i < showingContacts.size(); i++) {
                Contact contact = showingContacts.get(0);
                if (contact instanceof Company) {
                    Company c = (Company) contact;
                    if (c.getName() != null) {
                        tempContactList.addLast(contact);
                        System.out.println("Empresa");
                    }
                }
                showingContacts.rotateDown();
            }
        } else if (option.compareTo("Pais") == 0) {
            for (int i = 0; i < showingContacts.size(); i++) {
                Contact contact = showingContacts.get(0);
                if (!contact.getAddresses().isEmpty()) {
                    tempContactList.addLast(contact);
                    System.out.println("Pais");
                }
                showingContacts.rotateDown();
            }
        }
        System.out.println(tempContactList);
        showingContacts = tempContactList;
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
        currentUser.getData().add(c);
        Memory.save();
        showContacts();
    }

    public static void setCurrentUser(User currentUser) {
        ContactsController.currentUser = currentUser;
    }

    public static CircularLinkedList<Contact> getContacts() {
        return contacts;
    }

    private void showContacts() {
        grid.getChildren().clear();
        int contactsSize = showingContacts.size();
        if (contactsSize <= 9 && contactsSize > 0) {
            createContactSlots(contactsSize);
        } else if (contactsSize > 9) {
            createContactSlots(10);
            for (int i = 0; i < contactsSize - 10; i++) {
                showingContacts.rotateDown();
            }
        }
    }

    private void editContact(Contact contact) {
        // edit context and name

        VBox inputs = new VBox(10);
        Scene contactScene = new Scene(inputs, 450, 450);
        Stage contactStage = new Stage();
        contactStage.setScene(contactScene);
        contactStage.show();

        TextField context = new TextField(contact.getContext());
        Label name = new Label("Contactos asociados");
        Button save = new Button("Guardar cambios");
        VBox contactosAsociados = new VBox();
        for (Contact relContact : contact.getRelatedContacts()) {
            HBox relCard = new HBox(20);
            Label relName = new Label(relContact.toString());
            ImageView pfp = vistasUtilitary.loadImage(relContact.getPfp());
            relCard.getChildren().addAll(relName, pfp);
            contactosAsociados.getChildren().add(relCard);
        }
        inputs.getChildren().addAll(context, name, contactosAsociados, save);

        save.setOnAction(ev -> {
            contact.setContext(context.getText());
            contactStage.close();
        });
    }

    private void createContactSlots(int k) {
        for (int i = 0; i < k; i++) {
            Contact contact = showingContacts.get(0);
            HBox card = new HBox(20);
            Label name = new Label(contact.toString());
            ImageView pfp = vistasUtilitary.loadImage(contact.getPfp());
            vistasUtilitary.cropIntoCircle(pfp, 18);
            Button edit = new Button("Editar contacto");
            edit.setOnAction(ev -> editContact(contact));
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
            showingContacts.rotateDown();
        }
    }

    @FXML
    private void moveUp() {
        showingContacts.rotateUp();
        showContacts();
    }

    @FXML
    private void moveDown() {
        showingContacts.rotateDown();
        showContacts();
    }
}
