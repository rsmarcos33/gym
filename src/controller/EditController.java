package controller;

import mapping.ORM;
import model.Packet;
import model.User;
import view.SceneChanger;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class EditController implements Initializable {
    SceneChanger sch = new SceneChanger();
    ORM orm = new ORM();
    Alert alert;
    ListView lv;
    @FXML TextArea taId;
    @FXML TextField tfIme;
    @FXML TextField tfPrezime;
    @FXML ChoiceBox cbTarifa;
    @FXML ChoiceBox cbd;
    @FXML ChoiceBox cbm;
    @FXML ChoiceBox cby;
    @FXML VBox vb;
    @FXML Button btnback;
    @FXML Button btnsave;
    List list;
    LocalDate[] termini;
    List usedApp;
    int id = 0;
    LocalDate local;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cbTarifa.getItems().add("12x");
        cbTarifa.getItems().add("neograniceno");
        
        String[] days = new String[31];
        String[] months = new String[12];
        String[] years = new String[100];
        for(int i=0;i<days.length;i++){
        days[i] = String.valueOf(i+1);   
        cbd.getItems().add(days[i]);
        }
        for(int i=0;i<months.length;i++){
        months[i] = String.valueOf(i+1);  
        cbm.getItems().add(months[i]);
        }  
        for(int i=0;i<years.length;i++){
        years[i] = String.valueOf(i+2019);
        cby.getItems().add(years[i]);
        }
        lv = new ListView();

    }    
        public void getParameter(List list) throws InterruptedException{
        User user = null;
        Packet packet = null;
        local = LocalDate.of(1950, 1, 1);
        this.list = list;
        id = (int) list.get(0);
                try {
        user = orm.getUser(id);
        } catch (SQLException ex) {
            Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
        }
                taId.setText(String.valueOf(user.getId()));
                tfIme.setText(user.getName());
                tfPrezime.setText(user.getSurname());
        try {
        packet = orm.getPacket(id);
        } catch (SQLException ex) {
            Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
        }
        LocalDate lastDay = packet.getLastDay().minusMonths(1).minusDays(1);
        cbTarifa.getSelectionModel().select(packet.getContract());
        cby.getSelectionModel().select(String.valueOf(lastDay.getYear()));
        cbm.getSelectionModel().select(lastDay.getMonthValue());
        cbd.getSelectionModel().select(lastDay.getDayOfMonth());
        usedApp = new ArrayList();
        try {
            termini = orm.getAppointment(id);
        } catch (SQLException ex) {
            Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
        }
                for(int i=0;i<termini.length;i++){
            if(termini[i]!=null && local!=null && !termini[i].equals(local)){
                usedApp.add(termini[i]);
            }
        }
        lv.getItems().addAll(usedApp);
        vb.getChildren().add(lv);
        vb.boundsInParentProperty();
    }
        public void back(ActionEvent event) throws IOException{
        String scena = "ListOfMembers.fxml";
        sch.change(scena, event);
        }
        public void save(ActionEvent event) throws IOException, SQLException{
        orm.updateUser(id, tfIme.getText(), tfPrezime.getText());
        orm.updatePacket(id, (String) cbTarifa.getValue(), LocalDate.of(Integer.valueOf((String) cby.getValue()), Integer.valueOf((String) cbm.getValue()), Integer.valueOf((String)cbd.getValue())));
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potvrda");
        alert.setContentText("Uspesno sacuvan clan!!!");
        alert.show();
        }

}
