package lk.ijse.healthcare.model;

import javafx.collections.ObservableList;
import lk.ijse.healthcare.db.DbConnection;
import lk.ijse.healthcare.dto.Medicine;
import lk.ijse.healthcare.tm.CustomerOrderDetailsTm;
import lk.ijse.healthcare.tm.SuppliesOrderDetailsTm;
import lk.ijse.healthcare.util.CrudUtill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MedicineModel {

    private final static String URL = "jdbc:mysql://localhost:3306/pharmacy";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "root");
    }

    public static boolean saveMedicine(String medId, String medName, String medType, String medStock, int medPrice) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO medicine(med_Id,name,type,qty_on_stock,price) VALUES(?, ?, ?, ?,?)";

        return CrudUtill.execute(sql, medId, medName, medType, medStock, medPrice);
    }

    public static String generateNextMedicineId() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql = "SELECT med_Id FROM medicine ORDER BY med_Id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if (resultSet.next()) {
            return splitMedicineId(resultSet.getString(1));
        }
        return splitMedicineId(null);
    }

    public static String splitMedicineId(String currentMedicineId) {
        if (currentMedicineId != null) {
            String[] strings = currentMedicineId.split("M-0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "M-00" + id;
        }
        return "M-001";
    }

    public static List<Medicine> getAll() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM medicine";

        List<Medicine> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Medicine(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5)
            ));
        }
        return data;
    }

    public static boolean updateMedicine(Medicine medicine) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE medicine SET name = ?, type = ?, qty_on_stock = ?,price = ? WHERE med_Id = ?";
        return CrudUtill.execute(sql, medicine.getMedName(), medicine.getMedType(), medicine.getMedStock(), medicine.getMedPrice(), medicine.getMedId());
    }

    public static boolean decreaseQty(ObservableList<CustomerOrderDetailsTm> items) throws SQLException, ClassNotFoundException {
        for (CustomerOrderDetailsTm ob : items) {
            boolean isQtyUpdate = updateQty(ob);
            if (!isQtyUpdate) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(CustomerOrderDetailsTm item) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("UPDATE medicine SET qty_on_stock = qty_on_stock - ? WHERE med_id = ?", item.getQty(), item.getMedicineId());
    }

    public static boolean increaseQty(ObservableList<SuppliesOrderDetailsTm> items) throws SQLException, ClassNotFoundException {
        for (SuppliesOrderDetailsTm ob : items) {
            boolean isQtyUpdate = updateQty(ob);
            if (!isQtyUpdate) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(SuppliesOrderDetailsTm item) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("UPDATE medicine SET qty_on_stock = qty_on_stock + ? WHERE med_id = ?", item.getQuantity(), item.getMedicineId());
    }
    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM medicine WHERE med_Id= ?";
        return CrudUtill.execute(sql, id);
    }
}
