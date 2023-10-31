
package baseClasses;

import java.io.Serializable;

/**
 *
 * @author euclasio
 */
public class Handle implements Serializable {
    private String username;
    private String socialNetwork;

    public Handle(String username, String socialNetwork) {
        this.username = username;
        this.socialNetwork = socialNetwork;
    }

    public String getUsername() {
        return username;
    }

    public String getSocialNetwork() {
        return socialNetwork;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }
    
    @Override
    public String toString(){
        return "@"+username;
    }
}
