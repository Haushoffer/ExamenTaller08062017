package bo.ucbcba.taller.proyecto.proyecto.controllers;

import bo.ucbcba.taller.proyecto.proyecto.services.HeartRateService;
import bo.ucbcba.taller.proyecto.proyecto.services.LocationService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by CORE i7 on 31/05/2017.
 */
@Controller
public class HeartRateController {

    HeartRateService heartRateService;

    @Autowired
    public void setHeartRateService(HeartRateService heartRateService) {
        this.heartRateService = heartRateService;
    }

    @RequestMapping(value = "/admin/rates", method = RequestMethod.GET)
    public String list(Model model) {

        model.addAttribute("rates", heartRateService.listAllHeartRates());
        return "rates";
    }

    @RequestMapping(value = "/admin/searchResultsHeart", method = RequestMethod.POST)
    public String listResultsHearth(String dateStart, String dateEnd,Model model){

        String dateTime = dateEnd;

        DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-mm-dd");

        DateTime jodatime = dtf.parseDateTime(dateTime);

        DateTime dtPlusOne = jodatime.plusDays(1);

        dateTime =dtPlusOne.toString("YYYY-mm-dd");

        dateStart=dateStart.replace("-", "");
        dateTime=dateTime.replace("-", "");

        model.addAttribute("dateStart", dateStart);
        model.addAttribute("dateEnd", dateTime);

        model.addAttribute("rates",heartRateService.listAllSearchedHeartRates(dateStart,dateTime));

        return "rates";
    }
}
