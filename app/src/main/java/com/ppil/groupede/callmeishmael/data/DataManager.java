package com.ppil.groupede.callmeishmael.data;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Pima on 16/05/16.
 */
public class DataManager extends AsyncTask<String, String, String> {

    /*
        Classe appelée lorsque l'on a besoin de recevoir/envoyé des requetes à la base de donnée
        cette derniere prend en paramètre la future classe receveuse.
        Par exemple si la classe ConnexionFragment souhaite vérifier la connexion d'un utilisateur
        Il faudra faire dans la classe ConnexionFragment:
         DataManager dm = new DataManager(this);

         dm lorsqu'il aura terminé ses requetes executera la méthode receiveData de ConnexionFragment
     */
    private DataReceiver receiver; // classe appelée lorsque asyntask a finis ses calculs...

    /*
        Constructeur de DataManager,
        le receveur dataReceiver est le seul parametre
     */
    public DataManager(DataReceiver dataReceiver)
    {
        super();
        receiver = dataReceiver; // on y affecte le receveur
    }

    /*
        Fonction appelée avant le doInBackground,
        nous n'utilisons pas cette fonctionnalité dans notre application
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /*
        Fonction appelée lorsque DataManager a terminé sa fonction doInBackground
        res contient le resultat des requetes faites dans la fonction précèdente.
     */
    @Override
    protected void onPostExecute(String res) {
        super.onPostExecute(res);
        if(receiver != null){
        receiver.receiveData(res);}
    }

    /*
        Fonction chargée de faire en 'arriere plan' des requetes entre notre client et le serveur mysql
        elle retourne le resultat sous forme de String, ce resultat sera ensuite traité par la fonction
        onPostExecute.
        Les parametres de doInBackground, sont les urls à atteindre, dans notre cas, seul 1 URL sera
        en parametre à chaque fois.
        String... params aura été remplis préalablement grace à la classe Data
     */
    @Override
    protected String doInBackground(String... params) {
        String response = ""; // attribut contenant notre futur résultat
        try {
            /*
                On parcourt tous les paramètres
             */
            for(String url : params) {
                URL serv = new URL(url); // on instancie l'URL à atteindre
                /*
                    On établit la connexion avec le serveur
                 */
                HttpURLConnection urlConnection = (HttpURLConnection) serv.openConnection();
                /*
                    On utilise la méthode GET pour le transfert de données
                 */
                urlConnection.setRequestMethod("GET");
                /*
                    On récupère le résultat dans in
                 */
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in)); // on le lit ici...
                /*
                    Maintenant on le parcourt, puis on affectera à res le resultat.
                 */
                String line;
                StringBuilder sb = new StringBuilder("");
                line = reader.readLine();
                sb.append(line);
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                response = sb.toString(); // résultat affecté ici !
                urlConnection.disconnect(); // on ferme la connexion
            }
        } catch (MalformedURLException e) {
            e.printStackTrace(); // pas atteignable logiquement !
        } catch (IOException e) {
            e.printStackTrace(); // pas atteignable logiquement !
        }
        return response; // résultat contenant la réponse du serveur retourné...
    }
}
