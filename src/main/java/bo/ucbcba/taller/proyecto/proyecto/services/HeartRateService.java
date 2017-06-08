package bo.ucbcba.taller.proyecto.proyecto.services;

import bo.ucbcba.taller.proyecto.proyecto.entities.HeartRate;
import bo.ucbcba.taller.proyecto.proyecto.entities.Step;

import java.util.List;

/**
 * Created by CORE i7 on 31/05/2017.
 */
public interface HeartRateService {

    Iterable<HeartRate> listAllHeartRates();

    HeartRate getHeartRateById(Integer id);

    HeartRate saveHeartRate(HeartRate heartRate);

    List<HeartRate> listAllSearchedHeartRates(String dateStart, String dateFin);

    List<HeartRate> listAllSearchedHeartRatesByBand(String dateStart, String dateFin,String idBand);

    void deleteHeartRate(Integer id);

    List<HeartRate> listAllHeartRatesOfAnBand(String idBand);
}
