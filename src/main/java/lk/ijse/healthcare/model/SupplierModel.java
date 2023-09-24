package lk.ijse.healthcare.model;

import lk.ijse.healthcare.db.DbConnection;
import lk.ijse.healthcare.dto.Supplier;
import lk.ijse.healthcare.util.CrudUtill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public static boolean saveSupplier(String Supplier_id,String name,String Nic,String Contact,String address) throws SQLException, SQLException, ClassNotFoundException, SQLException {
        String sql = "INSERT INTO supplier(supplier_id,name,nic,address,contact_NO) VALUES (?,?,?,?,?)";
        return CrudUtill.execute(sql,Supplier_id,name,Nic,Contact,address);
    }

    public static String genarateNextSupplierId() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT Supplier_id FROM supplier ORDER BY Supplier_id DESC LIMIT 1";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()){
            return splitSupplierId(resultSet.getString(1));
        }
        return splitSupplierId(null);
    }

    public static String splitSupplierId(String currentSupplierId) {
        if(currentSupplierId != null){
            String[] strings = currentSupplierId.split("S0");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "S00"+id;
        }
        return "S001";
    }
    public static List<Supplier> getAll() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier";

        List<Supplier> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }
    public static boolean updateSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE supplier SET name = ?,nic = ?,contact_NO = ?,address= ? WHERE supplier_id = ?";
        return CrudUtill.execute(sql,supplier.getSupplierName(),supplier.getSupplierNic(),supplier.getSupplierContact(),supplier.getSupplierAddress(),supplier.getSupplierId());
    }
    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM supplier WHERE supplier_id= ?";
        return CrudUtill.execute(sql, id);
    }
}
