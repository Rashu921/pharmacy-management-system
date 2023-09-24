package lk.ijse.healthcare.model;

import lk.ijse.healthcare.dto.Order;
import lk.ijse.healthcare.util.CrudUtill;

import java.sql.SQLException;

public class OrderModel {
    public static boolean addOrder(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtill.execute("insert into orders values(?,?,?)",order.getOrderId(),
                order.getCustId(),order.getTotal());
    }

}
