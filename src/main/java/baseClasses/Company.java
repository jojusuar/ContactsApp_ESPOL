
package baseClasses;

/**
 *
 * @author euclasio
 */
public class Company extends Contact {
    private String name;

    public Company(String context, boolean isFavorite, String name) {
        super(context, isFavorite);
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
