package database;

import checkup.Search;
import mapping.ORM;
import model.Appointment;
import model.User;
import model.Packet;
import path.CFG;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
//this class is used for saving data into database
public class DataSave{
    static Alert alert;
    static PreparedStatement st = null;
    static LocalDate local;
    static Connection conn = null;
    static ORM orm = null;
    //method for quicker connection to database
    private static Connection getConnection() throws SQLException{
        return (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/"+CFG.database, "root", "");
    }
    //method for saving user into database
    public static void saveUser(User user) throws IOException, SQLException{
         conn = getConnection();
         st  = conn.prepareCall("insert into users values(null,?,?)");
         st.setString(1, user.getName());
         st.setString(2, user.getSurname());
         st.execute();
    }
    //method for saving packet into database
    public static void savePacket(Packet packet) throws SQLException{
        conn = getConnection();
        ResultSet rs = conn.createStatement().executeQuery("select id from users");
        while(rs.next()){
            packet.setId(rs.getInt("id"));
        }
        packet.setLastDay(LocalDate.now().plusMonths(1));
        st = conn.prepareStatement("insert into packets values(?,?,?)");
        st.setInt(1, packet.getId());
        st.setString(2, packet.getContract());
        st.setDate(3, java.sql.Date.valueOf(packet.getLastDay()));
        st.execute();
    }
    //method for saving appointment(using first appointment) into database
    public static void saveAppointment(Appointment appointment) throws SQLException{

        LocalDate[] ld = new LocalDate[12];
        local = LocalDate.of(1950, Month.JANUARY, 1);
        for(int i=0;i<ld.length;i++){
            ld[i] = local;
        }
        appointment.setUsedAppointments(ld);
        try {
            conn = getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DataSave.class.getName()).log(Level.SEVERE, null, ex);
        }
                        ResultSet rs = conn.createStatement().executeQuery("select id from users");
        while(rs.next()){
            appointment.setId(rs.getInt("id"));
        }
        st = conn.prepareStatement("insert into appointments values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        st.setInt(1, appointment.getId());
        for(int i=2;i<14;i++)
        st.setDate(i, java.sql.Date.valueOf(local));
        st.execute(); 
    }
    //method for using appointment
    public static void useAppointment(Appointment appointment) throws SQLException {
        orm = new ORM();
        if(Search.hasExpired(appointment.getId())==false){
            
        try {
            conn = getConnection();
            local = LocalDate.of(1950, Month.JANUARY, 1);
        } catch (SQLException ex) {
            Logger.getLogger(DataSave.class.getName()).log(Level.SEVERE, null, ex);
        }if(Search.isUnlimited(appointment.getId())==false){
        if(Search.todayApp(appointment.getId())==false){
        
        PreparedStatement st = null;
        LocalDate[] ld = orm.getAppointment(appointment.getId());
        boolean abc = false;
        
        int b=0;
       for(int i = 0;i<ld.length;i++){
       if(ld[i].isEqual(local)&&b<1&&abc==false){
       ld[i] = LocalDate.now();
       b++;}
       }
       if(b<1){
           alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("Greska");
           alert.setHeaderText("");
           alert.setContentText("Klijent je iskoristio sve termine...");
           alert.show();
           
       }else{
       appointment.setUsedAppointments(ld);
       st = conn.prepareStatement("update appointments set I=?, II=?, III=?, IV=?, V=?, VI=?, VII=?, VIII=?, IX=?, X=?, XI=?, XII=? where id=?");
       for(int a=1,c=0;c<appointment.getUsedAppointments().length;a++,c++){
       st.setDate(a, java.sql.Date.valueOf(appointment.getUsedAppointments()[c]));}
       st.setInt(13, appointment.getId());
       st.execute();
            
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potrvrda");
            alert.setContentText("Uspešno iskorišćen termin");
            alert.show();}
        }else{alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacija");
            alert.setContentText("Clan je vec iskoristio jedan termin danas");
            alert.show();}
        }else {alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacija");
            alert.setContentText("Clan ima neograniceno tarifu...");
            alert.show();
        }
        }else {alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Istekao mesec");
        alert.setContentText("Datom klijentu je istekla mesecna clanarina");
        alert.show();
            }            }
    //method for deleting users from database
    public static void deleteUser(User user) throws SQLException{
        conn = getConnection();
        st = conn.prepareCall("delete from users, packets, appointments using users, packets, appointments where users.id = packets.id and users.id = appointments.id and users.id = ?");
        st.setInt(1, user.getId());
        st.execute();
    }
    //method for updating user in database
    public static void updateUser(User user) throws SQLException{
         conn = getConnection();
         st  = conn.prepareCall("update users set name=?, surname=? where id=?");
         st.setString(1, user.getName());
         st.setString(2, user.getSurname());
         st.setInt(3, user.getId());
         st.execute();
    }
    //method for updating packet in database
    public static void updatePacket(Packet packet) throws SQLException{
        conn = getConnection();
        st  = conn.prepareStatement("update packets set packet=?, last_day=? where id=?");
        st.setString(1, packet.getContract());
        st.setDate(2, java.sql.Date.valueOf(packet.getLastDay()));
        st.setInt(3, packet.getId());
        st.execute();
    }
    //method for updating appointments in database
    public static void updateAppointments(Appointment appointment) throws SQLException{
        conn = getConnection();     
        st = conn.prepareStatement("update appointments set I=?, II=?, III=?, IV=?, V=?, VI=?, VII=?, VIII=?, IX=?, X=?, XI=?, XII=? where id=?");
        st.setInt(13, appointment.getId());
        LocalDate[] ld = new LocalDate[12];
        for(int i=0;i<12;i++)
        ld[i] = local;
        appointment.setUsedAppointments(ld);
        for(int i=1,a=0;i<13;i++,a++)
        st.setDate(i, java.sql.Date.valueOf(appointment.getUsedAppointments()[a]));
        st.execute(); 
    }
    //method for renewing appointments
    public static void renewAppointments(Appointment appointment) throws SQLException{
        local = LocalDate.of(1950, 1, 1);
        conn = getConnection();     
        st = conn.prepareStatement("update appointments set I=?, II=?, III=?, IV=?, V=?, VI=?, VII=?, VIII=?, IX=?, X=?, XI=?, XII=? where id=?");
        st.setInt(13, appointment.getId());
        LocalDate[] ld = new LocalDate[12];
        for(int i=0;i<12;i++)
        ld[i] = local;
        appointment.setUsedAppointments(ld);
        for(int i=1,a=0;i<13;i++,a++)
        st.setDate(i, java.sql.Date.valueOf(local));
        st.execute();  
    }
        }
    

