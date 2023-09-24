package lk.ijse.healthcare.tm;

public class EmployeeTm {
    private String employeeId;
    private String name;
    private int salary;
    private String contact;
    private String address;

    public EmployeeTm() {
    }

    public EmployeeTm(String employeeId, String name, int salary, String contact, String address) {
        this.employeeId = employeeId;
        this.name = name;
        this.salary = salary;
        this.contact = contact;
        this.address = address;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
