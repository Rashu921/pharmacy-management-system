package lk.ijse.healthcare.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.security.auth.login.LoginContext;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {
    public AnchorPane home;
    public AnchorPane DashContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/payment_form.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        home.getChildren().clear();
        home.getChildren().addAll(root);
    }


    public void payOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/payment_form.fxml"));
        home.getChildren().clear();
        home.getChildren().addAll(root);
        /*Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/
    }

    public void SupplierONAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/supplier_form.fxml"));
        home.getChildren().clear();
        home.getChildren().addAll(root);
    }

    public void orderOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/order_form.fxml"));
        //Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        home.getChildren().clear();
        home.getChildren().addAll(root);
    }

    public void cusOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customer_form.fxml"));
        home.getChildren().clear();
        home.getChildren().addAll(root);
    }

    public void empOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/employee_form.fxml"));
        home.getChildren().clear();
        home.getChildren().addAll(root);
    }

    public void medicineOn(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/medicne_form.fxml"));
        home.getChildren().clear();
        home.getChildren().addAll(root);
    }

    public void btnUserOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/user_form.fxml"));
        home.getChildren().clear();
        home.getChildren().addAll(root);
    }

    public void btnArrivalOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/arrival_form.fxml"));
        home.getChildren().clear();
        home.getChildren().addAll(root);
    }

    public void btnLogOutlOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage)DashContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
        window.centerOnScreen();

    }



}
