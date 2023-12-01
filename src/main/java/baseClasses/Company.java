
package baseClasses;

import java.util.Objects;
import tdas.ArrayList;
import tdas.CircularLinkedList;

/**
 *
 * @author euclasio
 */
public class Company extends Contact {
    private String name;

    public Company(String context, String pfp, String name, CircularLinkedList<String> gallery) {
        super(context);
        this.pfp = pfp;
        this.name = name;
        this.photos = gallery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(context,isFavorite,commonGroups,traits,phoneNumbers,addresses,emails,handles,pfp,photos,dates,relatedContacts, name);
    }
}
