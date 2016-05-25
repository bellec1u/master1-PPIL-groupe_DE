package com.ppil.groupede.callmeishmael.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ppil.groupede.callmeishmael.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EvaluationFragment extends Fragment{


    /*
        Classe étant utilisé seulement dans le profil d'un autre utilisateur
        pour afficher les différents commentaires de ce dernier.
     */

    private String titre;
    private String idLivre;
    private Bitmap imageLivre;
    private String commentaireUtilisateur;
    private int note;

    /*
        Elements faisant parties du fragment
     */
    private TextView textTitle;
    private TextView textComment;
    private ImageView star1; // etoile 1 ...
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5; // derniere etoile

    public EvaluationFragment(String title, String id, Bitmap image, String comment, int evaluation) {
        // Required empty public constructor
        titre = title;
        idLivre = id;
        imageLivre = image;
        commentaireUtilisateur = comment;
        note = evaluation;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_evaluation, container, false);

        textTitle = (TextView) view.findViewById(R.id.evaluation_titre);
        textComment = (TextView) view.findViewById(R.id.evaluation_resume);
        star1 = (ImageView) view.findViewById(R.id.evaluation_star1);
        star2 = (ImageView) view.findViewById(R.id.evaluation_star2);
        star3 = (ImageView) view.findViewById(R.id.evaluation_star3);
        star4 = (ImageView) view.findViewById(R.id.evaluation_star4);
        star5 = (ImageView) view.findViewById(R.id.evaluation_star5);

        /*
            ON remplit les champs nécessaires
            donc le titre, les etoiles et le commentaire
         */
        textTitle.setText("Livre : "+titre);
        setStar(note);
        textComment.setText(commentaireUtilisateur);
        textTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsLivreFragment fragment = new DetailsLivreFragment(idLivre, imageLivre);
        /*
            On change de fragment
         */
                getActivity().setTitle(titre);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager(); // objet permettant la gestion des Fragments
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit(); // on confirme le changement
            }
        });


        return view;
    }


    /*
       Affecte à l'étoile numero i une image d'étoile pleine
    */
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

}
