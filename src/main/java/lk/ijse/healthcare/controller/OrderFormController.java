package lk.ijse.healthcare.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderFormController {
    public JFXButton btn;
    public AnchorPane main;

    /*void initialize() {
        setCellValueFactory();
        setTableValues();

        setTextFieldValidations();
    }

    private void setTextFieldValidations() {
        Validations.setFocus(txtOrderId, Validations.orderIdPattern);
        Validations.setFocus(txtPatientId, Validations.patientIdPattern);
        Validations.setFocus(medIdTxt, Validations.medIdPattern);
        Validations.setFocus(txtOrderDate, Validations.datePattern);
        Validations.setFocus(txtOrderQty, Validations.intPattern1);

    }

    private boolean isAllDataValidated() {
        if (txtOrderId.getFocusColor().equals(Paint.valueOf("red")) ||
                txtPatientId.getFocusColor().equals(Paint.valueOf("red")) ||
                medIdTxt.getFocusColor().equals(Paint.valueOf("red")) ||
                txtOrderDate.getFocusColor().equals(Paint.valueOf("red")) ||
                txtOrderQty.getFocusColor().equals(Paint.valueOf("red"))
        ) {
            return false;
        } else if (txtOrderId.getText().equals("") ||
                medIdTxt.getText().equals("") ||
                txtPatientId.getText().equals("") ||
                txtOrderDate.getText().equals("") ||
                txtOrderQty.getText().equals("")
        ) {
            return false;
        }
        return true;

    }

    private String dateFormateChanger() {
        String date = txtOrderDate.getText();

        String[] dateAr = date.split("-");

        return dateAr[2]+"-" + dateAr[1] + "-" + dateAr[0] ;

    }*/


    public void btnOrderSupOrderDetails(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/supplies_order_details_form.fxml"));
        main.getChildren().clear();
        main.getChildren().addAll(root);

    }

    public void btnOrderCusOrderDetails(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customer_order_details_form.fxml"));
        main.getChildren().clear();
        main.getChildren().addAll(root);

    }
}
