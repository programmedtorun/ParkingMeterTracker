package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import sample.datamodel.ParkingMeter;
import sample.datamodel.ParkingMeterData;

public class NewMeterController {


    @FXML
    public Spinner<Integer> maxTime;

    @FXML
    public Spinner<Integer> rate;

    public ParkingMeter createNewMeter(){
        int maxTimeValue = maxTime.getValue();
        int rateValue = rate.getValue();
        ParkingMeter meter = new ParkingMeter(maxTimeValue, rateValue);
        ParkingMeterData.getInstance().getParkingMeters().add(meter);
        return meter;
    }




}
