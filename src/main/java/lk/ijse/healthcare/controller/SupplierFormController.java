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
import lk.ijse.healthcare.dto.Supplier;
import lk.ijse.healthcare.model.MedicineModel;
import lk.ijse.healthcare.model.SupplierModel;
import lk.ijse.healthcare.tm.SupplierTm;
import lk.ijse.healthcare.util.ValidationUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SupplierFormController implements Initializable {

    public JFXTextField txtSupId;
    public TableView tblSupplier;
    public TableColumn colSupplierId;
    public TableColumn colSupName;
    public TableColumn colSupNic;
    public TableColumn colSupAddress;
    public TableColumn colSupContact;
    public JFXTextField txtSupNic;
    public JFXButton btnSupSave;

    @FXML
    private JFXTextField txtSupAddress;
    @FXML
    private JFXTextField txtSupContact;
    @FXML
    private JFXTextField txtSupName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAllSuppliers();
        genarateNextSupplierId();
        setTextFieldValidation();
    }



    public Supplier makeSupplierObject(){
        String supId = txtSupId.getText();
        String supName = txtSupName.getText();
        String supNic = txtSupNic.getText();
        String supContact = txtSupContact.getText();
        String supAddress = txtSupAddress.getText();

        return new Supplier(supId,supName,supNic,supContact,supAddress);
    }

    public void SupSaveOnAction(javafx.event.ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!isValidated()){
            new Alert(Alert.AlertType.WARNING,
                    "Fill All Fields Correctly !",
                    ButtonType.OK
            ).show();
            return;
        }
        Supplier s = makeSupplierObject();
        boolean isSave = SupplierModel.saveSupplier(s.getSupplierId(),s.getSupplierName(),s.getSupplierNic(),s.getSupplierContact(),s.getSupplierAddress());

        if (isSave) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully...");
        }else {
            new Alert(Alert.AlertType.ERROR, "something happened! please try again...");
        }
        txtSupAddress.clear();
        txtSupContact.clear();
        txtSupName.clear();
        txtSupNic.clear();
        genarateNextSupplierId();
        setCellValueFactory();
        getAllSuppliers();
    }  public void setTextFieldValidation(){
        ValidationUtil.setFocus(txtSupId,ValidationUtil.SupIdPAttern);
        ValidationUtil.setFocus( txtSupName,ValidationUtil.namePattern);
        ValidationUtil.setFocus( txtSupNic,ValidationUtil.nicPattern);
        ValidationUtil.setFocus( txtSupContact,ValidationUtil.contactPattern);
        ValidationUtil.setFocus( txtSupAddress,ValidationUtil.AddressPAttern);


    }
    private boolean isValidated(){
        if(txtSupId.getFocusColor().equals(Paint.valueOf("red")) ||
                txtSupName.getFocusColor().equals(Paint.valueOf("red"))||
                txtSupNic.getFocusColor().equals(Paint.valueOf("red"))||
                txtSupContact.getFocusColor().equals(Paint.valueOf("red"))||
                txtSupAddress.getFocusColor().equals(Paint.valueOf("red")))

        {
            return false;
        }else if(txtSupId.getText().equals("") ||
                txtSupName.getText().equals("")||
                txtSupNic.getText().equals("")||
                txtSupContact.getText().equals("")||
                txtSupAddress.getText().equals(""))
        {
            return false;
        }
        return true;
    }

    public void genarateNextSupplierId() {
        try {
            String nextSupId = SupplierModel.genarateNextSupplierId();
            txtSupId.setText(nextSupId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void setCellValueFactory() {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("SupplierId"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("SupplierName"));
        colSupNic.setCellValueFactory(new PropertyValueFactory<>("SupplierNic"));
        colSupContact.setCellValueFactory(new PropertyValueFactory<>("SupplierContact"));
        colSupAddress.setCellValueFactory(new PropertyValueFactory<>("SupplierAddress"));
    }

    void getAllSuppliers() {
        try {
            ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
            List<Supplier> supList = SupplierModel.getAll();

            for (Supplier supplier : supList) {
                obList.add(new SupplierTm(
                        supplier.getSupplierId(),
                        supplier.getSupplierName(),
                        supplier.getSupplierContact(),
                        supplier.getSupplierAddress(),
                        supplier.getSupplierNic()
                ));
            }
            tblSupplier.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void SupDeleteOnAction(javafx.event.ActionEvent actionEvent) {
        String id = txtSupId.getText();

        try {
            boolean isDelete = SupplierModel.delete(id);
            if (isDelete) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully").show();
                getAllSuppliers();
            } else {
                new Alert(Alert.AlertType.ERROR, "Delete Error").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        genarateNextSupplierId();
    }

    public void SupUpdateOnAction(javafx.event.ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Supplier s = makeSupplierObject();

        boolean isUpdated = SupplierModel.updateSupplier(s);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated!").show();
        }else{
            new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
        }
        txtSupAddress.clear();
        txtSupContact.clear();
        txtSupName.clear();
        txtSupNic.clear();
        genarateNextSupplierId();
        setCellValueFactory();
        getAllSuppliers();
    }

    public void btnSuprint(ActionEvent actionEvent) {
        new Thread() {
            @Override
            public void run() {
                try {
                    JasperDesign load = JRXmlLoader.load(new File("E:\\D\\healthcare new\\src\\main\\resources\\js-reports\\supplier_all_form.jrxml"));

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
