package lk.ijse.healthcare.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.EventObject;

public class loginFormController {
    public AnchorPane LoginContext;
    public JFXTextField txtLogUName;
    public JFXPasswordField txtLogPassword;
    public Label lblLog;
    public ImageView Homes;

    public void btnLogin(ActionEvent actionEvent) throws IOException {
        String user = "Dara";
        String password = "1998";
        if (txtLogUName.getText().equals(user) && txtLogPassword.getText().equals(password)) {
            Stage window = (Stage) LoginContext.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml "))));
            window.centerOnScreen();

        } else if (txtLogUName.getText().isEmpty() && txtLogPassword.getText().isEmpty()) {
            lblLog.setText("User Name Or Password IS Empty...!");
            txtLogUName.clear();
            txtLogPassword.clear();
        }
        else if (!txtLogUName.getText().equals(user)) {
            lblLog.setText("User Name is incorrect..!");
            txtLogPassword.clear();
            txtLogPassword.clear();
        } else if (!txtLogPassword.getText().equals(password)) {
            lblLog.setText("Password is incorrect..!");
            txtLogUName.clear();
            txtLogPassword.clear();
        }




     //   Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
     //   Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
      //  Scene scene = new Scene(root);
     //   stage.setScene(scene);
     //   stage.show();
    }
}
