
package baseClasses;

import java.util.Objects;
import tdas.CircularLinkedList;

/**
 *
 * @author euclasio
 */
public class Person extends Contact {
    private String firstName;
    private String middleName;
    private String lastName;

    public Person( String context, String pfp, String firstName, String middleName, String lastName, CircularLinkedList<String> gallery) {
        super(context);
        this.pfp = pfp;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.photos = gallery;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @Override
    public String toString(){
        return firstName+" "+middleName.charAt(0)+". "+lastName;
    }
    @Override
    public int hashCode() {
        return Objects.hash(context,isFavorite,commonGroups,traits,phoneNumbers,addresses,emails,handles,pfp,photos,dates,relatedContacts, firstName, middleName, lastName);
    }
}
