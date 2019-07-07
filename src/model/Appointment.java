package model;

import java.time.LocalDate;

public class Appointment {
    private int id;
    private LocalDate[] usedAppointments;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public LocalDate[] getUsedAppointments() {
        return usedAppointments;
    }
    public void setUsedAppointments(LocalDate[] usedAppointments) {
        this.usedAppointments = usedAppointments;
    }
}
