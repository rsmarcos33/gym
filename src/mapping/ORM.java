
package mapping;

import checkup.Search;
import database.DataGet;
import database.DataSave;
import model.Appointment;
import model.User;
import model.Packet;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
//this class is used for easier getting from database and saving into it
public class ORM {
    User user;
    Packet packet;
    Appointment appointment;
    Alert alert;
    public ORM(){
        user = new User();
        packet = new Packet();
        appointment = new Appointment();
    }
    public void insertUser(String name, String surname) throws IOException, SQLException{
    user.setName(name);
    user.setSurname(surname);
    DataSave.saveUser(user);
    }
    public void insertPacket(String contract) throws SQLException{
    packet.setContract(contract);       
    DataSave.savePacket(packet);
    }
    public void useFirstAppointment() throws SQLException{
    DataSave.saveAppointment(appointment);        
    }
    public void useAppointment(int id) throws SQLException{
    appointment.setId(id);
    appointment.setUsedAppointments(DataGet.getAppointments(id));
    DataSave.useAppointment(appointment);
        }
    public void deleteUser(int id) throws SQLException{
        user.setId(id);
        DataSave.deleteUser(user);
    }
    public List search(String searchBy, String parameter) throws SQLException{
        List list = new ArrayList();
        switch(searchBy){
            case "ime":
                list = Search.searchName(parameter);
                break;
            case "prezime":
                list = Search.searchPrename(parameter);
                break;
            case "id":
                list = Search.search(Integer.valueOf(parameter));
                break;
        }
        return list;
    }
    public void updateUser(int id, String name, String surname) throws IOException, SQLException{
    user.setId(id);
    user.setName(name);
    user.setSurname(surname);
    DataSave.updateUser(user);
    }
    public void updatePacket(int id, String contract, LocalDate lastDay) throws SQLException{
        packet.setId(id);
        packet.setContract(contract);
        packet.setLastDay(lastDay);
        DataSave.updatePacket(packet);
    }
    public void renewAppointments(int id) throws SQLException{
        appointment.setId(id);
        DataSave.renewAppointments(appointment);
    }
    public User getUser(int id) throws SQLException{
        user = DataGet.getUser(id);
        return user;
    }
    public Packet getPacket(int id) throws SQLException{
        packet = DataGet.getPacket(id);
        return packet;
    }
    public LocalDate[] getAppointment(int id) throws SQLException{
        appointment.setUsedAppointments(DataGet.getAppointments(id));
        return appointment.getUsedAppointments();
    }
    public void updateAppointments(int id, LocalDate[] ld) throws SQLException{
        appointment.setId(id);
        appointment.setUsedAppointments(ld);
        DataSave.updateAppointments(appointment);
    }
    public List getUsers() throws SQLException{
      List list = DataGet.getUsers();
        return list;
    }
    public List getPackets() throws SQLException{
      List list = DataGet.getPackets();
        return list;
    }
    
}
