package lk.ijse.healthcare.model;

import lk.ijse.healthcare.db.DbConnection;
import lk.ijse.healthcare.dto.Employee;
import lk.ijse.healthcare.dto.User;
import lk.ijse.healthcare.util.CrudUtill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
    public static boolean saveUser(String UserId,String UserName,String UserPassword,String EmployeeId) throws SQLException, SQLException, ClassNotFoundException, SQLException {
        String sql = "INSERT INTO user(user_Id,username,password, Employee_Id) VALUES (?,?,?,?)";
        return CrudUtill.execute(sql,UserId,UserName,UserPassword,EmployeeId );
    }

    public static String genarateNextUser() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT user_Id FROM user ORDER BY user_Id  DESC LIMIT 1";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()){
            return splitUser(resultSet.getString(1));
        }
        return splitUser(null);
    }

    public static String splitUser(String currentUser) {
        if(currentUser != null){
            String[] strings = currentUser.split("U-0");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "U-00"+id;
        }
        return "U-001";
    }
    public static List<User> getAll() throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user ";

        List<User> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

            ));

        }
        return data;
    }
    public static boolean updateUser(User user) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE user SET username = ?,password = ?,Employee_id = ? WHERE user_Id = ?";
        return CrudUtill.execute(sql,user.getUserName(),user.getPassword(),user.getEmployeeId(),user.getUserId());
    }
    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM user WHERE = user_Id ?";
        return CrudUtill.execute(sql, id);
    }
}
