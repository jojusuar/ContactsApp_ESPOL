/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.espol.proyecto1_estructuras;

import baseClasses.Email;
import baseClasses.PhoneNumber;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Ellipse;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tdas.ArrayList;
import tdas.CircularLinkedList;

/**
 *
 * @author euclasio
 */
public class vistasUtilitary {

    public static String chooseFile(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imagen");
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        String pfpPath = "src/main/resources/assets/pfp.png";
        if (selectedFile != null) {
            Path sourcePath = selectedFile.toPath();
            pfpPath = "src/main/resources/cache/" + selectedFile.getName();
            Path destinationPath = Path.of(pfpPath);
            try {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pfpPath;
    }

    public static ImageView loadImage(String path) {
        ImageView loaded = null;
        try (FileInputStream in = new FileInputStream(path);) {
            loaded = new ImageView(new Image(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loaded;
    }

    public static void cropIntoCircle(ImageView imageview, double radius) {
        imageview.setPreserveRatio(true);
        Double imageW = imageview.getBoundsInLocal().getWidth();
        Double imageH = imageview.getBoundsInLocal().getHeight();
        if (imageW > imageH) {
            imageview.setFitHeight(radius * 2);
            imageview.setViewport(new javafx.geometry.Rectangle2D((imageW - imageH) / 2, 0, imageH, imageH));
        } else {
            imageview.setFitWidth(radius * 2);
            imageview.setViewport(new javafx.geometry.Rectangle2D(0, (imageH - imageW) / 2, imageW, imageW));
        }
        imageview.setSmooth(true);
        Ellipse ellipse = new Ellipse();
        ellipse.setCenterX(imageview.getBoundsInLocal().getWidth() / 2.0);
        ellipse.setCenterY(imageview.getBoundsInLocal().getHeight() / 2.0);
        ellipse.setRadiusX(radius);
        ellipse.setRadiusY(radius);
        imageview.setClip(ellipse);
    }
    
    public static CircularLinkedList<String> galleryWizard(VBox input, Stage contactStage) {
        Button loadPfp = new Button("Cargar foto de contacto");
        Label pfpInfo = new Label("foto de perfil no seleccionada");
        Button loadPhoto = new Button("Cargar imagen a la galería del contacto");
        VBox loadedImages = new VBox();
        Label photosInfo = new Label("imágenes de la galería:");
        CircularLinkedList<String> imgPaths = new CircularLinkedList<>();
        imgPaths.addLast("src/main/resources/assets/pfp.png");
        loadPfp.setOnAction(ev -> {
            imgPaths.set(0,vistasUtilitary.chooseFile(contactStage));
            pfpInfo.setText("foto de contacto: " + imgPaths.get(0).substring(25));
        });
        loadPhoto.setOnAction(ev -> {
            String imagePath = vistasUtilitary.chooseFile(contactStage);
            imgPaths.addLast(imagePath);
            HBox imageName = new HBox();
            Label infotext = new Label(imgPaths.getReference().getPrevious().getContent().substring(25));
            Button forget = new Button("X");
            imageName.getChildren().addAll(infotext, forget);
            loadedImages.getChildren().add(imageName);
            forget.setOnAction(ev2 -> {
                Comparator<String> comp = (String s1, String s2) -> {return s1.compareTo(s2);};
                imgPaths.remove(imagePath, comp);
                loadedImages.getChildren().remove(imageName);
            });
        });
        input.getChildren().addAll(loadPfp, pfpInfo, loadPhoto, photosInfo, loadedImages);
        return imgPaths;
    }
    
    public static ArrayList<PhoneNumber> phoneWizard(VBox input){
        Button newPhoneBtn = new Button("Agregar número de teléfono");
        VBox phoneVb = new VBox();
        ArrayList<PhoneNumber> phoneList = new ArrayList<>();
        newPhoneBtn.setOnAction(ev -> {
            HBox phoneFields = new HBox();
            TextField code = new TextField("código");
            TextField number = new TextField("número");
            TextField context = new TextField("contexto");
            Button add = new Button("O");
            Button abort = new Button("X");
            phoneFields.getChildren().addAll(code, number, context, add,abort);
            phoneVb.getChildren().add(phoneFields);
            add.setOnAction(ev2 -> {
                PhoneNumber phoneNumber = new PhoneNumber(context.getText(), Integer.parseInt(code.getText()), Integer.parseInt(number.getText()));
                phoneList.addLast(phoneNumber);
                phoneFields.getChildren().clear();
                Label lbl = new Label(phoneNumber.getContext()+": "+phoneNumber.toString());
                Button forget = new Button("X");
                phoneFields.getChildren().addAll(lbl, forget);
                forget.setOnAction(ev3 -> {
                   Comparator<PhoneNumber> comp = (PhoneNumber p1, PhoneNumber p2) -> {return p1.toString().compareTo(p2.toString());}; 
                   phoneList.remove(phoneNumber, comp);
                   phoneVb.getChildren().remove(phoneFields);
                });
            });
            abort.setOnAction(ev2 -> {
                phoneVb.getChildren().remove(phoneFields);
            });
        });
        input.getChildren().addAll(newPhoneBtn, phoneVb);
        return phoneList;
    }
    
    public static ArrayList<Email> emailWizard(VBox input){
        Button newEmailBtn = new Button("Agregar correo electrónico");
        VBox emailVb = new VBox();
        ArrayList<Email> emailList = new ArrayList<>();
        newEmailBtn.setOnAction(ev -> {
            HBox emailFields = new HBox();
            TextField mailTf = new TextField("email");
            TextField context = new TextField("contexto");
            Button add = new Button("O");
            Button abort = new Button("X");
            emailFields.getChildren().addAll(mailTf, context, add,abort);
            emailVb.getChildren().add(emailFields);
            add.setOnAction(ev2 -> {
                Email email = new Email(context.getText(), mailTf.getText());
                emailList.addLast(email);
                emailFields.getChildren().clear();
                Label lbl = new Label(email.getContext()+": "+email.toString());
                Button forget = new Button("X");
                emailFields.getChildren().addAll(lbl, forget);
                forget.setOnAction(ev3 -> {
                   Comparator<Email> comp = (Email e1, Email e2) -> {return e1.toString().compareTo(e2.toString());}; 
                   emailList.remove(email, comp);
                   emailVb.getChildren().remove(emailFields);
                });
            });
            abort.setOnAction(ev2 -> {
                emailVb.getChildren().remove(emailFields);
            });
        });
        input.getChildren().addAll(newEmailBtn, emailVb);
        return emailList;
    }
}
