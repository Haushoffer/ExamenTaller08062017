package bo.ucbcba.taller.proyecto.proyecto.services;

import bo.ucbcba.taller.proyecto.proyecto.entities.HeartRate;
import bo.ucbcba.taller.proyecto.proyecto.entities.Location;
import bo.ucbcba.taller.proyecto.proyecto.entities.Step;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by CORE i7 on 31/05/2017.
 */
@Service
public interface LocationService {

    Iterable<Location> listAllLocations();

    Location getLocationById(Integer id);

    Location saveLocation(Location location);

    List<Location> listAllSearchedLocations(String dateStart, String dateFin);

    List<Location> listAllSearchedLocationsByBand(String dateStart, String dateFin,String idBand);

    void deleteLocation(Integer id);

    List<Location> listAllLocationsOfAnBand(String idBand);
    List<Location> listAllLocationsOfAnBandInOrder(String idBand);



}
