package lk.ijse.healthcare.dto;

public class Arrival {
    private String empId;
    private String time;
    private String Date;

    public Arrival() {
    }

    public Arrival(String empId, String time, String date) {
        this.empId = empId;
        this.time = time;
        Date = date;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
