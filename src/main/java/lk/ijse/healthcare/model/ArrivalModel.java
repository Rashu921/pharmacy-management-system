package lk.ijse.healthcare.model;

import lk.ijse.healthcare.db.DbConnection;
import lk.ijse.healthcare.dto.Arrival;
import lk.ijse.healthcare.dto.Customer;
import lk.ijse.healthcare.util.CrudUtill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ArrivalModel {
    private final static String URL = "jdbc:mysql://localhost:3306/pharmacy";
    private final static Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "root");
    }
    public static boolean saveArrivel(String EmpId, String Time,String Date) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO arrival(Employee_Id ,time,date) VALUES(?, ?, ?)";

        return CrudUtill.execute(sql,EmpId,Time,Date);
    }

    public static String generateNextEmpId() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql = "SELECT Employee_Id FROM arrival ORDER BY Employee_Id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitEmpId(resultSet.getString(1));
        }
        return splitEmpId(null);
    }

    public static String splitEmpId(String currentEmpId) {
        if(currentEmpId != null) {
            String[] strings = currentEmpId.split("E0");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "E00"+id;
        }
        return "E001";
    }
    public static List<Arrival> getAll() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM arrival";

        List<Arrival> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Arrival(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)

            ));
        }
        return data;
    }
    public static boolean update(Arrival arrival) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE arrival SET time = ?, date = ? WHERE Employee_Id = ?";
        return CrudUtill.execute(sql,arrival.getTime(),arrival.getDate(),arrival.getEmpId());
    }
    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM arrival WHERE Employee_Id = ?";
        return CrudUtill.execute(sql, id);
    }
}
