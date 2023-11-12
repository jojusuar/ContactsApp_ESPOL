
package baseClasses;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javafx.scene.image.Image;
import tdas.ArrayList;
import tdas.CircularLinkedList;

/**
 *
 * @author euclasio
 */
public abstract class Contact implements Serializable {
    
    protected String context;
    protected boolean isFavorite;
    protected ArrayList<Group> commonGroups;
    protected ArrayList<String> traits;
    protected ArrayList<PhoneNumber> phoneNumbers;
    protected ArrayList<Address> addresses;
    protected ArrayList<Email> emails;
    protected ArrayList<Handle> handles;
    protected String pfp;
    protected CircularLinkedList<String> photos;
    protected ArrayList<IconicDate> dates;
    protected CircularLinkedList<Contact> relatedContacts;

    public Contact(String context) {
        this.context = context;
        this.isFavorite = false;
        this.commonGroups = new ArrayList<>();
        this.traits = new ArrayList<>();
        this.phoneNumbers = new ArrayList<>();
        this.addresses = new ArrayList<>();
        this.emails = new ArrayList<>();
        this.handles = new ArrayList<>();
        this.photos = new CircularLinkedList<>();
        this.pfp = "src/main/resources/assets/pfp.png";
        this.dates = new ArrayList<>();
        this.relatedContacts = new CircularLinkedList<>();
    }

    public String getContext() {
        return context;
    }

    public boolean isIsFavorite() {
        return isFavorite;
    }

    public ArrayList<Group> getCommonGroups() {
        return commonGroups;
    }

    public ArrayList<String> getTraits() {
        return traits;
    }

    public ArrayList<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public ArrayList<Email> getEmails() {
        return emails;
    }

    public ArrayList<Handle> getHandles() {
        return handles;
    }

    public CircularLinkedList<String> getPhotos() {
        return photos;
    }

    public ArrayList<IconicDate> getDates() {
        return dates;
    }

    public CircularLinkedList<Contact> getRelatedContacts() {
        return relatedContacts;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public void addCommonGroup(Group g) {
        commonGroups.addLast(g);
    }

    public void addTrait(String t) {
        traits.addLast(t);
    }

    public void addNumber(PhoneNumber n) {
        phoneNumbers.addLast(n);
    }

    public void addAddress(Address a) {
        addresses.addLast(a);
    }

    public void addEmail(Email e) {
        emails.addLast(e);
    }

    public void addHandle(Handle h) {
        handles.addLast(h);
    }

    public void addPhoto(String i) {
       photos.addLast(i);
    }

    public void addDate(IconicDate d) {
        dates.addLast(d);
    }

    public void addRelatedContact(Contact c) {
        relatedContacts.addLast(c);
    }

    public String getPfp() {
        return pfp;
    }

    public void setPfp(String pfp) {
        this.pfp = pfp;
    }
    
    @Override
    public abstract String toString();

}
