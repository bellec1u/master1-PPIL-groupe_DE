package com.ppil.groupede.callmeishmael.data;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

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
public class EPubDownloader extends AsyncTask<String,Integer,String> implements View.OnClickListener{

    /*
        Classe appelée lorsque l'utilisateur clique sur le bouton 'Ajouter à ma liste de lecture'
        de ce fait lorsque le onClick est déclenché alors le téléchargement de l'EPUB du livre
        représenté par la classe DetailsLivreFragment est lancé. //todo
        Lorsque ce téléchargement est terminé, le livre est alors ajouté à la liste de lecture
     */
    private DetailsLivreFragment page;

    private Context context;

    public EPubDownloader(DetailsLivreFragment pageCourante, Context leContext)
    {
        page = pageCourante;
        context = leContext;
    }

    /*
        Effectue une requete http afin d'ajouter un livre dans la liste de lecture d'un utilisateur,
        et (à faire) d'y télécharger le ePub correspondant.
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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context," Le livre a été ajouté avec succès !",Toast.LENGTH_SHORT).show();
    }

    /*
        Fonction déclenchée lorsque l'utilisateur clique sur le bouton
    */
    @Override
    public void onClick(View v) {
        /*
            Je recupere l'URL nécessaire pour ajouter un livre à ma page de donnée
            (non local)
            On recupere l'email de l'utilisateur connecté
         */
        SessionManager sessionManager = new SessionManager(context);
        String adresse = Data.getData().getURLAJouterLivre(sessionManager.getSessionEmail(),page.getIdLivre());
        System.out.println(adresse);
        execute(adresse); // on demande à acceder à cette requete
    }
}
