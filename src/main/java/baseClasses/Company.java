
package baseClasses;

/**
 *
 * @author euclasio
 */
public class Company extends Contact {
    private String name;

    public Company(String context, String pfp, String name) {
        super(context);
        this.pfp = pfp;
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
