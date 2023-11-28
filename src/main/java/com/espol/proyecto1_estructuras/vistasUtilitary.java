/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.espol.proyecto1_estructuras;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Ellipse;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
}
