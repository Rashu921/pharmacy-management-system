package lk.ijse.healthcare.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import lk.ijse.healthcare.db.DbConnection;
import lk.ijse.healthcare.dto.User;
import lk.ijse.healthcare.model.MedicineModel;
import lk.ijse.healthcare.model.UserModel;
import lk.ijse.healthcare.tm.UserTm;
import lk.ijse.healthcare.util.ValidationUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserFormContrller implements Initializable{
    public TableView tblUserManagement;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colPassword;
    public TableColumn colEmployeeId;
    public JFXTextField txtUserId;
    public JFXTextField txtName;
    public JFXTextField txtPassword;
    public JFXTextField txtEmpId;


        public void initialize(URL url, ResourceBundle resourceBundle) {
            setCellValueFactory();
            genarateNextUserId();
            getAllUser();
            setTextFieldValidation();
        }

        public User makeUserObject() {
            String UserId = txtUserId.getText();
            String UserName = txtName.getText();
            String Password = txtPassword.getText();
            String EmployeeId = txtEmpId.getText();


            return new User(UserId, UserName, Password, EmployeeId);
        }

        public void setTextFieldValidation(){
        ValidationUtil.setFocus(txtUserId,ValidationUtil.userIdPattern);
        ValidationUtil.setFocus( txtName,ValidationUtil.namePattern);
        ValidationUtil.setFocus( txtPassword,ValidationUtil.passwordPattern);
        ValidationUtil.setFocus( txtEmpId,ValidationUtil.employeeIdPattern);



    }
    private boolean isValidated(){
        if(txtUserId.getFocusColor().equals(Paint.valueOf("red")) ||
                txtName.getFocusColor().equals(Paint.valueOf("red"))||
                txtPassword.getFocusColor().equals(Paint.valueOf("red"))||
                txtEmpId.getFocusColor().equals(Paint.valueOf("red")))


        {
            return false;
        }else if(txtUserId.getText().equals("") ||
                txtName.getText().equals("")||
                txtPassword.getText().equals("")||
                txtEmpId.getText().equals(""))

        {
            return false;
        }
        return true;
    }

    public void btnUserSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            if(!isValidated()){
                new Alert(Alert.AlertType.WARNING,
                        "Fill All Fields Correctly !",
                        ButtonType.OK
                ).show();
                return;
            }
            User U = makeUserObject();
            boolean isSave = UserModel.saveUser(U.getUserId(), U.getUserName(), U.getPassword(), U.getEmployeeId());

            if (isSave) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully...").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "something happened! please try again...").show();
            }
            txtName.clear();
            txtPassword.clear();
            txtEmpId.clear();

            genarateNextUserId();
            setCellValueFactory();
            getAllUser();
        }

        public void genarateNextUserId() {
            try {
                String nextUser = UserModel.genarateNextUser();
                txtUserId.setText(nextUser);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        void setCellValueFactory() {
            colId.setCellValueFactory(new PropertyValueFactory<>("UserId"));
            colName.setCellValueFactory(new PropertyValueFactory<>("UserName"));
            colPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
            colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));

        }

        void getAllUser() {
            try {
                ObservableList<UserTm> obList = FXCollections.observableArrayList();
                List<User> UserList = UserModel.getAll();

                for (User user : UserList) {
                    obList.add(new UserTm(
                            user.getUserId(),
                            user.getUserName(),
                            user.getPassword(),
                            user.getEmployeeId()


                    ));
                }
                tblUserManagement.setItems(obList);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Query error!").show();
            }
        }


        public void btnUserDeleteOnAction(ActionEvent actionEvent) {
            String id = txtUserId.getText();

            try {
                boolean isDelete = UserModel.delete(id);
                if (isDelete) {
                    new Alert(Alert.AlertType.CONFIRMATION, " Deleted Successfully").show();
                    getAllUser();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Delete Error").show();
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            genarateNextUserId();
        }

        public void btnUserUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
            User U = makeUserObject();

            boolean isUpdated = UserModel.updateUser(U);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Successfully Updated!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
            }
            txtName.clear();
            txtPassword.clear();

            genarateNextUserId();
            setCellValueFactory();
            getAllUser();
        }

    public void btnUsprint(ActionEvent actionEvent) {
        new Thread() {
            @Override
            public void run() {
                try {
                    JasperDesign load = JRXmlLoader.load(new File("E:\\D\\healthcare new\\src\\main\\resources\\js-reports\\user_all_form.jrxml"));

                    JasperReport js = JasperCompileManager.compileReport(load);

                    JasperPrint jp = JasperFillManager.fillReport(js, null, DbConnection.getInstance().getConnection());

                    JasperViewer viewer = new JasperViewer(jp , false);
                    viewer.show();

                } catch (JRException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                this.stop();
            }
        }.start();
    }
}