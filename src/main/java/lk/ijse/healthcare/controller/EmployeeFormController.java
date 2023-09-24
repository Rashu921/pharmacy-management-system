package lk.ijse.healthcare.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.healthcare.dto.Employee;
import lk.ijse.healthcare.model.EmployeeModel;
import lk.ijse.healthcare.tm.EmployeeTm;
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

public class EmployeeFormController implements Initializable {


    public TableView tblEmployees;
    public TableColumn colEmpId;
    public TableColumn colEmpName;
    public TableColumn colEmpSalary;
    public TableColumn colEmpContact;
    public TableColumn colEmpAddress;
    public JFXButton btnSave;
    public JFXTextField txtEmpName;
    public JFXTextField txtEmpAddress;
    public JFXTextField txtEmployeeId;
    public JFXTextField txtEmpSalary;
    public JFXTextField txtEmpContact;

    public Employee makeEmployeeObject() {
        String empId = txtEmployeeId.getText();
        String empName = txtEmpName.getText();
        int empSalary = Integer.parseInt(txtEmpSalary.getText());
        String empContact = txtEmpContact.getText();
        String empAddress = txtEmpAddress.getText();

        return new Employee(empId, empName, empSalary, empContact, empAddress);
    }

    public void btnSearch(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        genarateNextEmployeeId();
        getAllEmployees();
        setTextFieldValidation();
    }
    public void setTextFieldValidation(){
        ValidationUtil.setFocus(txtEmployeeId,ValidationUtil.employeeIdPattern);
        ValidationUtil.setFocus( txtEmpName,ValidationUtil.namePattern);
        ValidationUtil.setFocus( txtEmpContact,ValidationUtil.contactPattern);
        ValidationUtil.setFocus( txtEmpSalary,ValidationUtil.SallaryPAttern);
        ValidationUtil.setFocus( txtEmpAddress,ValidationUtil.AddressPAttern);


    }
    private boolean isValidated(){
        if(txtEmployeeId.getFocusColor().equals(Paint.valueOf("red")) ||
                txtEmpName.getFocusColor().equals(Paint.valueOf("red"))||
                txtEmpContact.getFocusColor().equals(Paint.valueOf("red"))||
                txtEmpSalary.getFocusColor().equals(Paint.valueOf("red"))||
                txtEmpAddress.getFocusColor().equals(Paint.valueOf("red")))

        {
            return false;
        }else if(txtEmployeeId.getText().equals("") ||
                txtEmpName.getText().equals("")||
                txtEmpContact.getText().equals("")||
                txtEmpSalary.getText().equals("")||
                txtEmpAddress.getText().equals(""))
        {
            return false;
        }
        return true;
    }


    public void btnEmployeeDelete(ActionEvent actionEvent) {
        String id = txtEmployeeId.getText();

        try {
            boolean isDelete = EmployeeModel.delete(id);
            if (isDelete) {
                new Alert(Alert.AlertType.CONFIRMATION, " Deleted Successfully").show();
                getAllEmployees();
            } else {
                new Alert(Alert.AlertType.ERROR, "Delete Error").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        genarateNextEmployeeId();
    }

    public void btnEmployeeUpdate(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Employee e = makeEmployeeObject();

        boolean isUpdated = EmployeeModel.updateEmployee(e);
         if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully  Updated!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
        }
        txtEmpName.clear();
        txtEmpAddress.clear();
        txtEmpContact.clear();
        txtEmpSalary.clear();
        genarateNextEmployeeId();
        setCellValueFactory();
        getAllEmployees();
    }

    public void btnEmployeeSaveOnAction(ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        if(!isValidated()){
            new Alert(Alert.AlertType.WARNING,
                    "Fill All Fields Correctly !",
                    ButtonType.OK
            ).show();
            return;
        }
        Employee e = makeEmployeeObject();
        boolean isSave = EmployeeModel.saveEmployee(e.getEmployeeId(), e.getName(), e.getSalary(), e.getContact(), e.getAddress());

        if (isSave) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully...");
        } else {
            new Alert(Alert.AlertType.ERROR, "something happened! please try again...");
        }
        txtEmpName.clear();
        txtEmpAddress.clear();
        txtEmpContact.clear();
        txtEmpSalary.clear();
        genarateNextEmployeeId();
        setCellValueFactory();
        getAllEmployees();
    }

    public void genarateNextEmployeeId() {
        try {
            String nextSupId = EmployeeModel.genarateNextEmployeeId();
            txtEmployeeId.setText(nextSupId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmpSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colEmpContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmpAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    void getAllEmployees() {
        try {
            ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
            List<Employee> employeeList = EmployeeModel.getAll();

            for (Employee employee : employeeList) {
                obList.add(new EmployeeTm(
                        employee.getEmployeeId(),
                        employee.getName(),
                        employee.getSalary(),
                        employee.getContact(),
                        employee.getAddress()
                ));
            }
            tblEmployees.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void btnEmprint(ActionEvent actionEvent) {
        new Thread() {
            @Override
            public void run() {
                try {
                    JasperDesign load = JRXmlLoader.load(new File("E:\\D\\healthcare new\\src\\main\\resources\\js-reports\\employee_all_form.jrxml"));

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

