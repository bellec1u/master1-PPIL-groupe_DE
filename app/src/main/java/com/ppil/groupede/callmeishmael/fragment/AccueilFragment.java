package com.ppil.groupede.callmeishmael.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.SingletonBackPressed;
import com.ppil.groupede.callmeishmael.data.BitmapManager;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.DataReceiver;
import com.ppil.groupede.callmeishmael.data.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


/**
 * Created by Pima on 16/05/16.
 */
public class AccueilFragment extends Fragment implements DataReceiver {

    /*
        Classe affichant l'accueil de l'application,
        cette dernière sert de passerelle à d'autres fragments comme :
        DetailsLivreFragment par exemple.

        Cette classe possède le top10 représenté par 10 ImageButtons ayant comme image
        la couverture du livre.

        On pourra aussi à partir de cette classe avoir accès aux suggestions de lecture ainsi
        qu'à la liste de lecture de l'utilisateur.
        Ces deux fonctionnalités seront aussi représentés par des ImageButtons. (pas encore implémenté)

        Cette classe implémente Fragment car elle a pour rôle de s'insérer dans le MainActivity,
        elle implement aussi DataReceiver de facon à pouvoir traiter plus facilement les données
        envoyés par la classe DataManager.

        Au premier load, elle effectuera une requete vers la base afin de récupérer le top10,
        puis gardera en mémoire ce dernier afin d'éviter de refaire des requetes inutilement
     */

    private boolean firstLoad; // booléen permettant de savoir si la classe est chargée pour la premiere fois ou non
    public static int nbTop = 10; // static contenant le nombre de livre affiché dans le top(ici 10)
    private ImageButton[] top; // tableau contenant les nbTop ImageButton
    private int hauteur; // hauteur de chaque image (cover d'un livre)
    private int largeur; // largeur de chaque image (cover d'un livre)

    private GridLayout leTop; // gridLayout contenant les livres du top 10.
    private GridLayout suggestion; // gridLayout contenant les livres suggérer pour l'utilisateur
    private GridLayout listeLecture; // liste de lecture de l'utilisateur

    // ImageButton de la liste de lecture
    private ImageButton[] boutonListe;

    //info Liste, textView present seulement si la liste de lecture est vide
    private TextView infoLivre;

    /*
        Constructeur d'AccueilFragment, initialement vide...
     */
    public AccueilFragment() {
        // Required empty public constructor
        firstLoad = true; // premier chargement à faux : logique
        /*
            On instancie top
         */
        top = new ImageButton[nbTop];
    }


    /*
        Fonction permettant de créer et de retourner le Fragment avec les divers
        élements contenus dans ce dernier...
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accueil, container, false);
        infoLivre = (TextView) view.findViewById(R.id.infoListe);
        infoLivre.setVisibility(View.GONE);

        /*
            On affecte aux 3 GridLayouts le bon élément du layout
         */
        leTop = (GridLayout) view.findViewById(R.id.top_dix);
        suggestion = (GridLayout) view.findViewById(R.id.liste_des_suggestions);
        listeLecture = (GridLayout) view.findViewById(R.id.liste_de_lecture);

        /*
            On donne le nom au fragment : Accueil
         */
        this.getActivity().setTitle("Accueil");

        // ---------- ---------- ---------- ---------- ---------- top 10

        int nbLivre = nbTop; // livres à contenir dans la page
        /*
            On prepare la mémoire pour les ImageButtons
         */
        for(int i = 0 ; i < nbTop ; i++)
        {
            top[i] = new ImageButton(view.getContext()); // instanciation ici...
        }
        int nbColonnes = 3;
        int nbLignes = ((int)(nbLivre/3)) + 1;

        /*
            Mise en place du GridLayout TOP 10
        */
        leTop.removeAllViews();
        leTop.setColumnCount( nbColonnes );
        leTop.setRowCount( nbLignes );


        /*
            On instancie DataManager
         */
            DataManager dataManager = new DataManager(this);
        /*
            On recupère l'URL nécessaire pour récupérer les informations du top(nbTop) grace à la classe Data
         */
            String url = Data.getData().getTop10();

        /*
            On demande maintenant au serveur les informations,
            cette requete sera faite en arriere plan par rapport à UIThread
            donc il n'y aura plus de latence de chargement, mais par contre
            les résultats risquent de mettre un tout petit peu plus de résultat à apparaitre.
            (dépend bien sur de la vitesse de connexion utilisée !)
         */
            try {
                String wait = dataManager.execute(url).get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }

        for (int i = 0, c = 0, r = 0; i < nbLivre; i++, c++) {
            if (c == nbColonnes) {
                c = 0;
                r++;
            }

            largeur = (int) (100 * getResources().getDisplayMetrics().density + 0.5f);
            hauteur = (int) (150 * getResources().getDisplayMetrics().density + 0.5f);

            top[i].setMinimumWidth(largeur);
            top[i].setMinimumHeight(hauteur);

            GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(GridLayout.spec(r), GridLayout.spec(c));
            leTop.addView(top[i], gridParam);
        }


        /*
            SI l'utilisateur n'est pas connecté, il n'y a pas de liste de suggestion ni de liste de lecture
            On verifie ca grace à SessionManager
         */
        listeLecture = (GridLayout) view.findViewById(R.id.liste_de_lecture);

        SessionManager sessionManager = new SessionManager(getContext());
        if(!sessionManager.isConnected())
        {
            //setSuggestion();
            suggestion.setVisibility(View.GONE);
            TextView sug = (TextView) view.findViewById(R.id.textSuggestion);
            sug.setVisibility(View.GONE);
            View barre = (View) view.findViewById(R.id.barre2);
            barre.setVisibility(View.GONE);
            setListeDeLecture();
        }
        else
        {
            listeLecture.setVisibility(View.GONE);
            suggestion.setVisibility(View.GONE);
            TextView sug = (TextView) view.findViewById(R.id.textSuggestion);
            sug.setVisibility(View.GONE);
            TextView lecture = (TextView) view.findViewById(R.id.textListeLecture);
            lecture.setVisibility(View.GONE);
            View barre = (View) view.findViewById(R.id.barre3);
            barre.setVisibility(View.GONE);
        }
            // ---------- ---------- ---------- ---------- ---------- Liste de suggestion

         /*   nbLivre = 8;
            nbColonnes = 3;
            nbLignes = ((int) (nbLivre / 3)) + 1;

            suggestion = (GridLayout) view.findViewById(R.id.liste_des_suggestions);
            suggestion.removeAllViews();
            suggestion.setVisibility(View.GONE);
            suggestion.setColumnCount(nbColonnes);
            suggestion.setRowCount(nbLignes);

            for (int i = 0, c = 0, r = 0; i < nbLivre; i++, c++) {
                if (c == nbColonnes) {
                    c = 0;
                    r++;
                }

                ImageButton ib = new ImageButton(view.getContext());

                largeur = (int) (100 * getResources().getDisplayMetrics().density + 0.5f);
                hauteur = (int) (150 * getResources().getDisplayMetrics().density + 0.5f);

                ib.setMinimumWidth(largeur);
                ib.setMinimumHeight(hauteur);
                ib.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        Toast.makeText(getActivity(), "ImageButton is clicked!", Toast.LENGTH_SHORT).show();

                    }

                });

                GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(GridLayout.spec(r), GridLayout.spec(c));
                suggestion.addView(ib, gridParam);
            }
        }*/
        firstLoad = false; // la fonction aura donc été appelée au moins 1 fois

        //change d'état le bouton de retour
        SingletonBackPressed.getInstance().setCanBackView(false);
        return view;
    }

    /*
        Fonction permettant de traiter les résultats recu par une requete http (GET) du serveur,
        dans ce Fragment, on récupera ici les informations du top10 etc...
     */
    @Override
    public void receiveData(String resultat) {
        try {
            JSONArray array = new JSONArray(resultat) ; // convertie le résultat en JSONArray
            /*
                On parcourt ensuite le JSONArray et on instancie les images correspondantes
             */
            for(int i = 0 ; i  < array.length() ; i++){
                // On recupere le premier élement dans un Object, puis en JSONObject
                Object object = array.get(i);
                JSONObject jsonObject = new JSONObject(object.toString()); // instanciation du JSONObject

                /*
                    Utilisation de la classe BitmapManager pour charger dans un Bitmap
                    une image venant d'un URL
                */
                Bitmap bitmap = null ; // parametre d'ImageManager
                BitmapManager bitmapManager = new BitmapManager(bitmap); // instanciation ici...
                /*
                    On execute bitmapManager, donc requete,
                    le get() fait en sorte que l'UIThread attend le resultat.
                 */
                bitmap = bitmapManager.execute(jsonObject.getString("cover_url")).get();
                if(bitmap != null) {
                /*
                    On redimensionne l'image (si besoin est)
                 */
                    bitmap = Bitmap.createScaledBitmap(bitmap, largeur, hauteur, true);
                /*
                    On l'affecte à notre objet de classe
                 */
                    top[i].setImageBitmap(bitmap);
                }

                /*
                    On créer maintenant un onClickListener pour ouvrir le fragment DetailsLivreFragment
                    associé à l'ImageButton.
                    On récupère avant les attributs title et id présent dans le JSONObject
                 */
                String titre = jsonObject.getString("title");
                String id = jsonObject.getString("id");
                Activity activity = getActivity(); // activity courante
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager(); // objet permettant la gestion des Fragments
                top[i].setOnClickListener(new ImageCliquable(bitmap,titre,id,activity,fragmentManager)); // affectation du Listener ici...
            }
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
    }

    /*
        Effectue un chargement de la liste de lecture de l'utilisateur
     */
    public void setListeDeLecture()
    {
        ListeDeLecture listeDeLecture = new ListeDeLecture();
        /*
            On recupere les informations de l'utilisateur via SessionManager
         */
        SessionManager sessionManager = new SessionManager(getContext());
        String email = sessionManager.getSessionEmail();

        listeDeLecture.execute(email); // on demande la requete ici
    }

    /*
        Permet de charger la liste de lecture
     */
    public void chargerListe(String resultat)
    {
        try {
            JSONArray array = new JSONArray(resultat) ; // convertie le résultat en JSONArray
            if(array.length() == 0){
                 /*
                    On ajoute un text dans ce layout
                 */
                infoLivre.setVisibility(View.VISIBLE);
                listeLecture.setVisibility(View.GONE);
                infoLivre.setText("Votre liste de lecture est vide...");

            }else {
            /*
                On parcourt ensuite le JSONArray et on instancie les images correspondantes
             */
                boutonListe = new ImageButton[array.length()]; // on instancie la liste
                int nbColonnes, nbLignes;
                nbColonnes = 3;
                nbLignes = ((int) (array.length() / 3)) + 1;
                listeLecture.removeAllViews();
                listeLecture.setColumnCount(nbColonnes);
                listeLecture.setRowCount(nbLignes);

                for (int i = 0, c = 0, r = 0; i < array.length(); i++, c++) {
                    if (c == nbColonnes) {
                        c = 0;
                        r++;
                    }

                    boutonListe[i] = new ImageButton(getContext());
                    largeur = (int) (100 * getResources().getDisplayMetrics().density + 0.5f);
                    hauteur = (int) (150 * getResources().getDisplayMetrics().density + 0.5f);

                    boutonListe[i].setMinimumWidth(largeur);
                    boutonListe[i].setMinimumHeight(hauteur);
                    // On recupere le premier élement dans un Object, puis en JSONObject
                    Object object = array.get(i);
                    JSONObject jsonObject = new JSONObject(object.toString()); // instanciation du JSONObject

                /*
                    Utilisation de la classe BitmapManager pour charger dans un Bitmap
                    une image venant d'un URL
                */
                    Bitmap bitmap = null; // parametre d'ImageManager
                    if(jsonObject.getString("cover_url").startsWith("http")) {
                        BitmapManager bitmapManager = new BitmapManager(bitmap); // instanciation ici...
                /*
                    On execute bitmapManager, donc requete,
                    le get() fait en sorte que l'UIThread attend le resultat.
                 */
                        bitmap = bitmapManager.execute(jsonObject.getString("cover_url")).get();
                    }else{
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        System.out.println(jsonObject.getString("cover_url"));
                        bitmap = BitmapFactory.decodeFile(jsonObject.getString("cover_url"),options);
                       // bitmap = Bitmap.createScaledBitmap(bitmap,48,48,true);
                    }

                    if (bitmap != null) {
                /*
                    On redimensionne l'image (si besoin est)
                 */
                        bitmap = Bitmap.createScaledBitmap(bitmap, largeur, hauteur, true);
                /*
                    On l'affecte à notre objet de classe
                 */
                        boutonListe[i].setImageBitmap(bitmap);
                    }
                 /*
                    On créer maintenant un onClickListener pour ouvrir le fragment DetailsLivreFragment
                    associé à l'ImageButton.
                    On récupère avant les attributs title et id présent dans le JSONObject
                 */
                    String titre = jsonObject.getString("title");
                    String id = jsonObject.getString("id");
                    Activity activity = getActivity(); // activity courante
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager(); // objet permettant la gestion des Fragments
                    boutonListe[i].setOnClickListener(new ImageCliquable(bitmap, titre, id, activity, fragmentManager));
                /*
                    Je recupere l'adresse email de l'utilisateur
                 */
                    SessionManager sessionManager = new SessionManager(getContext());
                    boutonListe[i].setOnLongClickListener(new ImageSupprimable(id, sessionManager.getSessionEmail(), this));

                /*
                    On ajoute l'imageButton dans le layout
                 */
                    GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(GridLayout.spec(r), GridLayout.spec(c));
                    listeLecture.addView(boutonListe[i], gridParam);
                }
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }

    /*
        Permet d'interroger la base avec un processus, en arriere plan
     */
    class ListeDeLecture extends AsyncTask<String,String,String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            chargerListe(s);
        }

        @Override
        protected String doInBackground(String... param) {
            String response = ""; // attribut contenant notre futur résultat
            try {
            /*
                On parcourt tous les paramètres
             */
                for(String url : param) {

                    Map<String,Object> params = new LinkedHashMap<>();
                    params.put("email", url); // on associe au champs email = emailUser

                    /*
                        Charge les parametres
                     */
                    StringBuilder postData = new StringBuilder();
                    for (Map.Entry<String,Object> para : params.entrySet()) {
                        if (postData.length() != 0) postData.append('&');
                        postData.append(URLEncoder.encode(para.getKey(), "UTF-8"));
                        postData.append('=');
                        postData.append(URLEncoder.encode(String.valueOf(para.getValue()), "UTF-8"));
                    }

                    byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                    /*
                        Recupere adresse via Data
                     */
                    String adresse = Data.getData().getURLecture();
                    URL serv = new URL(adresse); // on instancie l'URL à atteindre
                /*
                    On établit la connexion avec le serveur
                 */
                    HttpURLConnection urlConnection = (HttpURLConnection) serv.openConnection();
                /*
                    On utilise la méthode POST pour le transfert de données
                 */
                    urlConnection.setRequestMethod("POST");

                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                    urlConnection.setDoOutput(true);
                    urlConnection.getOutputStream().write(postDataBytes);

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

}

/*
    Listener permettant lors d'un clique sur l'image de lancer le fragment DetailsLivreFragment associé
    et affiche à l'écran le titre du livre cliqué
 */
class ImageCliquable implements View.OnClickListener
{

    public Bitmap image; // image associée au livre (couverture)...
    public String titre; // titre du livre
    public String id; // id du livre, afin de simplifier les recherches du livre si besoin est dans le futur
    public Activity activity; // activity courante
    public FragmentManager fragmentManager; // manager des fragments
    public ImageCliquable(Bitmap img, String title, String idLivre, Activity activ, FragmentManager fmanager)
    {
        /*
            On lie les attributs aux parametres
         */
        image = img;
        titre = title;
        id = idLivre;
        activity = activ;
        fragmentManager = fmanager;
    }

    /*
        Fonction permettant de gérer le click sur une ImageButton
     */
    @Override
    public void onClick(View v) {
        /*
            On affiche le titre
         */
        Toast.makeText(activity, titre, Toast.LENGTH_SHORT).show();
        DetailsLivreFragment fragment = new DetailsLivreFragment(id, image);
        /*
            On change de fragment
         */
        activity.setTitle(titre);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit(); // on confirme le changement
    }

}

/*
    Listener permettant lors d'un long clique sur l'image de la supprimer de notre liste de lecture
 */
class ImageSupprimable implements View.OnLongClickListener{

    private String idLivre; // id du livre a supprimer
    private String emailUtilisateur; // email de l'utilisateur possedant ce livre
    private AccueilFragment accueil; // accueil Fragment
    /*
        On supprime donc lors d'un longClick le livre d'identifiant id, de la liste de lecture
        de l'utilisateur ayant comme email emailUtilisateur
     */
    public ImageSupprimable(String id, String email, AccueilFragment accueil){
        idLivre = id;
        emailUtilisateur = email;
        this.accueil = accueil;
    }
    @Override
    public boolean onLongClick(View v) {
        //Toast.makeText(getActivity(), "long click : delete book", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(accueil.getContext());

        // set title
        alertDialogBuilder.setTitle("Suppression");
        // set dialog message
        alertDialogBuilder
                .setMessage("Voulez-vous vraiment supprimer ce livre de votre liste de lecture?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*
                            On appel DataManager,
                         */
                        String adresse = Data.getData().getURLSupprimerLivre(idLivre, emailUtilisateur);
                        DataManager dataManager = new DataManager(null);
                        System.out.println(adresse);
                        dataManager.execute(adresse);
                        /*
                            On demande a la page accueil de se recharger
                         */
                        accueil.setListeDeLecture();
                        accueil.getActivity().setTitle("Accueil");
                        FragmentManager fragmentManager = accueil.getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, accueil);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();

        return false;
    }
}
