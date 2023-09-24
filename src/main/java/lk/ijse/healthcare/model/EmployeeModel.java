package lk.ijse.healthcare.model;

import lk.ijse.healthcare.db.DbConnection;
import lk.ijse.healthcare.dto.Employee;
import lk.ijse.healthcare.util.CrudUtill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    private static CrudUtill CrudUtil;

    public static boolean saveEmployee(String employeeId, String name, int salary, String contact, String address) throws SQLException, SQLException, ClassNotFoundException {
        String sql = "INSERT INTO employee(Employee_id,name,salary,phone_number,address) VALUES (?,?,?,?,?)";
        return CrudUtill.execute(sql,employeeId,name,salary,contact,address);
    }

    public static String genarateNextEmployeeId() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT Employee_id FROM employee ORDER BY Employee_id DESC LIMIT 1";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()){
            return splitEmployeeId(resultSet.getString(1));
        }
        return splitEmployeeId(null);
    }

    public static String splitEmployeeId(String currentEmployeeId) {
        if(currentEmployeeId != null){
            String[] strings = currentEmployeeId.split("E0");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "E00"+id;
        }
        return "E001";
    }
    public static List<Employee> getAll() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM employee";

        List<Employee> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }
    public static boolean updateEmployee(Employee employee) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE employee SET name = ?,salary = ?, phone_number = ?,address= ? WHERE Employee_id = ?";
        return CrudUtill.execute(sql,employee.getName(),employee.getSalary(),employee.getContact(),employee.getAddress(),employee.getEmployeeId());
    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM employee WHERE Employee_id = ?";
        return CrudUtil. execute(sql, id);
    }
}
