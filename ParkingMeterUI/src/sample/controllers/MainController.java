package sample.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.datamodel.ParkingMeter;

import java.io.IOException;
import java.util.Optional;

public class MainController {

    @FXML
    private ListView<ParkingMeter> pmListView;

    @FXML
    private Button meterListButton;
    private ObservableList<ParkingMeter> parkingMeters;

    @FXML
    private BorderPane mainBorderPane;

    public void initialize(){
    }

    @FXML
    public void showMeterList(){
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/parkingmeterlist.fxml"));
            Parent root = fxmlLoader.load();
            stage.setTitle("PM List");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e){
            System.out.println("Could not load new window...");
            e.printStackTrace();
        }
    }

    @FXML
    public void showNewPMDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Create New Parking Meter");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../views/newmeterdialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog: ");
            e.printStackTrace();
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            NewMeterController ctrl = fxmlLoader.getController();
            ctrl.createNewMeter();
        }
    }

    @FXML
    public void handleExit(){
        Platform.exit();
    }
}
