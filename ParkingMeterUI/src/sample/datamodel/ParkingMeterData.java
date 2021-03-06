package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.time.format.DateTimeFormatter;

public class ParkingMeterData {

        private static ParkingMeterData instance = new ParkingMeterData();
        private static String filename = "parkingmeters.dat";

        // ObservableList binding
        private ObservableList<ParkingMeter> parkingMeters;
        private DateTimeFormatter formatter;

        public static ParkingMeterData getInstance(){
            return instance;
        }

        //Ensure can't create other instances of class
        private ParkingMeterData(){
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        }

        public ObservableList<ParkingMeter> getParkingMeters() {
            return parkingMeters ;
        }

        public void loadParkingMeters() throws IOException {
            parkingMeters = FXCollections.observableArrayList();
            try(ObjectInputStream file = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
                boolean eof = false;
                int count = 1;
                while (!eof) {
                    try {
                        ParkingMeter meter = (ParkingMeter) file.readObject();
                        System.out.println("Loading meter #" + count + ". With max time: " + meter.getMaxTime());
                        parkingMeters.add(meter);
                    } catch (EOFException e) {
                        eof = true;
                    }
                    count++;
                }
            } catch(IOException io) {
                System.out.println("IO Exception " + io.getMessage());
            } catch(ClassNotFoundException e) {
                System.out.println("ClassNotFoundException " + e.getMessage());
            }
        }

        public void storeParkingMeters() throws IOException {
            int count = 1;
            try (ObjectOutputStream file = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
                for(ParkingMeter meter : parkingMeters) {
                    System.out.println("Storing meter #" + count + ". With max time: " + meter.getMaxTime());
                    file.writeObject(meter);
                    count++;
                }
            }
        }

        public void deleteParkingMeter(ParkingMeter meter){
            getParkingMeters().remove(meter);
        }
}
