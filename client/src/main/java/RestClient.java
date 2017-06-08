import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Client.Client;
import Client.Location;
import Client.HeartRate;
import Client.Step;
import org.apache.http.NameValuePair;

public class RestClient {

    public static void main(String[] args) throws InterruptedException {
        double latAux=1;
        double longAux = 3;         //limite 1044 200,500 500,100
        int contAux = 1;


            while(true) {
                int sleep;
                int id = 1;//id de la banda

                //crear objeto step
                long registration = System.currentTimeMillis();//tiempo de registro

                //crear objeto location
                double lat=latAux;
                double lng=longAux;
                if (contAux % 2 == 0) {
                    registration = System.currentTimeMillis();//tiempo de registro
                }
                else
                {
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_YEAR, -1);
                    registration = calendar.getTimeInMillis();

                }
                Location location = new Location(0,0,0);
                if (contAux == 10)
                {
                    location.setLat(200);
                    location.setLng(500);
                    location.setRegistrationDate(registration);
                }
                if (contAux == 12)
                {
                    location.setLat(500);
                    location.setLng(500);
                    location.setRegistrationDate(registration);
                }
                if(contAux == 11)
                {
                    location.setLat(lat);
                    location.setLng(lng);
                    location.setRegistrationDate(registration);
                }
                if(contAux <10)
                {
                    location.setLat(lat);
                    location.setLng(lng);
                    location.setRegistrationDate(registration);
                }
                if(contAux >12)
                {
                    location.setLat(lat);
                    location.setLng(lng);
                    location.setRegistrationDate(registration);
                }




                ////TIPO DE DATO STEP
                int cantSteps = 11;

                //crear objeto heartrate
                int bpm=(int) (Math.random() * 80) + 40;
                registration = System.currentTimeMillis();//tiempo de registro
                HeartRate hr=new HeartRate(bpm,registration);
                Step step = new Step(cantSteps,registration);
                //duerme el sistema de 1.5 seg hasta 5 seg
                try {
                    sleep=(int) (Math.random() * 5000) + 1500;
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //crear cliente con id definido
                Client client=new Client(id);
                //setear

                client.setLocation(location);
                client.setHeartRate(hr);
                client.setStep(step);
                //seetear parametros step

                //ejecutar steps

                //setear parametros location
                ArrayList<NameValuePair> parameters=client.parametersLocation();
                //ejecutar location

                client.execute("http://localhost:8080/API/locationLog/" + location.getRegistrationDate(),parameters);
                //parametors HeartRate
                parameters=client.parametersHeartRate();
                //ejecutar HeartRate
                client.execute("http://localhost:8080/API/heartrateLog/" + location.getRegistrationDate(),parameters);

                parameters=client.parametersStep();

                client.execute("http://localhost:8080/API/stepLog/" + step.getRegisterDate(),parameters);
                //apagar cliente
                client.shutDown();

                longAux++;
                latAux++;
                contAux++;

            }


    }
}