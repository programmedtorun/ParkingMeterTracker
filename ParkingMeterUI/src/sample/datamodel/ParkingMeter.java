package sample.datamodel;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

/*
Design and implement a class 'ParkingMeter' for parking meters.
The class has three member variables: 'maxTime' for maximum parking minutes,
'rate' for parking rate (minutes per quarter), and 'time' for remaining parking time.

The class should provide member functions for the client to insert quarters and check
remaining parking time.

The constructor should take the maximum parking minutes and the rate.
 */

public class ParkingMeter implements Serializable {

    private static final long serialVersionUID = 1L;
    private int maxTime; // minutes
    private int rate; // minutes per $0.25
    private long time; //seconds
    private long initialTime;
    private LocalDateTime begTime;
    private Long secElapsed;
    private long cumSecElapsed;

    public ParkingMeter(int maxTime, int rate){
        this.maxTime = maxTime;
        this.rate = rate;
        this.time = 0;
        this.secElapsed = null;
        this.initialTime = 0L;
        this.cumSecElapsed = 0L;
        this.begTime = null;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public long getCumSecElapsed() {
        return cumSecElapsed;
    }

    public int getRate() {
        return rate;
    }

    public long getTime() {
        return time;
    }

    public void insertQuarters(int numQsInserted){
        if (begTime == null || time == 0){
            begTime = LocalDateTime.now(); time = 0; secElapsed = null; cumSecElapsed = 0L;
        }
        long maxQsAccepted = ((maxTime - (time / 60)) / rate);
        if (numQsInserted > maxQsAccepted){ // #seconds bought
            time += (maxQsAccepted * rate * 60);
        } else {
            time += (numQsInserted * rate * 60);
        }
    }

    public String checkTimeRemaining(){
        if (begTime != null) {
            Duration duration = Duration.between(begTime, LocalDateTime.now());
            if (secElapsed == null) {
                secElapsed = duration.getSeconds();
                cumSecElapsed += secElapsed;
            } else {
                secElapsed = duration.getSeconds() - cumSecElapsed;
                cumSecElapsed += secElapsed;
            }
            if (time <= 0) {
                begTime = null; secElapsed = null; cumSecElapsed = 0L; time = 0;
            } else {
                time -= secElapsed;
            }
        } else {
            secElapsed = null; cumSecElapsed = 0L; time = 0;
        }
        return String.format("%d:%02d:%02d", time / 3600,
                (time % 3600) / 60, time % 60);
    }
}
