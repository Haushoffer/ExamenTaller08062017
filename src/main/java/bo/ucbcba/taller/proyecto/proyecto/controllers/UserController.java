package bo.ucbcba.taller.proyecto.proyecto.controllers;

import bo.ucbcba.taller.proyecto.proyecto.entities.*;
import bo.ucbcba.taller.proyecto.proyecto.services.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sun.plugin.PluginURLJarFileCallBack;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Juan on 11/05/2017.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BandService bandService;

    @Autowired
    private StepService stepServices;

    @Autowired
    private HeartRateService heartRateService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private SecurityService securityService;


    //@Autowired
    //private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationInit(Model model) {
        model.addAttribute("user", new User());

        return "registration";
    }

    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public String registration(Model model) {
        return "access-denied";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        ///userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(user);
        securityService.autologin(user.getUsername(), user.getPasswordConfirm());
        model.addAttribute("usuario", user);
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        String var;
        if (user.getBlocked()) {
            var = "redirect:/logout";
        } else {
            var = "welcome";
        }
        return var;
    }


    @RequestMapping(value = {"/admin/"}, method = RequestMethod.GET)
    public String admin(Model model) {
        return "welcome";
    }


    @RequestMapping(value = {"/bienvenidos"}, method = RequestMethod.GET)
    public String welcome2(Model model) {
        return "welcomeAdmin";
    }


    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("users", userService.listAllUsers());
        return "users";
    }

    @RequestMapping(value = "user", method = RequestMethod.POST)
    public String saveUser(User user) {
        userService.save(user);
        return "redirect:/myProfile";
    }

    @RequestMapping(value = "/admin/user/{id}", method = RequestMethod.GET)
    public String showUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "userShowAdm";
    }

    /*           */
    @RequestMapping(value = "admin/user/{id}/block", method = RequestMethod.GET)
    public String blockUser(@PathVariable Long id, Model model) {
        userService.findById(id).setBlocked(true);
        userService.update(userService.findById(id));
        model.addAttribute("users", userService.listAllUsers());
        return "users";
    }

    @RequestMapping(value = "admin/user/{id}/unlock", method = RequestMethod.GET)
    public String unlockUser(@PathVariable Long id, Model model) {
        userService.findById(id).setBlocked(false);
        userService.update(userService.findById(id));
        model.addAttribute("users", userService.listAllUsers());
        return "users";
    }

    /*             */
    @RequestMapping(value = "/myProfile", method = RequestMethod.GET)
    public ModelAndView viewProfile(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        modelAndView.addObject("name", user.getName());
        modelAndView.addObject("lastName", user.getLastName());
        modelAndView.addObject("userName", user.getUsername());
        modelAndView.addObject("weigth", user.getWeigth());
        modelAndView.addObject("heigth", user.getHeigth());
        String sexo;
        if (user.getGender()) {
            sexo = "Male";
        } else {
            sexo = "Female";
        }
        modelAndView.addObject("gender", sexo);
        modelAndView.addObject("age", user.getAge());
        modelAndView.setViewName("userShow");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/adminMode", method = RequestMethod.GET)
    public ModelAndView adminMode(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        modelAndView.setViewName("welcomeAdmin");
        return modelAndView;
    }

    @RequestMapping(value = "/myBands", method = RequestMethod.GET)
    public ModelAndView viewBands(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        String idUser = String.valueOf(user.getId());
        List<Band> bandsList = bandService.getBandsByUserId(idUser);
        modelAndView.addObject("bands", bandsList);
        modelAndView.setViewName("bands");
        return modelAndView;
    }

    @RequestMapping(value = "/todayInfo", method = RequestMethod.GET)
    public ModelAndView viewStats() {

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        String idUser = String.valueOf(user.getId());


        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        String today = dateFormat.format(date);
        String showableDate = today;
        today = today.replace("/", "");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        String tomorrow = dateFormat.format(calendar.getTime());

        tomorrow = tomorrow.replace("/", "");

        List<Band> bandsList = bandService.getBandsByUserId(idUser);
        List<Step> steps = new ArrayList<Step>();
        List<HeartRate> rates = new ArrayList<>();
        List<Location> locations = new ArrayList<>();

        for (int j = 0; j < bandsList.size(); j++) {
            String idBand = String.valueOf(bandsList.get(j).getId());

            List<Step> auxSteps = stepServices.listAllSearchedStepsByBand(today, tomorrow, idBand);
            List<HeartRate> auxRates = heartRateService.listAllSearchedHeartRatesByBand(today, tomorrow, idBand);
            List<Location> auxLocations = locationService.listAllSearchedLocationsByBand(today, tomorrow, idBand);
            steps.addAll(auxSteps);
            rates.addAll(auxRates);
            locations.addAll(auxLocations);
        }

        Integer aditionResult = 0;
        Integer hRate = 0;

        for (int i = 0; i < steps.size(); i++) {
            aditionResult = aditionResult + steps.get(i).getQuantity();

        }
        for (int i = 0; i < rates.size(); i++) {
            hRate = hRate + rates.get(i).getRate();
        }
        int average = 1;
        if (rates.size() > 0) {
            average = rates.size();
        }


        modelAndView.addObject("cantSteps", aditionResult);

        modelAndView.addObject("rateToday", hRate / average);

        modelAndView.addObject("showableDate", showableDate);

        modelAndView.addObject("locations", locations);

        modelAndView.setViewName("stepUserShow");


        return modelAndView;
    }

    @RequestMapping(value = "/myStatsSteps", method = RequestMethod.GET)
    public ModelAndView listUserSteps(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        String idUser = String.valueOf(user.getId());

        List<Band> bandsList = bandService.getBandsByUserId(idUser);
        List<Step> steps = new ArrayList<Step>();
        for (int j = 0; j < bandsList.size(); j++) {
            String idBand = String.valueOf(bandsList.get(j).getId());

            List<Step> auxSteps = stepServices.listAllStepsOfAnBand(idBand);
            steps.addAll(auxSteps);

        }
        modelAndView.addObject("steps", steps);
        modelAndView.setViewName("stepsUser");

        return modelAndView;
    }

    @RequestMapping(value = "/admin/searchUser", method = RequestMethod.POST)
    public String buscarUsuario(String mail, Model model) {
        User user = userService.findByUsername(mail);
        if (user != null) {
            return "redirect:/admin/user/" + user.getId();
        } else {
            model.addAttribute("users", userService.listAllUsers());
            return "users";
        }
    }

    @RequestMapping(value = "/mySearchResultsSteps", method = RequestMethod.POST)
    public ModelAndView listResults(String dateStart, String dateEnd, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        String idUser = String.valueOf(user.getId());

        String dateTime = dateEnd;

        DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-mm-dd");

        DateTime jodatime = dtf.parseDateTime(dateTime);

        DateTime dtPlusOne = jodatime.plusDays(1);

        dateTime = dtPlusOne.toString("YYYY-mm-dd");

        dateStart = dateStart.replace("-", "");
        dateTime = dateTime.replace("-", "");

        List<Band> bandsList = bandService.getBandsByUserId(idUser);
        List<Step> steps = new ArrayList<Step>();
        for (int j = 0; j < bandsList.size(); j++) {
            String idBand = String.valueOf(bandsList.get(j).getId());

            List<Step> auxSteps = stepServices.listAllSearchedStepsByBand(dateStart, dateTime, idBand);
            steps.addAll(auxSteps);

        }

        modelAndView.addObject("dateStart", dateStart);
        modelAndView.addObject("dateEnd", dateTime);

        modelAndView.addObject("steps", steps);
        modelAndView.setViewName("stepsUser");

        return modelAndView;
    }

    @RequestMapping(value = "/myStatsLocations", method = RequestMethod.GET)
    public ModelAndView listLocations(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        String idUser = String.valueOf(user.getId());

        List<Band> bandsList = bandService.getBandsByUserId(idUser);
        List<Location> locations = new ArrayList<Location>();
        for (int j = 0; j < bandsList.size(); j++) {
            String idBand = String.valueOf(bandsList.get(j).getId());

            List<Location> auxLocation = locationService.listAllLocationsOfAnBand(idBand);
            locations.addAll(auxLocation);

        }
        modelAndView.addObject("locations", locations);
        modelAndView.setViewName("locationsUser");

        return modelAndView;
    }

    @RequestMapping(value = "/searchResultsLocation", method = RequestMethod.POST)
    public ModelAndView listResultsLocation(String dateStart, String dateEnd, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        String dateTime = dateEnd;

        DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-mm-dd");

        DateTime jodatime = dtf.parseDateTime(dateTime);

        DateTime dtPlusOne = jodatime.plusDays(1);

        dateTime = dtPlusOne.toString("YYYY-mm-dd");

        dateStart = dateStart.replace("-", "");
        dateTime = dateTime.replace("-", "");

        modelAndView.addObject("dateStart", dateStart);
        modelAndView.addObject("dateEnd", dateTime);

        modelAndView.addObject("locations", locationService.listAllSearchedLocations(dateStart, dateTime));
        modelAndView.setViewName("locationsUser");

        return modelAndView;
    }

    @RequestMapping(value = "/myStatsHR", method = RequestMethod.GET)
    public ModelAndView listHR(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        String idUser = String.valueOf(user.getId());

        List<Band> bandsList = bandService.getBandsByUserId(idUser);
        List<HeartRate> heartRates = new ArrayList<HeartRate>();
        for (int j = 0; j < bandsList.size(); j++) {
            String idBand = String.valueOf(bandsList.get(j).getId());

            List<HeartRate> auxHR = heartRateService.listAllHeartRatesOfAnBand(idBand);
            heartRates.addAll(auxHR);

        }
        modelAndView.addObject("rates", heartRates);
        modelAndView.setViewName("ratesUser");

        return modelAndView;
    }

    @RequestMapping(value = "/searchResultsHeart", method = RequestMethod.POST)
    public ModelAndView listResultsHR(String dateStart, String dateEnd, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        String dateTime = dateEnd;

        DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-mm-dd");

        DateTime jodatime = dtf.parseDateTime(dateTime);

        DateTime dtPlusOne = jodatime.plusDays(1);

        dateTime = dtPlusOne.toString("YYYY-mm-dd");

        dateStart = dateStart.replace("-", "");
        dateTime = dateTime.replace("-", "");

        modelAndView.addObject("dateStart", dateStart);
        modelAndView.addObject("dateEnd", dateTime);

        modelAndView.addObject("rates", heartRateService.listAllSearchedHeartRates(dateStart, dateTime));
        modelAndView.setViewName("ratesUser");

        return modelAndView;
    }

    @RequestMapping(value = "/addBand", method = RequestMethod.GET)
    public String addBandGet(Model model) {
        return "/addBand";

    }

    @RequestMapping(value = "/addBand", method = RequestMethod.POST)
    public String addBandPost(String modelo, String serial, Model model) {
        Band newBand = new Band();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        if (bandService.thisSerialIsNew(serial).size() >= 1) {
            return "redirect:/addBand";
        } else {
            newBand.setModelo(modelo);
            newBand.setSerial(serial);
            newBand.setUser(user);
            bandService.saveBand(newBand);
            return "redirect:/myBands";
        }
    }

    @RequestMapping(value = "user/edit", method = RequestMethod.GET)
    public String editProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("user", user);
        return "editForm";
    }

    @RequestMapping(value = "user/edit", method = RequestMethod.POST)
    public String editProfilePost(@ModelAttribute("user") User user, Model model) {

        userService.update(user);
        return "redirect:/myProfile";
    }

    @RequestMapping(value = "/distance", method = RequestMethod.GET)
    public ModelAndView    getDistance() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        String idUser = String.valueOf(user.getId());
        List<Location> locations = new ArrayList<Location>();

        List<Band> bandsList = bandService.getBandsByUserId(idUser);

        for (int j = 0; j < bandsList.size(); j++) {
            String idBand = String.valueOf(bandsList.get(j).getId());
            List<Location> auxLocations = locationService.listAllLocationsOfAnBandInOrder(idBand);

            locations.addAll(auxLocations);
        }
        int resultado =0;
        int auxMaximo = 0 ;
        for (int first = 0; first<locations.size() - 1;first++)
        {
            int aux = first +1;
            Location from=locations.get(first);
            Location to = locations.get(aux);
            int preDistancia = ((int)Math.sqrt(
                    ((int)Math.pow((from.getLatitude()+to.getLatitude()),2))+
                            ((int)Math.pow((from.getLongitude()+to.getLongitude()),2))));

            if (preDistancia >= 1000)
            {
                preDistancia = 0;
                locationService.deleteLocation(to.getId());
            }
            resultado = resultado +preDistancia;


        }

        modelAndView.addObject("distance", resultado);

        modelAndView.setViewName("distance");

        return modelAndView;
    }
}

