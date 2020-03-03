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
        this.secElapsed = 0L;
        this.initialTime = 0L;
        this.cumSecElapsed = 0L;
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

    public long getInitialTime() {
        return initialTime;
    }

    public LocalDateTime getBegTime() {
        return begTime;
    }

    public Long getSecElapsed() {
        return secElapsed;
    }

    public void insertQuarters(int numQsInserted){
        System.out.println("Insert quarters.  " +
                "Meter time remaining is: " + this.checkTimeRemaining());
        long maxQsAccepted = (maxTime / rate);
        if (numQsInserted > maxQsAccepted){
            System.out.println("You have inserted too many quarters, " +
                    "please take your change of " +
                    (numQsInserted - maxQsAccepted) + " quarters.");
            time += maxQsAccepted * rate * 60;
            maxTime -= (maxQsAccepted * rate);
        } else {
            time += numQsInserted * rate * 60; // number of seconds
            maxTime -= (numQsInserted * rate);
        }
        if (begTime == null){
            this.begTime = LocalDateTime.now();
        }
        System.out.println("You now have " + (time / 60) + " minutes on this meter.");
    }

    public String checkTimeRemaining(){
        if (begTime != null){
            Duration duration = Duration.between(begTime, LocalDateTime.now());
            if(secElapsed == null){
                secElapsed = duration.getSeconds();
                cumSecElapsed += secElapsed;
            } else {
                // Need this line in the event checkTimeRemaining() is called with no sleep time in between.
                if(secElapsed == 0){ secElapsed = initialTime; } initialTime = secElapsed;

                // Subtracting the cumulative seconds that have elapsed and been calculated from waiting (sleep)
                secElapsed = duration.getSeconds() - cumSecElapsed;

                cumSecElapsed += secElapsed;
            }
            if (cumSecElapsed >= time){
                System.out.println("Expired meter");
            } else {
                maxTime += (cumSecElapsed / 60);
                time -= secElapsed;
            }
            return String.format("%d:%02d:%02d", time / 3600,
                    (time % 3600) / 60, time % 60);
        } else {
            return "00:00:00";
        }
    }
}
