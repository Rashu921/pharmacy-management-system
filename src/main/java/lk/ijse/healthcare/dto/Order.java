package lk.ijse.healthcare.dto;

public class Order {
    private String orderId;
    private String custId;
    private int total;

    public Order(String text, String custId, String medId, int i, int parseInt) {
    }

    public Order(String orderId, String medId, int total) {
        this.orderId = orderId;
        this.custId = medId;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
