package lk.ijse.healthcare.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import lk.ijse.healthcare.db.DbConnection;
import lk.ijse.healthcare.dto.Customer;
import lk.ijse.healthcare.util.CrudUtill;

public class CustomerModel {
    private final static String URL = "jdbc:mysql://localhost:3306/pharmacy";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "root");
    }
    public static boolean saveCustomer(String cust_Id, String name,String contact_NO, String join_date,String address) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO customer(cust_Id,name,contact_NO,join_date,address) VALUES(?, ?, ?, ?,?)";

        return CrudUtill.execute(sql,cust_Id,name,contact_NO,join_date,address);
    }

    public static String generateNextCustomerId() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql = "SELECT cust_Id FROM customer ORDER BY cust_Id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitCustomerId(resultSet.getString(1));
        }
        return splitCustomerId(null);
    }

    public static String splitCustomerId(String currentCustomerId) {
        if(currentCustomerId != null) {
            String[] strings = currentCustomerId.split("C-0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "C-00"+id;
        }
        return "C-001";
    }
    public static List<Customer> getAll() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM customer";

        List<Customer> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }
    public static boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE customer SET name = ?, contact_NO = ?, join_date = ?, address = ? WHERE cust_Id = ?";
        return CrudUtill.execute(sql,customer.getCustName(),customer.getCustContact(),customer.getCustJoinDate(),customer.getCustAddress(),customer.getCustId());
    }
    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM customer WHERE cust_Id = ?";
        return CrudUtill.execute(sql, id);
    }

}
