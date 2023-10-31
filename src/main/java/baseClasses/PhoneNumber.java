
package baseClasses;

import java.io.Serializable;

/**
 *
 * @author euclasio
 */
public class PhoneNumber implements Serializable {
    private String context;
    private int countryCode;
    private int number;

    public PhoneNumber(String context, int number) {
        this.context = context;
        this.countryCode = 593;
        this.number = number;
    }

    public PhoneNumber(String context, int countryCode, int number) {
        this.context = context;
        this.countryCode = countryCode;
        this.number = number;
    }

    public String getContext() {
        return context;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public int getNumber() {
        return number;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    @Override
    public String toString(){
        return "+"+countryCode+number;
    }
    
}
