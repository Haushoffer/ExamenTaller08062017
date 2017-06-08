package bo.ucbcba.taller.proyecto.proyecto.services;

import bo.ucbcba.taller.proyecto.proyecto.entities.Location;
import bo.ucbcba.taller.proyecto.proyecto.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CORE i7 on 31/05/2017.
 */
 @Service
public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;

    @Autowired
    @Qualifier(value = "locationRepository")
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Iterable<Location> listAllLocations()
    {
        return locationRepository.findAll();
    }
    @Override
    public Location getLocationById(Integer id)
    {
        return locationRepository.findOne(id);
    }
    @Override
    public Location saveLocation(Location location)
    {
        return locationRepository.save(location);
    }
    @Override
    public List<Location> listAllSearchedLocations(String dateStart, String dateFin){
        return locationRepository.findByDates(dateStart,dateFin);
    }
    @Override
    public  List<Location> listAllSearchedLocationsByBand(String dateStart, String dateFin,String idBand){
        return locationRepository.findByDatesAndBand(dateStart,dateFin,idBand);
    }
    @Override
    public void deleteLocation(Integer id){
        locationRepository.delete(id);
    }
    @Override
    public List<Location> listAllLocationsOfAnBand(String idBand){
        return locationRepository.findByBandId(idBand);
    }
    @Override
    public  List<Location> listAllLocationsOfAnBandInOrder(String idBand){
        return locationRepository.findByBandIdInOrder(idBand);
    }

}
