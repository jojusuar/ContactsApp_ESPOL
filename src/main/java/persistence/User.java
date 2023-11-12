/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import java.io.Serializable;

/**
 *
 * @author euclasio
 */
public class User implements Serializable{
    private String username;
    private String password;
    private UserData[] data;
    
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.data = new UserData[1];
        data[0]= new UserData();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String toString(){
        return username;
    } 
}
