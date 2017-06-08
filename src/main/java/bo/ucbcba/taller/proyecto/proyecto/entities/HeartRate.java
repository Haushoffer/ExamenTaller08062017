package bo.ucbcba.taller.proyecto.proyecto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by CORE i7 on 31/05/2017.
 */
@Entity
public class HeartRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Column(name = "bpm")
    private Integer rate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "band_id")
    private Band band;

    private Date uploadDate ;

    private Date registerDate ;

    @Transient
    private int bandId;

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

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
        return rate+" "+uploadDate.toString()+" "+registerDate.toString()+" "+bandId;
    }
}
