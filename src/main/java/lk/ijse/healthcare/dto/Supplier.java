package lk.ijse.healthcare.dto;


    public class Supplier {
        private String SupplierId;
        private String SupplierName;
        private String SupplierNic;
        private String SupplierContact;
        private String SupplierAddress;

        public Supplier() {
        }

        public Supplier(String supplierId, String supplierName,String supplierNic, String supplierContact, String supplierAddress) {
            SupplierId = supplierId;
            SupplierName = supplierName;
            SupplierNic =  supplierNic;
            SupplierContact = supplierContact;
            SupplierAddress = supplierAddress;
        }

        public String getSupplierId() {
            return SupplierId;
        }

        public void setSupplierId(String supplierId) {
            SupplierId = supplierId;
        }

        public String getSupplierName() {
            return SupplierName;
        }

        public void setSupplierName(String supplierName) {
            SupplierName = supplierName;
        }

        public String getSupplierContact() {
            return SupplierContact;
        }

        public void setSupplierContact(String supplierContact) {
            SupplierContact = supplierContact;
        }

        public String getSupplierAddress() {
            return SupplierAddress;
        }

        public void setSupplierAddress(String supplierAddress) {
            SupplierAddress = supplierAddress;
        }
        public String getSupplierNic() {
            return SupplierNic;
        }

        public void setSupplierNic(String supplierNic) {
            SupplierNic = supplierNic;
        }

    }
