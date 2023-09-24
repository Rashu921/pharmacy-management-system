package lk.ijse.healthcare.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.healthcare.dto.Arrival;
import lk.ijse.healthcare.dto.Payment;
import lk.ijse.healthcare.model.ArrivalModel;
import lk.ijse.healthcare.model.MedicineModel;
import lk.ijse.healthcare.model.PaymentModel;
import lk.ijse.healthcare.tm.ArrivalTm;
import lk.ijse.healthcare.tm.PaymentTm;
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

public class PaymentFormController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNextPaymentlId();
        setCellValueFactory();
        getAll();
        setCmbPPaymentType();
        setTextFieldValidation();
    }

    public JFXTextField txtPPaymentId;
    public JFXTextField txtPDate;
    public JFXTextField txtPtotal;
    public JFXComboBox cmbPPaymentType;
    public JFXButton btnPSave;
    public TableView tblPayment;
    public TableColumn colPPaymentId;
    public TableColumn colPPaymentType;
    public TableColumn colPDate;
    public TableColumn colPTotal;

    public void btnPSaveOnAction(ActionEvent actionEvent) {
        if(!isValidated()){
            new Alert(Alert.AlertType.WARNING,
                    "Fill All Fields Correctly !",
                    ButtonType.OK
            ).show();
            return;
        }
        String paymentId = txtPPaymentId.getText();
        String paymentType = (String) cmbPPaymentType.getValue();
        String date = txtPDate.getText();
        double total =Double.parseDouble(txtPtotal.getText());

        try{
            boolean isCreate = PaymentModel.savePayment(paymentId,paymentType,date,total);
            if(isCreate){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved Successfully...");
                generateNextPaymentlId();
                getAll();
                cmbPPaymentType.setValue("");
                txtPDate.clear();
                txtPtotal.clear();

            }
        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e);
        }
        txtPPaymentId.clear();
        txtPDate.clear();
        txtPtotal.clear();
        generateNextPaymentlId();
        setCellValueFactory();

    }
    public void setTextFieldValidation(){
        ValidationUtil.setFocus(txtPPaymentId,ValidationUtil.PayIdPAttern);
        ValidationUtil.setFocus( txtPDate,ValidationUtil.datePattern);
        ValidationUtil.setFocus( txtPtotal,ValidationUtil.SallaryPAttern);


    }
    private boolean isValidated(){
        if(txtPPaymentId.getFocusColor().equals(Paint.valueOf("red")) ||
                txtPDate.getFocusColor().equals(Paint.valueOf("red"))||
                txtPtotal.getFocusColor().equals(Paint.valueOf("red")))


        {
            return false;
        }else if(txtPPaymentId.getText().equals("") ||
                txtPDate.getText().equals("")||
                txtPtotal.getText().equals(""))

        {
            return false;
        }
        return true;
    }
    private void generateNextPaymentlId() {
        try {
            String nextId = PaymentModel.generateNextPaymentId();
            txtPPaymentId.setText(nextId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void setCmbPPaymentType(){
        ObservableList<String>type=FXCollections.observableArrayList();
            type.add("Cash");
            type.add("Card");
            cmbPPaymentType.setItems(type);
    }


    void setCellValueFactory() {
        colPPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colPPaymentType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        colPDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
    void getAll() {
        try {
            ObservableList<PaymentTm> obList = FXCollections.observableArrayList();
            List<Payment> paymentsList = PaymentModel.getAll();

            for (Payment payment : paymentsList) {
                obList.add(new PaymentTm(
                        payment.getPaymentId(),
                        payment.getPaymentType(),
                        payment.getDate(),
                        payment.getTotal()


                ));
            }
            tblPayment.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void btnPDeleteOnAction(ActionEvent actionEvent) {
        String id = txtPPaymentId.getText();

        try {
            boolean isDelete = PaymentModel.delete(id);
            if (isDelete) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully").show();
                getAll();
            } else {
                new Alert(Alert.AlertType.ERROR, "Delete Error").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        generateNextPaymentlId();

    }

    public void btnPUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String paymentId = txtPPaymentId.getText();
        String paymentType = (String) cmbPPaymentType.getValue();
        String date = txtPDate.getText();
        double total =Double.parseDouble(txtPtotal.getText());


        Payment payment = new Payment(paymentId,paymentType,date,total);

        boolean isUpdated = PaymentModel.update(payment);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Updated!").show();
            getAll();
        }else{
            new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
        }

    }

    public void btnPaprint(ActionEvent actionEvent) {
        new Thread() {
            @Override
            public void run() {
                try {
                    JasperDesign load = JRXmlLoader.load(new File("E:\\D\\healthcare new\\src\\main\\resources\\js-reports\\payment_all_form.jrxml"));

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



