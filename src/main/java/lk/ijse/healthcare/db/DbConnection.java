package lk.ijse.healthcare.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnection {

    private static DbConnection dbConnection;
    private static Connection connection;

    private DbConnection() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pharmacy", "root", "1234");
    }


    public static DbConnection getInstance() throws SQLException, ClassNotFoundException {
        if (dbConnection == null) {
            dbConnection = new DbConnection();
        }
        return dbConnection;
    }
    public Connection getConnection(){
        return connection;
    }



    public static ResultSet search(String query) throws SQLException, ClassNotFoundException {
        if (dbConnection==null){
            new DbConnection();
        }
        return connection.createStatement().executeQuery(query);

    }
    public static void oid (String query) throws SQLException, ClassNotFoundException {
        if (dbConnection==null){
            new DbConnection();
        }
        connection.createStatement().executeUpdate(query);
    }

    /*    public static ResultSet search(String query)throws Exception{
                if (dbConnection == null) {
                        DbConnection();
                }
                return connection.createStatement().executeQuery(query);
        }
        public static void iud (String query)throws Exception {
                if (dbConnection == null) {
                        DbConnection();
                }
                connection.createStatement().executeUpdate(query);
        }*/
}
