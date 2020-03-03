package sample;

import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import sample.datamodel.ParkingMeter;
import sample.datamodel.ParkingMeterData;

public class NewMeterController {


    @FXML
    private Spinner<Integer> maxTime;

    @FXML
    private Spinner<Integer> rate;


    public ParkingMeter processResults(){
        int maxTimeValue = maxTime.getValue();
        int rateValue = rate.getValue();
        ParkingMeter meter = new ParkingMeter(maxTimeValue, rateValue);
        ParkingMeterData.getInstance().getParkingMeters().add(meter);
        return meter;
    }




}