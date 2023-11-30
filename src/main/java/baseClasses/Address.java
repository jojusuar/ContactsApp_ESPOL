
package baseClasses;

import java.io.Serializable;

/**
 *
 * @author euclasio
 */
public class Address implements Serializable{
    private String context;
    private String street1;
    private String street2;
    private String code;
    private String city;
    private String country;

    public Address(String context, String street1, String street2) {
        this.context = context;
        this.street1 = street1;
        this.street2 = street2;
    }

    public Address(String context, String street1, String street2, String code, String city, String country) {
        this.context = context;
        this.street1 = street1;
        this.street2 = street2;
        this.code = code;
        this.city = city;
        this.country = country;
    }

    public String getContext() {
        return context;
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getCode() {
        return code;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }


    public void setContext(String context) {
        this.context = context;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString(){
        return street1+" y "+street2+", "+city+", "+country;
    }

    
    
}
