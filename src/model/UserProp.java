package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserProp {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty surname;
    
    public UserProp(User user){
        id = new SimpleIntegerProperty(user.getId());
        name = new SimpleStringProperty(user.getName());
        surname = new SimpleStringProperty(user.getSurname());
    }
    public Integer getId() {
        return id.getValue();
    }
    public void setId(IntegerProperty id) {
        this.id = id;
    }
    public String getName() {
        return name.getValue();
    }
    public void setName(StringProperty name) {
        this.name = name;
    }
    public String getPrename() {
        return surname.getValue();
    }
    public void setPrename(StringProperty surname) {
        this.surname = surname;
    }
}
