package com.ppil.groupede.callmeishmael.fragment;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.R;
import com.google.android.gms.plus.PlusOneButton;

/**
 * A fragment with a Google +1 button.
 */
public class EcrireCommentaireFragment extends Fragment {

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


    public EcrireCommentaireFragment() {
        // Required empty public constructor
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

        star1.setOnClickListener(new StarListener(star1));
        star2.setOnClickListener(new StarListener(star2));
        star3.setOnClickListener(new StarListener(star3));
        star4.setOnClickListener(new StarListener(star4));
        star5.setOnClickListener(new StarListener(star5));

        /*
            Listener anonyme pour le bouton valider,
            on verifiera avant si le commentaire est correct ou non
         */
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String com = commentaire.getText().toString();
                String tmp = com.replaceAll("\\s","");
                if(tmp.length() == 0)
                {
                    Toast.makeText(getContext()," Votre commentaire est vide !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    System.out.println(note);
                    System.out.println(tmp);
                }
            }
        });



        return view;
    }

    /*
        Listener pour étoile, utilisé afin de traduire un clic d'une imageButton en une note
     */
    private class StarListener implements View.OnClickListener
    {
        private boolean clicked = false;
        private ImageButton img = null;

        public StarListener(ImageButton image)
        {
            img = image;
        }
        @Override
        public void onClick(View v) {
            if(!clicked)
            {
                note++;
                img.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                        R.drawable.fullstar));
                clicked = true;
            }
            else
            {
                note--;
                img.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                        R.drawable.empty_star));
                clicked = false;
            }
        }
    }
}
