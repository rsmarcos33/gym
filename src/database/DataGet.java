package database;

import model.Packet;
import model.PacketProp;
import model.User;
import model.UserProp;
import path.CFG;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class DataGet{
    static LocalDate local = LocalDate.of(1950, Month.JANUARY, 1); 
    static Connection conn;
    //this method is used for quicker connection to database
        private static Connection getConnection() throws SQLException{
        return (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/"+CFG.database, "root", "");
    }
    //this method is used for getting user from database
   public static User getUser(int id) throws SQLException{
       User user = new User();
       user.setId(id);
       conn = getConnection();
       ResultSet rs = conn.createStatement().executeQuery("select * from users where id="+id);
       
       rs.next();
       
       user.setName(rs.getString("name"));
       user.setSurname(rs.getString("surname"));
        return user;
   }
    //this method is used for getting packet from database   
   public static Packet getPacket(int id) throws SQLException{
       Packet packet = new Packet();
       packet.setId(id);
       conn = getConnection();
       ResultSet rs = conn.createStatement().executeQuery("select * from packets where id="+id);
       rs.next();
       packet.setContract(rs.getString("packet"));
       packet.setLastDay(rs.getDate("last_day").toLocalDate());
        return packet;
   }
    //this method is used for getting appointments from database
   public static LocalDate[] getAppointments(int id) throws SQLException{
       String[] appointments = new String[]{"I","II","III","IV","V","VI","VII","VIII","IX","X","XI","XII"};
       conn = getConnection();
       ResultSet rs = conn.createStatement().executeQuery("select * from appointments where id="+id);
       rs.next();
       LocalDate[] ld  = new LocalDate[12];
       for(int i = 0;i<ld.length;i++){
       try{
       ld[i] = rs.getDate(appointments[i]).toLocalDate();
       } catch(Exception ex){ 
       //if the localdate value is null in database, we are initializing it to the value of local 
           ld[i] = local;
       }             
       }
 return ld;
}
       //this method is used for getting all users from database
       public static List getUsers() throws SQLException{
        User user = new User();
        List<UserProp> users = new ArrayList<>();
        conn = getConnection();
        ResultSet rs = conn.createStatement().executeQuery("select * from users");
        while(rs.next()){
          user.setId(rs.getInt("id"));
          user.setName(rs.getString("name"));
          user.setSurname(rs.getString("surname"));
          users.add(new UserProp(user));
        }
        return users;
        
    }
       //this method is used for getting all packets from database
              public static List getPackets() throws SQLException{
        Packet packet = new Packet();
        List<PacketProp> packets = new ArrayList<>();
        conn = getConnection();
        ResultSet rs = conn.createStatement().executeQuery("select * from packets");
        while(rs.next()){
          packet.setId(rs.getInt("id"));
          packet.setContract(rs.getString("packet"));
          packet.setLastDay(rs.getDate("last_day").toLocalDate());
          packets.add(new PacketProp(packet));
        }
        return packets;
        
    }

}
