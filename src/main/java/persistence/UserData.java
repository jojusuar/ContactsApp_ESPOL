/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import baseClasses.Contact;
import java.io.Serializable;
import tdas.ArrayList;
import tdas.CircularLinkedList;

/**
 *
 * @author euclasio
 */
public class UserData implements Serializable{
    private ArrayList<Contact> contacts;
    
    public UserData(){
        contacts = new ArrayList<>();
    }
    
    public void add(Contact c){
        contacts.addLast(c);
    };
    public void delete(Contact c){
        contacts.remove(c);
    };

    public ArrayList<Contact> getContacts() {
        return contacts;
    }
}
