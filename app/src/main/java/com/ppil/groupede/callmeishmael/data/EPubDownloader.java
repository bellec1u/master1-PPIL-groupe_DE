package com.ppil.groupede.callmeishmael.data;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.MainActivity;
import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.fragment.AccueilFragment;
import com.ppil.groupede.callmeishmael.fragment.DetailsLivreFragment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Pima on 19/05/16.
 */
public class EPubDownloader extends AsyncTask<Object,String,String>{

    /*
        Classe appelée lorsque l'utilisateur clique sur le bouton 'Ajouter à ma liste de lecture'
        de ce fait lorsque le onClick est déclenché alors le téléchargement de l'EPUB du livre
        représenté par la classe DetailsLivreFragment est lancé. //todo
        Lorsque ce téléchargement est terminé, le livre est alors ajouté à la liste de lecture
     */
    /*
        Effectue une requete http afin d'ajouter un livre dans la liste de lecture d'un utilisateur,
        et (à faire) d'y télécharger le ePub correspondant.
     */
    @Override
    protected String doInBackground(Object... params) {
        String response = ""; // attribut contenant notre futur résultat
        try {
            /*
                On parcourt tous les paramètres
             */
            String url = (String) params[0];
            URL serv = new URL(url); // on instancie l'URL à atteindre
                /*
                    On établit la connexion avec le serveur
                 */
            HttpURLConnection urlConnection = (HttpURLConnection) serv.openConnection();
                /*
                    On utilise la méthode GET pour le transfert de données
                 */
            urlConnection.setRequestMethod("POST");

            if(params.length > 1) {
                byte[] infos = (byte[]) params[1];
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("Content-Length", String.valueOf(infos.length));
                urlConnection.setDoOutput(true);
                urlConnection.getOutputStream().write(infos);
            }
                /*
                    On récupère le résultat dans in
                 */
            System.out.println(urlConnection.toString());
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
        } catch (MalformedURLException e) {
            e.printStackTrace(); // pas atteignable logiquement !
        } catch (IOException e) {
            e.printStackTrace(); // pas atteignable logiquement !
        }
        return response; // résultat contenant la réponse du serveur retourné...
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

}
