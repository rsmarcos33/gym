package controller;

import mapping.ORM;
import model.PacketProp;
import model.User;
import model.UserProp;
import view.SceneChanger;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MainController implements Initializable {
    ORM orm;
    @FXML Button back;
    @FXML Button newmm;
    @FXML Button list;
    @FXML Button ua;
    @FXML TextField br;
    @FXML Button btnExpired;
    Alert alert;
    SceneChanger sch = new SceneChanger();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        orm = new ORM();   
    }    
    //on action this method changes scene
    public void NewMember(ActionEvent event) throws IOException{
    String scena ="NewMember.fxml";
    sch.change(scena, event);
    }
    //on action this method changes scene
    public void ListOfMembers(ActionEvent event) throws IOException{
        String scena = "ListOfMembers.fxml";
        sch.change(scena, event);
    }
    //on action this method tries to use appointment and shows alert
    public void useAppointment(ActionEvent event){
        Alert error = new Alert(Alert.AlertType.ERROR);
        int id;
        try{
        id = Integer.valueOf(br.getText());
        boolean slovobroj = id<0||id==0||id>0;
        if(slovobroj==true){
            if(id<=0){
            error.setContentText("Molimo da unesete id u trazenom formatu(npr. 15)!");
            error.show();}else if(id>0) {
            orm.useAppointment(id);
            }  }
        }catch(Exception ex){
            error.setContentText("Unesi broj člana da bi iskoristio termin!");
            error.show();
        }
        
    }
    //on action this method gives alert 
            public void expired(ActionEvent event) throws SQLException{
            boolean n = false; 
            int b = 0;
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Info");
            String content = "Clanarine isticu sledecim clanovima:\r\n";
            
            List users = orm.getUsers();
            List packets = orm.getPackets();
            for(Object opacket:packets){
            PacketProp packet =  (PacketProp) opacket;
            String[] pld = packet.getLast_day().toString().split("-");
            if(LocalDate.of(Integer.parseInt(pld[0]), Integer.parseInt(pld[1]),Integer.parseInt(pld[2])).equals(LocalDate.now())){
                
            User user = orm.getUser(packet.getId());
            UserProp prop = new UserProp(user);
            content = content +("\r\n "+user.getId()+" "+user.getName()+" "+user.getSurname());
            b++;
            }}
            
            n = b==0;
            if(n==true){
                content = content +("\r\nNi jednom clanu ne istice clanarina.");
            }
            alert.setContentText(content);
            alert.show();
        }
}
