package lk.ijse.healthcare.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.util.StringConverter;
import lk.ijse.healthcare.db.DbConnection;
import lk.ijse.healthcare.dto.*;
import lk.ijse.healthcare.model.CustomerModel;
import lk.ijse.healthcare.model.MedicineModel;
import lk.ijse.healthcare.model.SupplierModel;
import lk.ijse.healthcare.model.SuppliesOrderDetailsModel;
import lk.ijse.healthcare.tm.CustomerOrderDetailsTm;
import lk.ijse.healthcare.tm.CustomerTm;
import lk.ijse.healthcare.tm.SuppliesOrderDetailsTm;
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

public class SuppliesOrderDetailsFormController implements Initializable {
    public ComboBox<Medicine> cmbMedicine;
    public ComboBox<Supplier> cmbSupplier;
    public JFXTextField txtQty;
    public JFXButton btnAddToCart;
    public TableColumn colSODOrderId;
    public TableColumn colSODSuppId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNextSupId();
        setCellValueFactory();
        setComBoBoxes();
        //getAll();
    }
    public JFXTextField txtSupOrderDetailsSupId;
    public JFXTextField txtSupOrderDetailsTotal;
    public JFXTextField txtSupOrderDetailsQuantity;
    public JFXButton btnSODSave;
    public TableView<SuppliesOrderDetailsTm> tblCOD;
    public TableColumn colSODMedicineId;
    public TableColumn colSODQuantity;
    public TableColumn colSODTotal;

    public void btnSupOrderDetailsSaveOnAction(ActionEvent actionEvent) {

    }
    private void generateNextSupId() {
        try {
            String nextId = SuppliesOrderDetailsModel.generateNextSupId();
            txtSupOrderDetailsSupId.setText(nextId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    void setCellValueFactory() {
        colSODOrderId.setCellValueFactory(new PropertyValueFactory<>("SupId"));
        colSODSuppId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colSODMedicineId.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        colSODQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colSODTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

    }
    void getAll() {
        try {
            ObservableList<SuppliesOrderDetailsTm> obList = FXCollections.observableArrayList();
            List<SuppliesOrderDetails> suppliesOrderDetailsList = SuppliesOrderDetailsModel.getAll();

            for (SuppliesOrderDetails suppliesOrderDetails : suppliesOrderDetailsList) {
                obList.add(new SuppliesOrderDetailsTm(
                        suppliesOrderDetails.getSupId(),
                        suppliesOrderDetails.getSupplierId(),
                        suppliesOrderDetails.getMedicineId(),
                        suppliesOrderDetails.getQuantity(),
                        suppliesOrderDetails.getTotal()


                ));
            }
            tblCOD.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void btnSODDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnSODUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

    }


    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        Medicine selectedItem = cmbMedicine.getSelectionModel().getSelectedItem();
        SuppliesOrderDetailsTm customerOrderDetailsTm = new SuppliesOrderDetailsTm(txtSupOrderDetailsSupId.getText(),cmbSupplier.getSelectionModel().getSelectedItem().getSupplierId(),selectedItem.getMedId(), Integer.parseInt(txtQty.getText()), Integer.parseInt(txtSupOrderDetailsTotal.getText()));
        ObservableList<SuppliesOrderDetailsTm> items = tblCOD.getItems();
        selectedItem.setMedStock(String.valueOf(Integer.parseInt(selectedItem.getMedStock())-Integer.parseInt(txtQty.getText())));
        for(SuppliesOrderDetailsTm ob : items){
            if(ob.getMedicineId().equals(customerOrderDetailsTm.getMedicineId())){
                ob.setQuantity(customerOrderDetailsTm.getQuantity()+ob.getQuantity());
                ob.setTotal(customerOrderDetailsTm.getTotal()+ob.getTotal());
                tblCOD.refresh();
                return;
            }
        }
        items.addAll(customerOrderDetailsTm);


        txtQty.clear();
        txtSupOrderDetailsTotal.clear();
        setCellValueFactory();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        SuppliesOrderDetailsTm selectedItem = tblCOD.getSelectionModel().getSelectedItem();
        if(tblCOD.getSelectionModel().getSelectedIndex()==-1)return;
        tblCOD.getItems().remove(tblCOD.getSelectionModel().getSelectedIndex());
        ObservableList<Medicine> items = cmbMedicine.getItems();
        for (Medicine ob : items){
            if(ob.getMedId().equals(selectedItem.getMedicineId())){
                ob.setMedStock(String.valueOf(selectedItem.getQuantity()+Integer.parseInt(ob.getMedStock())));
            }
        }
    }

    public void initialize(){
        setComBoBoxes();
        setTextFieldValidation();
        generateNextSupId();
    }

    public void setComBoBoxes(){
        try {
            cmbMedicine.setItems(FXCollections.observableArrayList(MedicineModel.getAll()));
            cmbSupplier.setItems(FXCollections.observableArrayList(SupplierModel.getAll()));
            System.out.println(cmbMedicine.getItems().size());
            cmbMedicine.setConverter(new StringConverter<Medicine>() {
                @Override
                public String toString(Medicine medicine) {
                    return medicine==null ? null:medicine.getMedId();
                }

                @Override
                public Medicine fromString(String s) {
                    return null;
                }
            });
            cmbSupplier.setConverter(new StringConverter<Supplier>() {
                @Override
                public String toString(Supplier supplier) {
                    return supplier==null ? null:supplier.getSupplierId();
                }

                @Override
                public Supplier fromString(String s) {
                    return null;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setTextFieldValidation(){
        ValidationUtil.setFocus(txtSupOrderDetailsSupId,ValidationUtil.employeeIdPattern);
        ValidationUtil.setFocus( txtSupOrderDetailsQuantity,ValidationUtil.QtyPAttern);
        ValidationUtil.setFocus( txtSupOrderDetailsTotal,ValidationUtil.SallaryPAttern);



    }
    private boolean isValidated(){
        if(txtSupOrderDetailsSupId.getFocusColor().equals(Paint.valueOf("red")) ||
                txtSupOrderDetailsQuantity.getFocusColor().equals(Paint.valueOf("red"))||
                txtSupOrderDetailsTotal.getFocusColor().equals(Paint.valueOf("red")))


        {
            return false;
        }else if(txtSupOrderDetailsSupId.getText().equals("") ||
                txtSupOrderDetailsQuantity.getText().equals("")||
                txtSupOrderDetailsTotal.getText().equals(""))

        {
            return false;
        }
        return true;
    }

    public void btnSupOrderPrint(ActionEvent actionEvent) {
        new Thread() {
            @Override
            public void run() {
                try {
                    JasperDesign load = JRXmlLoader.load(new File("E:\\D\\healthcare new\\src\\main\\resources\\js-reports\\supplierOrder_all_form.jrxml"));

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
