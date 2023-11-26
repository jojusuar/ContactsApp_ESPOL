/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.espol.proyecto1_estructuras;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author euclasio
 */
public class vistasUtilitary {
    
    public static String chooseFile(Stage primaryStage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una foto de perfil para el contacto");
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        String pfpPath = "src/main/resources/assets/pfp.png";
        if (selectedFile != null) {
            Path sourcePath = selectedFile.toPath();
            pfpPath = "src/main/resources/cache/"+selectedFile.getName();
            Path destinationPath = Path.of(pfpPath);
            try {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pfpPath;
    }
}
