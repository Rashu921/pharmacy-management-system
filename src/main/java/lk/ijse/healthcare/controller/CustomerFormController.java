package lk.ijse.healthcare.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import lk.ijse.healthcare.db.DbConnection;
import lk.ijse.healthcare.dto.Customer;
import lk.ijse.healthcare.model.CustomerModel;
import lk.ijse.healthcare.model.EmployeeModel;
import lk.ijse.healthcare.tm.CustomerTm;
import lk.ijse.healthcare.util.ValidationUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    public JFXTextField txtJoinDate;
    public TableColumn colCusJoinDate;
    @FXML
    private TableView<CustomerTm> tblCustomers;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colCusName;

    @FXML
    private TableColumn<?, ?> colCusAddress;

    @FXML
    private TableColumn<?, ?> colCusContact;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXTextField txtCusName;

    @FXML
    private JFXTextField txtCusAddress;

    @FXML
    private JFXTextField txtCusContact;

    @FXML
    private JFXTextField txtCustomerId;

    public void btnCustomerDelete(javafx.event.ActionEvent actionEvent) {
        String id = txtCustomerId.getText();

        try {
            boolean isDelete = CustomerModel.delete(id);
            if (isDelete) {
                new Alert(Alert.AlertType.CONFIRMATION, "Equipment Deleted Successfully").show();
                getAll();
            } else {
                new Alert(Alert.AlertType.ERROR, "Delete Error").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        generateNextCustomerId();
    }

    public void btnCustomerUpdate(javafx.event.ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtCustomerId.getText();
        String name = txtCusName.getText();
        String address = txtCusAddress.getText();
        String contact = txtCusContact.getText();
        String joinDate = txtJoinDate.getText();

        Customer customer = new Customer(id,name,contact,joinDate,address);

        boolean isUpdated = CustomerModel.update(customer);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Updated!").show();
        }else{
            new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
        }
        generateNextCustomerId();
        getAll();
        txtCusName.clear();
        txtCusAddress.clear();
        txtCusContact.clear();
        txtJoinDate.clear();

    }

    public void btnCusSearch(javafx.event.ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNextCustomerId();
        setCellValueFactory();
        getAll();
        setTextFieldValidation();
    }
    public void setTextFieldValidation(){
        ValidationUtil.setFocus(txtCustomerId,ValidationUtil.CustIdPAttern);
        ValidationUtil.setFocus( txtCusName,ValidationUtil.namePattern);
        ValidationUtil.setFocus( txtJoinDate,ValidationUtil.datePattern);
        ValidationUtil.setFocus( txtCusContact,ValidationUtil.contactPattern);
        ValidationUtil.setFocus( txtCusAddress,ValidationUtil.AddressPAttern);


    }
    private boolean isValidated(){
        if(txtCustomerId.getFocusColor().equals(Paint.valueOf("red")) ||
                txtCusName.getFocusColor().equals(Paint.valueOf("red"))||
                txtJoinDate.getFocusColor().equals(Paint.valueOf("red"))||
                txtCusContact.getFocusColor().equals(Paint.valueOf("red"))||
                        txtCusAddress.getFocusColor().equals(Paint.valueOf("red")))

        {
            return false;
        }else if(txtCustomerId.getText().equals("") ||
                txtCusName.getText().equals("")||
                txtJoinDate.getText().equals("")||
                txtCusContact.getText().equals("")||
                    txtCusAddress.getText().equals(""))
        {
            return false;
        }
        return true;
    }



    public void btnCustomerSaveOnAction(javafx.event.ActionEvent actionEvent) {
        if(!isValidated()){
            new Alert(Alert.AlertType.WARNING,
                    "Fill All Fields Correctly !",
                    ButtonType.OK
            ).show();
            return;
        }
        String id = txtCustomerId.getText();
        String name = txtCusName.getText();
        String address = txtCusAddress.getText();
        String contact = txtCusContact.getText();
        String joinDate = txtJoinDate.getText();

        try{
            boolean isCreate = CustomerModel.saveCustomer(id,name,contact,joinDate,address);
            if(isCreate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully...");
            }else {
                new Alert(Alert.AlertType.ERROR, "something happened! please try again...");
            }

        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e);
        }
        generateNextCustomerId();
        getAll();
        txtCusName.clear();
        txtCusAddress.clear();
        txtCusContact.clear();
        txtJoinDate.clear();
    }
    private void generateNextCustomerId() {
        try {
            String nextId = CustomerModel.generateNextCustomerId();
            txtCustomerId.setText(nextId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        colCusAddress.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        colCusContact.setCellValueFactory(new PropertyValueFactory<>("custContact"));
        colCusJoinDate.setCellValueFactory(new PropertyValueFactory<>("custJoinDate"));
    }
    void getAll() {
        try {
            ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
            List<Customer> customerList = CustomerModel.getAll();

            for (Customer customer : customerList) {
                obList.add(new CustomerTm(
                        customer.getCustId(),
                        customer.getCustName(),
                        customer.getCustContact(),
                        customer.getCustJoinDate(),
                        customer.getCustAddress()

                ));
            }
            tblCustomers.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void btnPrintOnAction(ActionEvent actionEvent) {
        new Thread() {
            @Override
            public void run() {
                try {
                    JasperDesign load = JRXmlLoader.load(new File("E:\\D\\healthcare new\\src\\main\\resources\\js-reports\\customer_all_form.jrxml"));

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
