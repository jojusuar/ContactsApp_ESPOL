/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import baseClasses.Contact;
import java.io.Serializable;
import tdas.CircularLinkedList;

/**
 *
 * @author euclasio
 */
public class UserData implements Serializable{
    private CircularLinkedList<Contact> contacts;
}
