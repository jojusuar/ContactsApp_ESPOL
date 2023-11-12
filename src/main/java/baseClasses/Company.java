
package baseClasses;

/**
 *
 * @author euclasio
 */
public class Company extends Contact {
    private String name;

    public Company(String context, String name) {
        super(context);
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
