package controller;

import mapping.ORM;
import model.Appointment;
import model.Packet;
import model.User;
import view.SceneChanger;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class NewMemberController implements Initializable {
    ORM orm = new ORM();
    Alert alert;
    User user = new User();
    Packet packet = new Packet();
    Appointment appointment = new Appointment();
    @FXML Button bback;
    @FXML Button save;
    @FXML ChoiceBox cb;
    @FXML TextField tfName;
    @FXML TextField tfPrename;
    String dvanaest = "12x";
    String neograniceno = "neograniceno";
    SceneChanger sch = new SceneChanger();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cb.valueProperty().setValue(neograniceno);
        cb.getItems().add(dvanaest);
        cb.getItems().add(neograniceno);
    }  
    public void back(ActionEvent event) throws IOException{
    String main = "Main.fxml";
    sch.change(main, event);
    }
    public void saveMember() throws IOException, SQLException{
        try{
        orm.insertUser(tfName.getText(), tfPrename.getText());
        orm.insertPacket((String) cb.getValue());
        orm.useFirstAppointment();
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda");
        alert.setHeaderText("");
        alert.setContentText("Uspesno unet novi clan");
        alert.show();}catch(Exception ex){
            alert.setTitle("Greska");
            alert.setHeaderText("");
            alert.setContentText("Neuspesno unosenje novog clana, probajte opet!");
            alert.show();
        }
    }
    
}
