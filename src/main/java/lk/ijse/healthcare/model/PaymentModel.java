package lk.ijse.healthcare.model;

import lk.ijse.healthcare.db.DbConnection;
import lk.ijse.healthcare.dto.Customer;
import lk.ijse.healthcare.dto.Payment;
import lk.ijse.healthcare.util.CrudUtill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PaymentModel {
    private final static String URL = "jdbc:mysql://localhost:3306/pharmacy";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "root");
    }
    public static boolean savePayment(String paymentId, String paymentType,String date, double total) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO payment(pay_Id,type,date,total) VALUES(?, ?, ?, ?)";

        return CrudUtill.execute(sql,paymentId,paymentType,date,total);
    }

    public static String generateNextPaymentId() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql = "SELECT pay_Id FROM payment ORDER BY pay_Id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitPaymentId(resultSet.getString(1));
        }
        return splitPaymentId(null);
    }

    public static String splitPaymentId(String currentPaymentId) {
        if(currentPaymentId != null) {
            String[] strings = currentPaymentId.split("P-0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "P-00"+id;
        }
        return "P-001";
    }
    public static List<Payment> getAll() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM payment";

        List<Payment> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Payment(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)

            ));
        }
        return data;
    }
    public static boolean update(Payment payment) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE payment SET type = ?, date = ?, total = ? WHERE pay_Id = ?";
        return CrudUtill.execute(sql,payment.getPaymentType(),payment.getDate(),payment.getTotal(),payment.getPaymentId());
    }
    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM payment WHERE pay_Id= ?";
        return CrudUtill.execute(sql, id);
    }
}
