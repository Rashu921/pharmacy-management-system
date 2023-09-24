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
import lk.ijse.healthcare.dto.Medicine;
import lk.ijse.healthcare.model.CustomerModel;
import lk.ijse.healthcare.model.EmployeeModel;
import lk.ijse.healthcare.model.MedicineModel;
import lk.ijse.healthcare.tm.EmployeeTm;
import lk.ijse.healthcare.tm.MedicineTm;
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

public class MedicineFormController implements Initializable {
    public TableView tblMedicineStock;
    public TableColumn colMedId;
    public TableColumn colMedName;
    public TableColumn colMedType;
    public TableColumn colMedStock;
    public JFXButton btnSave;
    public JFXTextField txtMedType;
    public JFXTextField txtMedStock;
    public JFXTextField txtMedName;
    public JFXTextField txtMedId;
    public JFXTextField txtMediPrice;
    public TableColumn colMedPrice;

    public Medicine makeMedicineObject() {
        String medId = txtMedId.getText();
        String medName = txtMedName.getText();
        String medType = txtMedType.getText();
        String medStock = txtMedStock.getText();
        int medPrice =Integer.parseInt(txtMediPrice.getText());

        return new Medicine(medId, medName, medType, medStock, medPrice);
    }

    public void btnSearch(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        genarateNextMedicineId();
        getAllMedicine();
        setTextFieldValidation();
    }
    public void setTextFieldValidation(){
        ValidationUtil.setFocus(txtMedId,ValidationUtil.medicineIdPattern);
        ValidationUtil.setFocus( txtMedName,ValidationUtil.namePattern);
        ValidationUtil.setFocus( txtMediPrice,ValidationUtil.SallaryPAttern);
        ValidationUtil.setFocus( txtMedType,ValidationUtil.namePattern);
        ValidationUtil.setFocus( txtMedStock,ValidationUtil.AddressPAttern);


    }
    private boolean isValidated(){
        if(txtMedId.getFocusColor().equals(Paint.valueOf("red")) ||
                txtMedName.getFocusColor().equals(Paint.valueOf("red"))||
                txtMediPrice.getFocusColor().equals(Paint.valueOf("red"))||
                txtMedType.getFocusColor().equals(Paint.valueOf("red"))||
                txtMedStock.getFocusColor().equals(Paint.valueOf("red")))

        {
            return false;
        }else if(txtMedId.getText().equals("") ||
                txtMedName.getText().equals("")||
                txtMediPrice.getText().equals("")||
                txtMedType.getText().equals("")||
                txtMedStock.getText().equals(""))
        {
            return false;
        }
        return true;
    }

    public void btnMedicineDelete(ActionEvent actionEvent) {
    }


    public void genarateNextMedicineId() {
        try {
            String nextSupId = MedicineModel.generateNextMedicineId();
            txtMedId.setText(nextSupId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void setCellValueFactory() {
        colMedId.setCellValueFactory(new PropertyValueFactory<>("medId"));
        colMedName.setCellValueFactory(new PropertyValueFactory<>("medName"));
        colMedType.setCellValueFactory(new PropertyValueFactory<>("medType"));
        colMedStock.setCellValueFactory(new PropertyValueFactory<>("medStock"));
        colMedPrice.setCellValueFactory(new PropertyValueFactory<>("medPrice"));

    }

    void getAllMedicine() {
        try {
            ObservableList<MedicineTm> obList = FXCollections.observableArrayList();
            List<Medicine> medicineList = MedicineModel.getAll();

            for (Medicine medicine : medicineList) {
                obList.add(new MedicineTm(
                        medicine.getMedId(),
                        medicine.getMedName(),
                        medicine.getMedType(),
                        medicine.getMedStock(),
                        medicine.getMedPrice()

                ));
            }
            tblMedicineStock.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void btnMedStockSaveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(!isValidated()){
            new Alert(Alert.AlertType.WARNING,
                    "Fill All Fields Correctly !",
                    ButtonType.OK
            ).show();
            return;
        }
        Medicine m = makeMedicineObject();
        boolean isSave = MedicineModel.saveMedicine(m.getMedId(), m.getMedName(), m.getMedType(), m.getMedStock(),m.getMedPrice());

        if (isSave) {
            new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully...");
        } else {
            new Alert(Alert.AlertType.ERROR, "something happened! please try again...");
        }
        txtMedName.clear();
        txtMedType.clear();
        txtMedStock.clear();
        txtMediPrice.clear();
        genarateNextMedicineId();
        setCellValueFactory();
        getAllMedicine();
    }

    public void btnMedStockDelete(ActionEvent actionEvent) {
        String id = txtMedId.getText();

        try {
            boolean isDelete = MedicineModel.delete(id);
            if (isDelete) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully").show();
                getAllMedicine();
            } else {
                new Alert(Alert.AlertType.ERROR, "Delete Error").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        genarateNextMedicineId();
    }

    public void btnMedStockUpdate(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Medicine m = makeMedicineObject();

        boolean isUpdated = MedicineModel.updateMedicine(m);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Updated!").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
        }
        txtMedName.clear();
        txtMedType.clear();
        txtMedStock.clear();
        txtMediPrice.clear();
        genarateNextMedicineId();
        setCellValueFactory();
        getAllMedicine();
    }

    public void btnCusSearch(ActionEvent actionEvent) {
    }

    public void btnMeprint(ActionEvent actionEvent) {
        new Thread() {
            @Override
            public void run() {
                try {
                    JasperDesign load = JRXmlLoader.load(new File("E:\\D\\healthcare new\\src\\main\\resources\\js-reports\\medicine_all_form.jrxml"));

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
