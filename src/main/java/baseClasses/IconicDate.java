
package baseClasses;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 *
 * @author euclasio
 */
public class IconicDate implements Serializable{
    private String context;
    private SimpleDateFormat date;

    public IconicDate(String context, String date) {
        this.context = context;
        this.date = new SimpleDateFormat(date);
    }

    public String getContext() {
        return context;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }
    
    @Override
    public String toString(){
        return context+": "+date;
    }
    
}
