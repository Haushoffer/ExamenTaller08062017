package Client;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Jp on 02/06/2017.
 */
public class Client
{

    private DefaultHttpClient httpClient;
    private int band;
    private Location location;
    private HeartRate heartRate;
    private Step step;
    public Client(int band)
    {
        this.setBand(band);
        httpClient = new DefaultHttpClient();
    }


    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }


    public ArrayList<NameValuePair> parametersLocation()
    {
        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("latitude", location.getLat() + ""));
        postParameters.add(new BasicNameValuePair("longitude", location.getLng() + ""));
        postParameters.add(new BasicNameValuePair("bandId", getBand() + ""));
        return postParameters;
    }
    public ArrayList<NameValuePair> parametersStep()
    {
        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("quantity", step.getQuantity() + ""));
        postParameters.add(new BasicNameValuePair("bandId", getBand() + ""));
        return postParameters;
    }

    public ArrayList<NameValuePair> parametersHeartRate()
    {
        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("rate", heartRate.getBpm() + ""));
        postParameters.add(new BasicNameValuePair("bandId", getBand() + ""));
        return postParameters;
    }

    public void execute(String url,ArrayList<NameValuePair> postParameters)
    {
        try
        {
            HttpPost postRequest = new HttpPost(url);
            postRequest.setEntity(new UrlEncodedFormEntity(postParameters));
            postRequest.addHeader("accept", "application/json");
            HttpResponse response = httpClient.execute(postRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }
            output(response);
        }
        catch (ClientProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void shutDown()
    {
        httpClient.getConnectionManager().shutdown();
    }

    private void output(HttpResponse response)
    {
        try
        {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }
        }
        catch (IOException e) {

            e.printStackTrace();
        }
    }

    public int getBand() {
        return band;
    }

    public void setBand(int band) {
        this.band = band;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public HeartRate getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(HeartRate heartRate) {
        this.heartRate = heartRate;
    }



}
