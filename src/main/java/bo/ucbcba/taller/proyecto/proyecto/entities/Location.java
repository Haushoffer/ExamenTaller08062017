package bo.ucbcba.taller.proyecto.proyecto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by CORE i7 on 31/05/2017.
 */
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private double latitude;
    @NotNull
    private double longitude;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "band_id")
    private Band band;

    private Date uploadDate ;

    private Date registerDate ;
    @Transient
    private int bandId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public Date getUploadDate() {
        return uploadDate;
    }
    public int getAssignedBandId(){
        bandId=band.getId();
        return bandId;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public int getBandId() {
        return bandId;
    }

    public void setBandId(int bandId) {
        this.bandId = bandId;
    }

    @Override
    public String toString()
    {
        return latitude+" "+longitude +" "+uploadDate.toString()+" "+registerDate.toString()+" "+bandId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
