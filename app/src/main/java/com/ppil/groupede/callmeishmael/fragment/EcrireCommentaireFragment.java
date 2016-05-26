package com.ppil.groupede.callmeishmael.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.R;
import com.google.android.gms.plus.PlusOneButton;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.DataReceiver;
import com.ppil.groupede.callmeishmael.data.SessionManager;

/**
 * A fragment with a Google +1 button.
 */
public class EcrireCommentaireFragment extends Fragment implements DataReceiver{

    /*
        Classe permettant à l'utilisateur  de faire son commentaire,
        il cliquera sur chaque etoile (jusqu'à 5) et écrira son commentaire
     */

    private Button valider;
    private EditText commentaire;
    private ImageButton star1;
    private ImageButton star2;
    private ImageButton star3;
    private ImageButton star4;
    private ImageButton star5;
    private int note ; // note du commentaire
    private String idLivre ; // id du livre
    private Bitmap imageLivre; // garde en mémoire

    private boolean modification; // faux de base car l'utilisateur edite pour la premiere fois


    public EcrireCommentaireFragment(String id, Bitmap img) {
        // Required empty public constructor
        idLivre = id;
        imageLivre = img;
        modification = false;
        note = 0;
    }

    /*
        Affecte modification à true
     */
    public void setModification(boolean mod){
        modification = mod;
    }
    /*
        Fonction initialisant la vue avec ses divers éléments
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ecrire_commentaire, container, false);

        valider = (Button) view.findViewById(R.id.commentaire_ecrire_valider);
        commentaire = (EditText) view.findViewById(R.id.commentaire_ecrire_commentaire);
        star1 = (ImageButton) view.findViewById(R.id.commentaire_ecrire_star1);
        star2 = (ImageButton) view.findViewById(R.id.commentaire_ecrire_star2);
        star3 = (ImageButton) view.findViewById(R.id.commentaire_ecrire_star3);
        star4 = (ImageButton) view.findViewById(R.id.commentaire_ecrire_star4);
        star5 = (ImageButton) view.findViewById(R.id.commentaire_ecrire_star5);

        star1.setOnClickListener(new StarListener(star1,1));
        star2.setOnClickListener(new StarListener(star2,2));
        star3.setOnClickListener(new StarListener(star3,3));
        star4.setOnClickListener(new StarListener(star4,4));
        star5.setOnClickListener(new StarListener(star5,5));

        /*
            Listener anonyme pour le bouton valider,
            on verifiera avant si le commentaire est correct ou non
         */
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //masquer le clavier
                View view = getActivity().getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                String com = commentaire.getText().toString();
                String tmp = com.replaceAll("\\s","");
                    /*
                        J'instancie Data pour recuperer l'URL de creation d'un commentaire
                        J'instancie SessinManager pour recuperer egalement l'email de l'utilisateur
                     */
                        SessionManager sessionManager = new SessionManager(getContext());
                        String email = sessionManager.getSessionEmail();
                        String adresse = Data.getData().getURLCommentaire();
                        byte[] infos = Data.getData().getPostCommenter(idLivre, email, com, note);
                    /*
                        On demande a DataManager de faire la requete au serveur
                     */
                        DataManager dataManager = new DataManager(EcrireCommentaireFragment.this);
                        dataManager.execute(adresse,infos);
            }
        });



        return view;
    }

    /*
        Fonction appelée lorsque le processus en arrière plan aura terminé de dialoguer avec le serveur,
        ici on retournera l'utilisateur vers la vue de détails d'un livre.
     */
    @Override
    public void receiveData(String resultat) {
            Toast.makeText(getContext()," Votre commentaire a bien été enregistré ", Toast.LENGTH_SHORT).show();
            setVueDetail(idLivre);
    }

    /*
        Renvoie l'utilisateur vers la vue de détails d'un livre
     */
    public void setVueDetail(String id)
    {
        DetailsLivreFragment fragment = new DetailsLivreFragment(id, imageLivre); // id du livre
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    /*
        Listener pour étoile, utilisé afin de traduire un clic d'une imageButton en une note
     */
    private class StarListener implements View.OnClickListener
    {
        private boolean clicked = false;
        private ImageButton img = null;
        private int pos;

        public StarListener(ImageButton image, int position)
        {
            img = image;
            pos = position;
        }
        @Override
        public void onClick(View v) {
            for(int i = 0 ; i < pos ; i++)
            {
                setStar(i);
            }
            for(int i = pos ; i < 5 ; i++)
            {
                unSetStar(i);
            }
            note = pos ;
            System.out.println(note);
        }
    }

    public void setStar(int i)
    {
        Bitmap img = null;
        switch(i)
        {
            case 0 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.fullstar);
                star1.setImageBitmap(img);
                break;
            case 1 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.fullstar);
                star2.setImageBitmap(img);
                break;
            case 2 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.fullstar);
                star3.setImageBitmap(img);
                break;
            case 3 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.fullstar);
                star4.setImageBitmap(img);
                break;
            case 4 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.fullstar);
                star5.setImageBitmap(img);
                break;
        }
    }

    public void unSetStar(int i)
    {
        Bitmap img = null;
        switch(i)
        {
            case 0 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.empty_star);
                star1.setImageBitmap(img);
                break;
            case 1 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.empty_star);
                star2.setImageBitmap(img);
                break;
            case 2 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.empty_star);
                star3.setImageBitmap(img);
                break;
            case 3 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.empty_star);
                star4.setImageBitmap(img);
                break;
            case 4 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.empty_star);
                star5.setImageBitmap(img);
                break;
        }
    }
}
