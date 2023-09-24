package lk.ijse.healthcare.model;

import lk.ijse.healthcare.dto.SupplierOrder;
import lk.ijse.healthcare.util.CrudUtill;

import java.sql.SQLException;

public class SupplierOrderModel {
    public static boolean addRecord(SupplierOrder ob) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("insert into supplier_order values(?,?)"
        ,ob.getOrderId(),ob.getSupplierId());
    }
}

