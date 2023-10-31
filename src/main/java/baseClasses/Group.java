
package baseClasses;

import tdas.CircularLinkedList;

/**
 *
 * @author euclasio
 */
public class Group {
    private String name;
    private CircularLinkedList<Contact> contacts;

    public Group(String name) {
        this.name = name;
        this.contacts = new CircularLinkedList<>();
    }

    public String getName() {
        return name;
    }

    public CircularLinkedList<Contact> getContacts() {
        return contacts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addContact(Contact c) {
        contacts.add(c);
    }
    
    @Override
    public String toString(){
        String grouped = "";
        for(Contact c: contacts){
            grouped += c+", ";
        }
        return grouped.substring(0, grouped.length()-3);
    } 
    
}
