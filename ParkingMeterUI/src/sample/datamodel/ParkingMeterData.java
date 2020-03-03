package sample.datamodel;

import javafx.collections.FXCollections;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ParkingMeterData {

        private static ParkingMeterData instance = new ParkingMeterData();
        private static String filename = "parkingmeters.dat";

        // bind ListView (Controller) to ObservableList (maybe eventually)
        private List<ParkingMeter> parkingMeters;
        private DateTimeFormatter formatter;

        public static ParkingMeterData getInstance(){
            return instance;
        }


        private ParkingMeterData(){
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        } //to ensure can't create other instances of class

        public List<ParkingMeter> getParkingMeters() {
            return parkingMeters ;
        }

        public void setParkingMeters(List<ParkingMeter> meters){
            this.parkingMeters = meters;
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
                    System.out.println("storing " + count + " meter, with max time: " + meter.getMaxTime());
                    file.writeObject(meter);
                    count++;
                }
            }

        }
}
