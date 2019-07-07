package checkup;

import mapping.ORM;
import model.Packet;
import model.User;
import path.CFG;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Search {
    //this method is used for quicker accessing database in the further parts of class
        private static Connection getConnection() throws SQLException{
        return (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/"+CFG.database, "root", "");
    }
    //this method is used for searching database by name
        public static List searchName(String name) throws SQLException{
        List usersArray = new ArrayList();
        User user = new User();
        Connection conn = getConnection();
        ResultSet rs = conn.createStatement().executeQuery("select * from users where name = '"+name+"'");
        while(rs.next()){
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            usersArray.add(user);
        }
        return usersArray;
        
    }
    //this method is used for searching database by prename
        public static List searchPrename(String surname) throws SQLException{
        List usersArray = new ArrayList();
        User user = new User();
        Connection conn = getConnection();
        ResultSet rs = conn.createStatement().executeQuery("select * from users where surname = '"+surname+"'");
        while(rs.next()){
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            usersArray.add(user);
        }
        return usersArray;
        
    }
    //this method is used for searching database by id
        public static List search(int id) throws SQLException{
        List usersArray = new ArrayList();
        User user = new User();
        Connection conn = getConnection();
        ResultSet rs = conn.createStatement().executeQuery("select * from users where id="+id);
        while(rs.next()){
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setSurname(rs.getString("surname"));
            usersArray.add(user);
        }
        return usersArray;
        
    }
    //this method is used to check if the user have an unlimited packet 
        public static boolean isUnlimited(int id) throws SQLException{
        ORM orm = new ORM();
        boolean unlimited = true;
        Packet packet = null;
        packet =  orm.getPacket(id);
        String contract = packet.getContract();
        switch(contract){
            case "12x":unlimited = false;
                break;
            case "N":unlimited = true;
                break;
        }
        return unlimited;
        
    }
    //this method is to check if the packet of member has expired
        public static boolean hasExpired(int id) throws SQLException{
      ORM orm = new ORM();
      boolean expired = true;
      Packet packet = null;
      packet = orm.getPacket(id);
      LocalDate last_day = packet.getLastDay();
      if(LocalDate.now().isBefore(last_day)){
       expired = false;   
      }
            return expired;
      
    }
    //this method is used to chech if the packet of member is expiring today 
        public static boolean todayApp(int id) throws SQLException{
                  ORM orm = new ORM();
                  LocalDate[] ld = orm.getAppointment(id);
                  boolean abc = false;
                          for(int i=0;i<ld.length;i++){
                              if(ld[i].equals(LocalDate.now())){
                abc=true;}
                  
            }
        return abc;
                  
              }
}
