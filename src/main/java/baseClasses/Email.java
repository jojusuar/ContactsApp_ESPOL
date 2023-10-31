
package baseClasses;

import java.io.Serializable;

/**
 *
 * @author euclasio
 */
public class Email implements Serializable{
    private String context;
    private String emailAddress;

    public Email(String context, String emailAddress) {
        this.context = context;
        this.emailAddress = emailAddress;
    }

    public String getContext() {
        return context;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    @Override
    public String toString(){
        return emailAddress;
    }
    
    
}
