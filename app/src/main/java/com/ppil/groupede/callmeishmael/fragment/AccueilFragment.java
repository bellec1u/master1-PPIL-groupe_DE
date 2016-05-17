package com.ppil.groupede.callmeishmael.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;
import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.BitmapManager;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.DataReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            Mise en place du GridLayout
        */
        GridLayout gl = (GridLayout) view.findViewById(R.id.top_dix);
        gl.removeAllViews();
        gl.setColumnCount( nbColonnes );
        gl.setRowCount( nbLignes );


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
            gl.addView(top[i], gridParam);
        }


        // ---------- ---------- ---------- ---------- ---------- Liste de suggestion

        nbLivre = 8;
        nbColonnes = 3;
        nbLignes = ((int)(nbLivre/3)) + 1;

        gl = (GridLayout) view.findViewById(R.id.liste_des_suggestions);
        gl.removeAllViews();
        gl.setColumnCount( nbColonnes );
        gl.setRowCount( nbLignes );

        for (int i = 0, c = 0, r = 0; i < nbLivre; i++, c++) {
            if (c == nbColonnes) {
                c = 0;
                r++;
            }

            ImageButton ib = new ImageButton(view.getContext());

            int largeur = (int) (100 * getResources().getDisplayMetrics().density + 0.5f);
            int hauteur = (int) (150 * getResources().getDisplayMetrics().density + 0.5f);

            ib.setMinimumWidth(largeur);
            ib.setMinimumHeight(hauteur);
            ib.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Toast.makeText(getActivity(), "ImageButton is clicked!", Toast.LENGTH_SHORT).show();

                }

            });

            GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(GridLayout.spec(r), GridLayout.spec(c));
            gl.addView(ib, gridParam);
        }

        // ---------- ---------- ---------- ---------- ---------- Liste de lecture
        nbLivre = 4;
        nbLignes = ((int)(nbLivre/3)) + 1;

        gl = (GridLayout) view.findViewById(R.id.liste_de_lecture);
        gl.removeAllViews();
        gl.setColumnCount( nbColonnes );
        gl.setRowCount( nbLignes );

        for (int i = 0, c = 0, r = 0; i < nbLivre; i++, c++) {
            if (c == nbColonnes) {
                c = 0;
                r++;
            }

            ImageButton ib = new ImageButton(view.getContext());

            int largeur = (int) (100 * getResources().getDisplayMetrics().density + 0.5f);
            int hauteur = (int) (150 * getResources().getDisplayMetrics().density + 0.5f);

            ib.setMinimumWidth(largeur);
            ib.setMinimumHeight(hauteur);
            ib.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Toast.makeText(getActivity(), "ImageButton is clicked!", Toast.LENGTH_SHORT).show();

                }

            });

            //fonction permettant de supprimer un livre de la liste de lecture
            ib.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View arg0) {

                    //Toast.makeText(getActivity(), "long click : delete book", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( getContext() );

                    // set title
                    alertDialogBuilder.setTitle("Suppression");
                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Voulez-vous vraiment supprimer ce livre de votre liste de lecture?")
                            .setCancelable(false)
                            .setPositiveButton("Oui",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    //fonction de suppression du livre dans la BDD

                                }
                            })
                            .setNegativeButton("Non",new DialogInterface.OnClickListener() {
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
            });

            GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(GridLayout.spec(r), GridLayout.spec(c));
            gl.addView(ib, gridParam);
        }
        firstLoad = false; // la fonction aura donc été appelée au moins 1 fois
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
                /*
                    On redimensionne l'image (si besoin est)
                 */
                bitmap = Bitmap.createScaledBitmap(bitmap, largeur, hauteur, true);
                /*
                    On l'affecte à notre objet de classe
                 */
                top[i].setImageBitmap(bitmap);

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
