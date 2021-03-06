package sample.datamodel;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

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

    public String getBegTime(){
        if (begTime == null){
            return "Meter is not running, input quarters to start.";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(begTime.getDayOfWeek());
            sb.append(" ");
            sb.append(begTime.getMonth());
            sb.append(" ");
            sb.append(begTime.getDayOfMonth());
            sb.append(", ");
            sb.append(begTime.getYear());
            sb.append(" ");
            sb.append(begTime.getHour());
            sb.append(":");
            sb.append(begTime.getMinute());
            sb.append(":");
            sb.append(begTime.getSecond());
            return sb.toString();
        }
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
