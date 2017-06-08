package bo.ucbcba.taller.proyecto.proyecto.controllers;

import bo.ucbcba.taller.proyecto.proyecto.entities.Band;
import bo.ucbcba.taller.proyecto.proyecto.entities.HeartRate;
import bo.ucbcba.taller.proyecto.proyecto.services.BandService;
import bo.ucbcba.taller.proyecto.proyecto.services.HeartRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Jp on 02/06/2017.
 */
@RestController
public class HeartRateLogController
{
    private HeartRateService heartRateService;
    private BandService bandService;
    private SimpMessagingTemplate template;

    @Autowired
    public HeartRateLogController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Autowired
    public void setLocationService(HeartRateService heartRateService) {
        this.heartRateService = heartRateService;
    }

    @Autowired
    public void setBandService(BandService bandService) {
        this.bandService = bandService;
    }

    @RequestMapping(value = "/API/heartrateLog/{register}", method = RequestMethod.POST)
    public HeartRate saveLocation(@Valid HeartRate heartRate, @PathVariable Long register, BindingResult bindingResult, Model model) {
        //establezco el band por el bandId del heartrate
        int bandId=heartRate.getBandId();
        Band band=bandService.getBandById(bandId);
        heartRate.setBand(band);
        //establezco la fecha de subida
        Date uploadDate=new Date(System.currentTimeMillis());
        heartRate.setUploadDate(uploadDate);
        //establezco la fecha de registro, que se recibio como PathVariable
        Date registerDate=new Date(register);
        heartRate.setRegisterDate(registerDate);
        //guardo el Step en la base de datos
        heartRateService.saveHeartRate(heartRate);
        //informamos a los clientes suscritos a '/topic/general'
        this.template.convertAndSend("/topic/hr", "");
        return heartRate;
    }

    @SendTo("/topic/hr")
    @RequestMapping(value = "/API/getHRInner", method = RequestMethod.GET)
    public HeartRate getHR() {
        Integer promedio = 0;
        Iterator<HeartRate> iterator = heartRateService.listAllHeartRates().iterator();
        int cant=1;
        HeartRate hr = iterator.next();
        promedio+=hr.getRate();
        while(iterator.hasNext()){
            promedio += iterator.next().getRate();
            cant++;
        };
        promedio=promedio/cant;
        hr.setRate(promedio);
        return hr;
    }
}
