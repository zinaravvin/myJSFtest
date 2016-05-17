/**
 * Created by 202824 on 3/8/2016.
 */
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@ManagedBean(name = "student")
/* If no name is specified in the @ManagedBean annotation, the managed bean is always
 * accessed with the first letter of the class name in lowercase.
 */
public class StudentBean {
    private String firstName;
    private String lastName;
    private String country;
    private String message;


    boolean editable;

    public boolean isEditable() {
          return editable;
    }
    public void setEditable(boolean editable) {

        this.editable = editable;

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {


        return message;
    }



    public StudentBean(String firstName, String lastName, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public StudentBean(){}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
