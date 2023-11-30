
package baseClasses;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author euclasio
 */
public class IconicDate implements Serializable{
    private String context;
    private Date date;

    public IconicDate(String context, String day, String month, String year) {
        this.context = context;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try{
            this.date = dateFormat.parse(day+"/"+month+"/"+year);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getContext() {
        return context;
    }

    public Date getDate() {
        return date;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    @Override
    public String toString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return context+": "+dateFormat.format(date);
    }
    
}
