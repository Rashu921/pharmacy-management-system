package lk.ijse.healthcare.dto;

public class Customer {
    private String custId;
    private String custName;
    private String custContact;
    private String custJoinDate;
    private String custAddress;

    public Customer(String custId, String custName, String custContact, String JoinDate, String address) {
        this.custId = custId;
        this.custName = custName;
        this.custContact = custContact;
        this.custJoinDate = JoinDate;
        this.custAddress = address;
    }

    public Customer() {
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustContact() {
        return custContact;
    }

    public void setCustContact(String custContact) {
        this.custContact = custContact;
    }

    public String getCustJoinDate() {
        return custJoinDate;
    }

    public void setCustJoinDate(String custJoinDate) {
        this.custJoinDate = custJoinDate;
    }
    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }
}
