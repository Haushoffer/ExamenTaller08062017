package bo.ucbcba.taller.proyecto.proyecto.controllers;

import bo.ucbcba.taller.proyecto.proyecto.entities.Band;
import bo.ucbcba.taller.proyecto.proyecto.entities.Location;
import bo.ucbcba.taller.proyecto.proyecto.services.BandService;
import bo.ucbcba.taller.proyecto.proyecto.services.LocationService;
import bo.ucbcba.taller.proyecto.proyecto.services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * Created by JOEL on 01/06/2017.
 */
@RestController
public class LocationLogController {

    private LocationService locationService;
    private BandService bandService;

    @Autowired
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    @Autowired
    public void setBandService(BandService bandService) {
        this.bandService = bandService;
    }

    @RequestMapping(value = "/API/locationLog/{register}", method = RequestMethod.POST)
    public Location saveLocation(@Valid Location location, @PathVariable Long register, BindingResult bindingResult, Model model) {
        //establezco el band por el bandId del location
        int bandId=location.getBandId();
        Band band=bandService.getBandById(bandId);
        location.setBand(band);
        //establezco la fecha de subida
        Date uploadDate=new Date(System.currentTimeMillis());
        location.setUploadDate(uploadDate);
        //establezco la fecha de registro, que se recibio como PathVariable
        Date registerDate=new Date(register);
        location.setRegisterDate(registerDate);
        //guardo el Step en la base de datos
        locationService.saveLocation(location);
        return location;
    }

    @RequestMapping(value = "/API/locationsLog", method = RequestMethod.GET)
    public Iterable<Location> listLocations( ) {
        Iterable<Location> stepLogs = locationService.listAllLocations();
        return stepLogs;
    }

    @RequestMapping(value = "/API/delete/location/{id}", method = RequestMethod.DELETE)
    public Location deleteLocation(@PathVariable Integer id) {
        locationService.deleteLocation(id);
        return null;
    }

    @RequestMapping(value = "/API/locationLog/{id}", method = RequestMethod.GET)
    public Location getLocation(@PathVariable Integer id)
    {
        Location location=locationService.getLocationById(id);
        location.setBandId(location.getBand().getId());
        return location;
    }
}
