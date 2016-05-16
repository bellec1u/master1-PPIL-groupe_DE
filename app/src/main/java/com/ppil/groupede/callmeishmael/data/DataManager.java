package com.ppil.groupede.callmeishmael.data;

import android.app.ActivityManager;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ListIterator;

/**
 * Created by Pima on 10/05/16.
 */
public class DataManager implements Runnable {


    private URL urlBase; // votre @IP ( pas la localhost )

    public String ipMachine;

    public String port;

    public static String urlServeur;

    public static String urlLogin;

    public static String urlRegister;

    public static String urlBookDetail;

    public static String urlTop10;

    public static String urlInfoClient;

    public String adresseDesti;

    public String result; // resultat de la requete

    public DataManager()
    {
        super();
        try {
            /*
                A remplacer par votre port, celui de la base de donnée
             */
            port = "8888";
            ipMachine = InetAddress.getLocalHost().getHostAddress();
            /*
                A remplacer par votre adresse IP, ipconfig ou ifconfig
             */
            ipMachine = "192.168.1.10";
            urlServeur = "http://"+ipMachine+":"+port+"/requetes/";
            urlLogin = "http://"+ipMachine+":"+port+"/requetes/login.php?";
            urlRegister = "http://"+ipMachine+":"+port+"/requetes/register.php?";
            urlBookDetail = "http://"+ipMachine+":"+port+"/requetes/bookDetails.php?";
            urlTop10 = "http://"+ipMachine+":"+port+"/requetes/top10.php";
            urlInfoClient = "http://"+ipMachine+":"+port+"/requetes/infoClient.php?";

            adresseDesti = "";
            result = "";
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        String response = "";
        try {
            URL serv = new URL(adresseDesti);
            HttpURLConnection urlConnection = (HttpURLConnection) serv.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder sb = new StringBuilder("");
            line = reader.readLine();
            sb.append(line);
            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
            result = sb.toString();
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAdresseDesti(String adr)
    {
        adresseDesti = adr;
    }

    public String getAdresseDesti()
    {
        return adresseDesti;
    }

    public String getResult()
    {
        return result;
    }

    public void setUrlLogin(String email, String pwd)
    {
        adresseDesti = urlLogin + "email=" + email + "&password=" + pwd;
    }

    public void setUrlBookDetail(String id)
    {
        adresseDesti = urlBookDetail + "id=" + id;
    }

    public void setUrlRegister(String prenom, String nom, String email, String password, String sexe, String date)
    {
        adresseDesti = urlRegister +
                "email=" +email +
                "&password=" + password +
                "&prenom=" + prenom +
                "&nom=" + nom +
                "&sexe=" + sexe +
                "&date=" + date;
    }

    public void setUrl10()
    {
        adresseDesti = urlTop10 ;
    }

    // not used
    public JSONArray getJSONArray() {
        try {
            result.replaceAll("\\\\","");
            JSONObject o = new JSONObject(result);
            return o.getJSONArray("cover_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    public JSONArray toJSONArray()
    {
        JSONArray o = null;
        try {
            o = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o;
    }

    public void setUrlInfoClient(String email)
    {
        adresseDesti = urlInfoClient + "email=" + email;
    }
}
