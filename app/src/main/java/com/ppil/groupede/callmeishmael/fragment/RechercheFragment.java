package com.ppil.groupede.callmeishmael.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ppil.groupede.callmeishmael.ProgressTask;
import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.SingletonBackPressed;
import com.ppil.groupede.callmeishmael.data.BitmapManager;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.DataReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class RechercheFragment extends Fragment implements DataReceiver {
      /*
        Classe permettant à l'utilisateur de faire une recherche simple ou une recherche avancée
     */

    private EditText barre_de_recherche; // champ où l'utilisateur écrit sa recherche
    private EditText auteur; // champ où l'utilisateur écrit l'auteur a rechercher
    private ToggleButton recherche_detailler; // bouton permettant de faire le choix entre une recherche simple et avancée
    private ToggleButton ordre; //bouton permettant de faire le choix de l'ordre du tri (croissant ou décroissant)
    private ImageButton recherche; //bouton permettant de lancer la recherche
    private Spinner langue; //liste permettant de choisir la langue du livre
    private Spinner genre; //liste permettant de choisir le genre du livre
    private Spinner tri; //liste permettant de choisir la caractéristique que l'on va utiliser pour faire le tri
    // txt afficher devant les listes et les editext
    private TextView auteur_txt,genre_txt,langue_txt,ordre_txt,tri_txt; // techniquement on s'en fou
    private android.support.v4.app.FragmentTransaction ft;
    private LinearLayout lesResultats;



    public RechercheFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_recherche, container, false);

        //Recuperation des boutons,des listes...
        barre_de_recherche = (EditText) view.findViewById(R.id.txt_rechercher);
        auteur = (EditText) view.findViewById(R.id.auteur);
        recherche_detailler = (ToggleButton) view.findViewById(R.id.recherche_detailler);
        ordre = (ToggleButton) view.findViewById(R.id.ordre);
        recherche = (ImageButton)  view.findViewById(R.id.rechercher);
        langue = (Spinner) view.findViewById(R.id.langue);
        genre =  (Spinner) view.findViewById(R.id.genre);
        tri =  (Spinner) view.findViewById(R.id.tri);
        auteur_txt = (TextView) view.findViewById(R.id.auteur_txt);
        ordre_txt = (TextView) view.findViewById(R.id.ordre_txt);
        langue_txt = (TextView) view.findViewById(R.id.langue_txt);
        genre_txt = (TextView) view.findViewById(R.id.genre_txt);
        tri_txt = (TextView) view.findViewById(R.id.tri_txt);
        lesResultats = (LinearLayout) view.findViewById(R.id.layout_recherche);
        cacher(true);

        //supprimer le texte rechercher une fois qu on clique sur la barre de recherche
        barre_de_recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barre_de_recherche.setText("");
            }
        });

        // permet d'afficher ou cacher la recherche détaillée quand on clique sur +/-
        recherche_detailler.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cacher(false);
                } else {
                    cacher(true);
                }
            }
        });

        //lance la recherche
        recherche.setOnClickListener(new RechercheListener());

        //change d'état le bouton de retour
        SingletonBackPressed.getInstance().setCanBackView(false);

        return view;
    }

    @Override
    public void receiveData(String resultat) {
        int largeur = (int) (100 * getResources().getDisplayMetrics().density + 0.5f);
        int hauteur = (int) (150 * getResources().getDisplayMetrics().density + 0.5f);
         /*
            Si la recherche s'est bien passé alors on convertie le String en JSONObject et on le traite
         */
        try {
            System.out.println(resultat);
            JSONArray array = new JSONArray(resultat) ; // convertie le résultat en JSONArray
            /*
                On parcourt ensuite le JSONArray et on instancie les images correspondantes
             */

            if(array.length() > 0 ){
            for(int i = 0 ; i  < array.length() ; i++) {
                // On recupere le premier élement dans un Object, puis en JSONObject
                Object object = array.get(i);
                JSONObject jsonObject = new JSONObject(object.toString()); // instanciation du JSONObject

                             /*

                    On récupère avant les attributs présent dans le JSONObject
                 */
                String titre = jsonObject.getString("title");
                String genre = jsonObject.getString("genre");
                String id = jsonObject.getString("id");
                String note = jsonObject.getString("stars_average");

                 /*
                    Utilisation de la classe BitmapManager pour charger dans un Bitmap
                    une image venant d'un URL
                */
                Bitmap bitmap = null; // parametre d'ImageManager
                BitmapManager bitmapManager = new BitmapManager(bitmap); // instanciation ici...
                /*
                    On execute bitmapManager, donc requete,
                    le get() fait en sorte que l'UIThread attend le resultat.
                 */
                bitmap = bitmapManager.execute(jsonObject.getString("cover_url")).get();
                boolean image = false;
                if (bitmap != null) {
                    image = true;
                /*
                    On redimensionne l'image (si besoin est)
                 */
                    bitmap = Bitmap.createScaledBitmap(bitmap, largeur, hauteur, true);
                }

                Resultat_RechercheFragment com = new Resultat_RechercheFragment(id, bitmap, image, titre, genre, note);
                ft = getFragmentManager().beginTransaction();
                ft.add(R.id.layout_recherche, com, "");
                ft.commit();
                }
            }else{
                    Toast.makeText(getContext(), "Aucun resultat", Toast.LENGTH_SHORT).show();
                }

        } catch ( JSONException e) {
            Toast.makeText(getContext(), "Erreur lors de la recherche", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

        //cacher ou afficher la recherche avancée}

    public void cacher(Boolean res){
        if(res){
            auteur.setVisibility(View.GONE);
            genre.setVisibility(View.GONE);
            ordre.setVisibility(View.GONE);
            tri.setVisibility(View.GONE);
            langue.setVisibility(View.GONE);
            langue_txt.setVisibility(View.GONE);
            auteur_txt.setVisibility(View.GONE);
            genre_txt.setVisibility(View.GONE);
            ordre_txt.setVisibility(View.GONE);
            tri_txt.setVisibility(View.GONE);
            langue_txt.setVisibility(View.GONE);
        }else{
            auteur.setVisibility(View.VISIBLE);
            genre.setVisibility(View.VISIBLE);
            ordre.setVisibility(View.VISIBLE);
            tri.setVisibility(View.VISIBLE);
            langue.setVisibility(View.VISIBLE);
            langue_txt.setVisibility(View.VISIBLE);
            auteur_txt.setVisibility(View.VISIBLE);
            genre_txt.setVisibility(View.VISIBLE);
            ordre_txt.setVisibility(View.VISIBLE);
            tri_txt.setVisibility(View.VISIBLE);
            langue_txt.setVisibility(View.VISIBLE);
        }

    }

    /*
        Listener servant à gérer une recherche demandé par l"utilisateur,
        de ce faite, ce listener va charger tous les champs possibles,
        les verifier, et ensuite demander les résultats de cette derniere.
     */
    private class RechercheListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            new ProgressTask(getActivity()).execute();

            //masquer le clavier
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            //ON efface les resultats precedents du layout
            lesResultats.removeAllViews(); // on efface ici...

            String aut = auteur.getText().toString();
            String ord = ordre.getText().toString();
            String lan = langue.getSelectedItem().toString();
            String gen = genre.getSelectedItem().toString();
            String triPar = tri.getSelectedItem().toString();
            String recherche = barre_de_recherche.getText().toString().trim();
            /*
                On demande a Data l'URL pour cette recherche
             */
            if(recherche.length() != 0) {
                String adresse = Data.getData().getURLRecherche();
                byte[] infos = Data.getData().getPostRechercher(aut, ord, lan, gen, triPar, recherche);
                System.out.println(adresse);
                DataManager dataManager = new DataManager(RechercheFragment.this);
                dataManager.execute(adresse, infos);
            }else{
                Toast.makeText(getContext()," Veuillez remplir le champ : 'Rechercher'",Toast.LENGTH_SHORT).show();
            }

        }
    }
}

