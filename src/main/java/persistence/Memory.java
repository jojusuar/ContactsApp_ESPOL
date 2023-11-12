/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import tdas.ArrayList;

/**
 *
 * @author euclasio
 */
public class Memory {
    private static ArrayList<User> users = new ArrayList<>();

    public static ArrayList<User> getUsers() {
        return users;
    }
    
    public static void addUser(User user){
        users.addLast(user);
    }
    
    public static void deleteUser(User user){
        users.remove(user);
    }
    
    public static void save(){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/main/resources/memory/users.ser"));) {
            out.writeObject(users);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void load(){
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/main/resources/memory/users.ser"));) {
            users = (ArrayList<User>) in.readObject();
            System.out.println(users);
        } catch (IOException ex) {
            System.out.println("Error al cargar los usuarios");
            users = new ArrayList<>();
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró la clase");
        }
    }
}
