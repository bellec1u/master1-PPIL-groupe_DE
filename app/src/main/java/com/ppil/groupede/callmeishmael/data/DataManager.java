package com.ppil.groupede.callmeishmael.data;

import android.app.ActivityManager;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

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

    public String adresseDesti;

    public String result; // resultat de la requete

    public DataManager()
    {
        super();
        try {
            port = "8888";
            ipMachine = InetAddress.getLocalHost().getHostAddress();
            System.out.println("URL : "+ipMachine+":"+port);
            urlServeur = "http://"+ipMachine+":"+port+"/requetes/";
            urlLogin = "http://"+ipMachine+":"+port+"/requetes/login.php?";
            urlRegister = "http://"+ipMachine+":"+port+"/requetes/register.php?";
            urlBookDetail = "http://"+ipMachine+":"+port+"/requetes/bookDetails.php?";
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

    public void setUrlBookDetail(int id)
    {
        adresseDesti = urlBookDetail + "id=" + id;
    }
}
