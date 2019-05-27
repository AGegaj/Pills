package model;

public class ReminderRegister {

    private Integer hour;

    private Integer minutes;

    private String quantity;

    public ReminderRegister(){}

    public ReminderRegister(Integer hour, Integer minutes, String quantity) {
        this.hour = hour;
        this.minutes = minutes;
        this.quantity = quantity;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
