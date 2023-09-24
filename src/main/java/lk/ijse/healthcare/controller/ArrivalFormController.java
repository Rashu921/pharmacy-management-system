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
import lk.ijse.healthcare.dto.Arrival;
import lk.ijse.healthcare.dto.Customer;
import lk.ijse.healthcare.model.ArrivalModel;
import lk.ijse.healthcare.model.CustomerModel;
import lk.ijse.healthcare.tm.ArrivalTm;
import lk.ijse.healthcare.tm.CustomerTm;
import lk.ijse.healthcare.util.ValidationUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ArrivalFormController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateNextArrivalId();
        setCellValueFactory();
        getAll();
        setTextFieldValidation();
    }

    public JFXTextField txtEmpId;
    public JFXTextField txtAttTime;
    public JFXButton btnAttSave;
    public TableView tblAtt;
    public TableColumn colEmpId;
    public TableColumn colAttTime;
    public TableColumn colAttDate;
    public JFXTextField txtAttDate;

    public void btnAttSaveOnAction(ActionEvent actionEvent) {
        if(!isValidated()){
            new Alert(Alert.AlertType.WARNING,
                    "Fill All Fields Correctly !",
                    ButtonType.OK
            ).show();
            return;
        }
        String empId = txtEmpId.getText();
        String time = txtAttTime.getText();
        String date = txtAttDate.getText();

        try{
            boolean isCreate = ArrivalModel.saveArrivel(empId,time,date);
            if(isCreate){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved Successfully...");


            }
        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e);
        }


        txtAttTime.clear();
        txtAttDate.clear();
        generateNextArrivalId();
        setCellValueFactory();
        getAll();
    }
    private void generateNextArrivalId() {
        try {
            String nextId = ArrivalModel.generateNextEmpId();
            txtEmpId.setText(nextId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void setTextFieldValidation(){
        ValidationUtil.setFocus(txtEmpId,ValidationUtil.employeeIdPattern);
        ValidationUtil.setFocus( txtAttDate,ValidationUtil.datePattern);
        ValidationUtil.setFocus( txtAttTime,ValidationUtil.timePattern);



    }
    private boolean isValidated(){
        if(txtEmpId.getFocusColor().equals(Paint.valueOf("red")) ||
                txtAttDate.getFocusColor().equals(Paint.valueOf("red"))||
                txtAttTime.getFocusColor().equals(Paint.valueOf("red")))


        {
            return false;
        }else if(txtEmpId.getText().equals("") ||
                txtAttDate.getText().equals("")||
                txtAttTime.getText().equals(""))

        {
            return false;
        }
        return true;
    }


    void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colAttTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colAttDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

    }
    void getAll() {
        try {
            ObservableList<ArrivalTm> obList = FXCollections.observableArrayList();
            List<Arrival> arrivalsList = ArrivalModel.getAll();

            for (Arrival arrival : arrivalsList) {
                obList.add(new ArrivalTm(
                        arrival.getEmpId(),
                        arrival.getTime(),
                        arrival.getDate()


                ));
            }
            tblAtt.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }



    public void btnAttDeleteOnAction(ActionEvent actionEvent) {
        String id = txtEmpId.getText();

        try {
            boolean isDelete = ArrivalModel.delete(id);
            if (isDelete) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully").show();
                getAll();
            } else {
                new Alert(Alert.AlertType.ERROR, "Delete Error").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        generateNextArrivalId();
    }

    public void btnAttUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String empId = txtEmpId.getText();
        String time = txtAttTime.getText();
        String date = txtAttDate.getText();


        Arrival arrival = new Arrival(empId,time,date);

        boolean isUpdated = ArrivalModel.update(arrival);
        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully Updated!").show();
            getAll();
        }else{
            new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
        }

    }
    }



