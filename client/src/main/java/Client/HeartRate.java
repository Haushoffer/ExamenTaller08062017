package Client;

/**
 * Created by Jp on 02/06/2017.
 */
public class HeartRate
{
    private int bpm;
    private long registrationDate;


    public HeartRate(int bpm, long registrationDate) {
        this.setBpm(bpm);
        this.setRegistrationDate(registrationDate);
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public long getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(long registrationDate) {
        this.registrationDate = registrationDate;
    }
}
