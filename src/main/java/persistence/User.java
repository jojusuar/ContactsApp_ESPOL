/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

/**
 *
 * @author euclasio
 */
public class User {
    private String username;
    private String password;
    private UserData[] data;
    
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.data = new UserData[1];
        data[0]= new UserData();
    }
}
