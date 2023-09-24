package lk.ijse.healthcare.model;

import javafx.collections.ObservableList;
import lk.ijse.healthcare.db.DbConnection;
import lk.ijse.healthcare.dto.Customer;
import lk.ijse.healthcare.dto.SupplierOrder;
import lk.ijse.healthcare.dto.SuppliesOrderDetails;
import lk.ijse.healthcare.tm.SuppliesOrderDetailsTm;
import lk.ijse.healthcare.util.CrudUtill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SuppliesOrderDetailsModel {
    private final static String URL = "jdbc:mysql://localhost:3306/pharmacy";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "root");
    }
    public static boolean saveSupOrDetails(String SupId, String supplierId, String medicineId, int quantity, int total) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO supplier_order VALUES(?, ?, ?, ?,?)";

        return CrudUtill.execute(sql,SupId,supplierId,medicineId,quantity,total);
    }

    public static String generateNextSupId() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql = "SELECT supp_id FROM supplier_order ORDER BY supp_id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitSupId(resultSet.getString(1));
        }
        return splitSupId(null);
    }

    public static String splitSupId(String currentSupId) {
        if(currentSupId != null) {
            String[] strings = currentSupId.split("S-0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "S-00"+id;
        }
        return "S-001";
    }
    public static List<SuppliesOrderDetails> getAll() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier_order";

        List<SuppliesOrderDetails> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new SuppliesOrderDetails(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5)

            ));
        }
        return data;
    }
    public static boolean update(SuppliesOrderDetails suppliesOrderDetails) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE supplier_order SET supplier_Id  = ?,  med_Id = ?, qty = ?, total = ? WHERE supp_id = ?";
        return CrudUtill.execute(sql,suppliesOrderDetails.getSupId(),suppliesOrderDetails.getSupplierId(),suppliesOrderDetails.getMedicineId(),suppliesOrderDetails.getQuantity(),suppliesOrderDetails.getTotal());
    }

    public static boolean saveRecords(ObservableList<SuppliesOrderDetailsTm> ob,String orderId) throws SQLException, ClassNotFoundException {
        for (SuppliesOrderDetailsTm obj : ob){
            if(!saveSupOrDetails(obj.getSupId(), obj.getSupplierId(), obj.getMedicineId(), obj.getQuantity(), obj.getTotal() )){
                return false;
            }
        }
        return true;
    }

    /*
    public static boolean placeOrder(ObservableList<SuppliesOrderDetailsTm> items, SupplierOrder supplierOrder) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        con.setAutoCommit(false);
        try {
            boolean isOrderPlaced = SupplierOrderModel.addRecord(supplierOrder);
            if(isOrderPlaced){
                boolean isAllAdded = saveRecords(items,supplierOrder.getOrderId());
                if(isAllAdded){
                    boolean isAllUpdated = MedicineModel.increaseQty(items);
                    if(isAllUpdated){
                        con.commit();
                        return true;
                    }
                }
            }
            con.rollback();
            return false;
        }catch (SQLException e){
            con.rollback();
            throw e;
        }finally {
            con.setAutoCommit(true);
        }
    } */
}
