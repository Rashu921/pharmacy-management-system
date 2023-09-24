package lk.ijse.healthcare.tm;

public class PaymentTm {
    private String paymentId;
    private String paymentType;
    private String date;
    private double total;

    public PaymentTm() {
    }

    public PaymentTm(String paymentId, String paymentType, String date, double total) {
        this.paymentId = paymentId;
        this.paymentType = paymentType;
        this.date = date;
        this.total = total;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}


