
package baseClasses;

/**
 *
 * @author euclasio
 */
public class Person extends Contact {
    private String firstName;
    private String middleName;
    private String lastName;

    public Person( String context, boolean isFavorite, String firstName, String middleName, String lastName) {
        super(context, isFavorite);
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }
    
    @Override
    public String toString(){
        return firstName+" "+middleName.charAt(0)+". "+lastName;
    }
}
