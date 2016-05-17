package com.ppil.groupede.callmeishmael.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.BitmapManager;
import com.ppil.groupede.callmeishmael.data.SessionManager;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModificationMonProfilFragment extends Fragment {

    /*
        Classe permettant de modifier le profil d'un utilisateur
     */

    private Button save; // bouton pour s'inscrire
    private ImageButton imageButton; // imagebutton pour choisir l'image personnelle
    private RadioButton genreF; // le genre de l'utilisateur
    private RadioButton genreM ; // le genre de l'utilisateur
    private EditText dateJour; // date du jour
    private EditText dateMois; // date du mois
    private EditText dateAnnee; // date de l'année
    private EditText mdp; // mot de passe
    private EditText mdpConfirmer; // confimation de mot de passe
    private EditText email; // email
    private EditText emailConfirmer; // confirmation de mail
    private EditText nom; // nom de l'utilisateur
    private EditText prenom; // prenom de l'utilisateur

    public ModificationMonProfilFragment() {
        // Required empty public constructor
    }


    /*
    Fonction permettant de créer et de retourner le Fragment avec les divers
    élements contenus dans ce dernier...
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modification_mon_profil, container, false);

        /*
               On affecte à chaque attribut le bon élement
         */
        prenom = (EditText) view.findViewById(R.id.editText8);
        nom = (EditText) view.findViewById(R.id.editText);
        email = (EditText) view.findViewById(R.id.editText9);
        emailConfirmer = (EditText) view.findViewById(R.id.editText10);
        mdp = (EditText) view.findViewById(R.id.editText11);
        mdpConfirmer = (EditText) view.findViewById(R.id.editText12);
        dateJour = (EditText) view.findViewById(R.id.editText13);
        dateMois = (EditText) view.findViewById(R.id.editText14);
        dateAnnee = (EditText) view.findViewById(R.id.editText15);
        genreF = (RadioButton) view.findViewById(R.id.Female);
        genreM = (RadioButton) view.findViewById(R.id.radioButtonHomme);
        imageButton = (ImageButton) view.findViewById(R.id.imageButton11);

        /*
            On précharge les champs avec les valeurs connues sur cet utilisateur.
            Pour cela, on utilise SessionManager
         */
        SessionManager sessionManager = new SessionManager(getContext());
        prenom.setText(sessionManager.getSessionFirstName());
        nom.setText(sessionManager.getSessionName());
        email.setText(sessionManager.getSessionEmail());
        emailConfirmer.setText(sessionManager.getSessionEmail());
        String date = sessionManager.getSessionDate();
        String[] dateSpliter = date.split("-");

        /*
            On remplie la date
         */
        dateAnnee.setText(dateSpliter[0]);
        dateMois.setText(dateSpliter[1]);
        dateJour.setText(dateSpliter[2]);

        /*
            On verifie le sexe, et on coche la bonne case
         */
        String sexe = sessionManager.getSessionGenre();
        if(sexe.equals("M"))
        {
            genreM.setChecked(true);
            genreF.setChecked(false);
        }else{
            genreF.setChecked(true);
            genreM.setChecked(false);
        }

        /*
            On charge l'image de l'utilisateur
         */
        String url = sessionManager.getSessionURL();
        Bitmap img = null;
        if(url.equals(""))
        {
            /*
                On load l'image par défaut
                et on convertie le drawable en Bitmap (image par défault)
            */
            img = BitmapFactory.decodeResource(getResources(),
                    R.drawable.whale);
            imageButton.setImageBitmap(img); // on affecte l'image ici..
        }
        else
        {
            BitmapManager bitmapManager = new BitmapManager(img);
            try {
                img = bitmapManager.execute(url).get();
                imageButton.setImageBitmap(img); // on affecte l'image ici..
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(getContext(), " Une erreur s'est passé dans le chargement de l'image", Toast.LENGTH_SHORT).show();
                imageButton.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                        R.drawable.whale)); // on affecte l'image ici..
            }
        }







        return  view;
    }

}
