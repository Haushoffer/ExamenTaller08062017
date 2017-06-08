package bo.ucbcba.taller.proyecto.proyecto.services;

import bo.ucbcba.taller.proyecto.proyecto.entities.HeartRate;
import bo.ucbcba.taller.proyecto.proyecto.entities.Step;
import bo.ucbcba.taller.proyecto.proyecto.repositories.HeartRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CORE i7 on 31/05/2017.
 */
@Service
public class HeartRateImpl implements HeartRateService {
    private HeartRateRepository heartRateRepository;

    @Autowired
    @Qualifier(value = "heartRateRepository")
    public void setHeartRateRepository(HeartRateRepository heartRateRepository) {
        this.heartRateRepository = heartRateRepository;
    }
    @Override
    public Iterable<HeartRate> listAllHeartRates()
    {
        return heartRateRepository.findAll();
    }
    @Override
    public HeartRate getHeartRateById(Integer id)
    {
        return heartRateRepository.findOne(id);
    }
    @Override
    public HeartRate saveHeartRate(HeartRate heartRate)
    {
       return heartRateRepository.save(heartRate);
    }
    @Override
    public List<HeartRate> listAllSearchedHeartRates(String dateStart, String dateFin){
       return heartRateRepository.findByDates(dateStart,dateFin);
    }
    @Override
    public  List<HeartRate> listAllSearchedHeartRatesByBand(String dateStart, String dateFin,String idBand){
        return heartRateRepository.findByDatesAndBand(dateStart,dateFin,idBand);
    }
    @Override
    public void deleteHeartRate(Integer id){
        heartRateRepository.delete(id);
    }
    @Override
    public List<HeartRate> listAllHeartRatesOfAnBand(String idBand){
        return heartRateRepository.findByBandId(idBand);
    }
}
