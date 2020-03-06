package sample.controllers;

import javafx.beans.property.IntegerProperty;
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
        selectItem(pmListView.getItems().get(0));
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
                            setText("Secs elapsed:\t\t" + Long.toString(meter.getCumSecElapsed()) + "\n" +
                                    "Max Time:\t\t" + Integer.toString(meter.getMaxTime()) + "\n" +
                                    "Rate:\t\t\t" + Integer.toString(meter.getRate()));
//                            if(item.getDeadline().isBefore(LocalDate.now().plusDays(1))){
//                                setTextFill(Color.RED);
//                            } else if(item.getDeadline().equals(LocalDate.now().plusDays(1))){
//                                setTextFill(Color.ORANGE);
//                            }
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
    public void selectItem(ParkingMeter meter){
        pmListView.getSelectionModel().select(meter);
        cumSecElapsedTextArea.setText(Long.toString(meter.getCumSecElapsed()));
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy - h:ma");
//        begTime.setText(df.format(meter.getBegTime()));
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
