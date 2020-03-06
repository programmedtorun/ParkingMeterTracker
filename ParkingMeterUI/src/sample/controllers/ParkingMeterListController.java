package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import sample.datamodel.ParkingMeter;
import sample.datamodel.ParkingMeterData;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ParkingMeterListController {

    @FXML
    private TextArea cumSecElapsedTextArea;

    @FXML
    private TextArea begTime;

    @FXML
    private ListView<ParkingMeter> pmListView;

    public void initialize(){
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
}
