package Client;

/**
 * Created by Jp on 02/06/2017.
 */
public class Location
{
    private double lat;
    private double lng;
    private long registrationDate;

    public Location(double lat, double lng, long registrationDate) {
        this.setLat(lat);
        this.setLng(lng);
        this.setRegistrationDate(registrationDate);
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public long getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(long registrationDate) {
        this.registrationDate = registrationDate;
    }
}
