package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.datamodel.ParkingMeterData;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root;
        root = FXMLLoader.load(getClass().getResource("views/mainwindow.fxml"));
        primaryStage.setTitle("Parking Meter Tracker");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        try{
            ParkingMeterData.getInstance().storeParkingMeters();
        } catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void init() throws Exception {
        try{
            ParkingMeterData.getInstance().loadParkingMeters();
        } catch (IOException exception){
            System.out.println(exception.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
