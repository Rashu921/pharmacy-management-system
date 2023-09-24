package lk.ijse.healthcare.dto;

public class SupplierOrder {
    private String orderId;
    private String supplierId;

    public SupplierOrder(String orderId, String supplierId) {
        this.orderId = orderId;
        this.supplierId = supplierId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}
