package sample.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import sample.datamodel.ParkingMeter;
import sample.datamodel.ParkingMeterData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ParkingMeterListController {

    @FXML
    private ToggleButton filterToggleButton;

    @FXML
    private TextArea cumSecElapsedTextArea;

    @FXML
    private TextArea begTime;

    @FXML
    private ListView<ParkingMeter> pmListView;

    @FXML
    private ContextMenu listContextMenu; //will associate context menu with cell factory

    @FXML
    private TextField quarterCount;

    public void initialize(){
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ParkingMeter meter = pmListView.getSelectionModel().getSelectedItem();
                deleteMeter(meter);
            }
        });
        // ensures text input is numeric
        quarterCount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    quarterCount.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        listContextMenu.getItems().addAll(deleteMenuItem);
        pmListView.setItems(ParkingMeterData.getInstance().getParkingMeters());
        pmListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        selectMeter(pmListView.getItems().get(0));
        pmListView.setCellFactory(new Callback<ListView<ParkingMeter>, ListCell<ParkingMeter>>() {
            @Override
            public ListCell<ParkingMeter> call(ListView<ParkingMeter> param) {
                ListCell<ParkingMeter> cell = new ListCell<ParkingMeter>(){
                    @Override
                    protected void updateItem(ParkingMeter meter, boolean empty) {
                        super.updateItem(meter, empty);
                        if(empty){
                            setText(null);
                        }else{
                            int tm = (int) meter.getTime();
                            double num = meter.getMaxTime() - (tm / 60.0);
                            String forNum = String.format("%.2f", num);
                            setText("Time Remaining:\t\t" + meter.checkTimeRemaining() + "\n" +
                                    "Avail. min. for purchase:\t" + forNum + "\n" +
                                    "Rate:\t\t\t" + Integer.toString(meter.getRate()));
                            if(tm <= 120 && tm > 0){
                                setTextFill(Color.RED);
                            } else if(tm <= 300 && tm > 121){
                                setTextFill(Color.ORANGE);
                            }
                        }
                    }
                };
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) -> {
                            if (isNowEmpty){
                                cell.setContextMenu(null);
                            } else {
                                cell.setContextMenu(listContextMenu);
                            }
                        }
                );
                return cell;
            }
        });

    }
    public void selectMeter(ParkingMeter meter){
        pmListView.getSelectionModel().select(meter);
        begTime.setText("Beginning time:\n\n" + meter.getBegTime());
        double num = meter.getCumSecElapsed() / 60.0;
        String forNum = String.format("%.2f", num);
        cumSecElapsedTextArea.setText("Minutes elapsed:\n\n" + forNum);

    }

    @FXML
    public void handleKeyPress(KeyEvent event){
        ParkingMeter meter = pmListView.getSelectionModel().getSelectedItem();
        if(meter != null){
            if(event.getCode().equals(KeyCode.BACK_SPACE)){
                deleteMeter(meter);
            }
        }
    }

    @FXML
    public void handleClickListView(){
        ParkingMeter meter = pmListView.getSelectionModel().getSelectedItem();
        selectMeter(meter);

    }





    @FXML
    public void handleQuarterAdding(){
        ParkingMeter meter = pmListView.getSelectionModel().getSelectedItem();
        meter.insertQuarters(Integer.parseInt(quarterCount.getText()));
    }

    public void deleteMeter(ParkingMeter meter){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Parking Meter");
        alert.setHeaderText("Delete Meter with max time: " + meter.getMaxTime());
        alert.setContentText("Are you sure? Click OK.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            ParkingMeterData.getInstance().deleteParkingMeter(meter);
        }
    }
}
