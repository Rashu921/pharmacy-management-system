package lk.ijse.healthcare.dto;

public class SuppliesOrderDetails {

    private String SupId;
    private String supplierId;
    private String medicineId;
    private int quantity;
    private int total;

    public SuppliesOrderDetails() {
    }

    public SuppliesOrderDetails(String SupId, String supplierId, String medicineId, int quantity, int total) {
        this.SupId = SupId;
        this.supplierId = supplierId;
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.total = total;
    }

    public String getSupId() {
        return SupId;
    }

    public void setSupId(String supId) {
        SupId = supId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
