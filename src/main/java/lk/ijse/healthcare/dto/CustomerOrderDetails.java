package lk.ijse.healthcare.dto;

public class CustomerOrderDetails {

    private String orderId;
    private String cusId;
    private String medicineId;
    private int qty;
    private int total;




    public CustomerOrderDetails(String orderId, String cusId, String medicineId, int qty, int total) {
        this.orderId = orderId;
        this.cusId = cusId;
        this.medicineId = medicineId;
        this.qty = qty;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
