package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PacketProp {
    private IntegerProperty id;
    private StringProperty last_day;
    private StringProperty tarifa;
    
    public PacketProp(Packet packet){
        id = new SimpleIntegerProperty(packet.getId());
        last_day = new SimpleStringProperty(packet.getLastDay().toString());
        tarifa = new SimpleStringProperty(packet.getContract());
    }
    public Integer getId() {
        return id.getValue();
    }

    public void setId(IntegerProperty id) {
        this.id =  id;
    }
    public String getLast_day() {
        return last_day.getValue();
    }
    public void setLast_day(StringProperty last_day) {
        this.last_day = last_day;
    }
    public String getTarifa() {
        return tarifa.getValue();
    }
    public void setTarifa(StringProperty tarifa) {
        this.tarifa = tarifa;
    }
}
