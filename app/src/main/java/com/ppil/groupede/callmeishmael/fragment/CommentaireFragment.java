package com.ppil.groupede.callmeishmael.fragment;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ppil.groupede.callmeishmael.R;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class CommentaireFragment extends Fragment {

    /*
        Fragment permettant la mise en place d'un commentaire,
        plusieurs champs seront disponibles :
        -Auteur
        -Etoile1...2...5
        -Commentaire
        -Bouton modifier
        -Bouton Supprimer
        -Bouton Suivre
     */

    private TextView auteur; //auteur du commentaire
    private ImageView star1; // etoile 1 ...
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5; // derniere etoile
    private TextView resume; // resume
    private Button modifier; // bouton modifier si, auteur = utilisateur connecté
    private Button suivre; // bouton pour suivre l'auteur du commentaire
    private Button supprimer ; // bouton pour supprimer un commentaire

    private String auteurCommentaire;
    private float noteCommentaire;
    private String resumeCommentaire;


    @SuppressLint("ValidFragment")
    public CommentaireFragment(String auteur, float note, String resume) {
        // Required empty public constructor
        auteurCommentaire = auteur;
        noteCommentaire = note;
        resumeCommentaire = resume;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_commentaire, container, false);
        auteur = (TextView) view.findViewById(R.id.commentaire_auteur);
        star1 = (ImageView) view.findViewById(R.id.commentaire_star1);
        star2 = (ImageView) view.findViewById(R.id.commentaire_star2);
        star3 = (ImageView) view.findViewById(R.id.commentaire_star3);
        star4 = (ImageView) view.findViewById(R.id.commentaire_star4);
        star5 = (ImageView) view.findViewById(R.id.commentaire_star5);

        resume = (TextView) view.findViewById(R.id.commentaire_resume);

        modifier = (Button) view.findViewById(R.id.commentaire_modifier);
        supprimer = (Button) view.findViewById(R.id.commentaire_supprimer);
        suivre = (Button) view.findViewById(R.id.commentaire_suivre);

        /*
            On affecte aux bons éléments les bonnes valeurs
         */
        auteur.setText(auteurCommentaire);
        resume.setText(resumeCommentaire);
        setStars(noteCommentaire);


        return view;
    }

    /*
        Convertie la note en étoile
     */
    public void setStars(float note)
    {
        float notation = Float.valueOf(note);
        for(int i = 0 ; i <(int)notation ; i++)
        {
            setStar(i);
        }
        int entier = (int)notation;
        float difference = notation - entier;
        if(Math.round(difference) == 0.5)
        {
            setHalfStar((int) notation);
        }
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

    /*
        Affecte à l'étoile numéro i une étoile mi-pleine
     */
    public void setHalfStar(int i)
    {
        Bitmap img = null;
        switch(i)
        {
            case 0 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.halfstar);
                star1.setImageBitmap(img);
                break;
            case 1 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.halfstar);
                star2.setImageBitmap(img);
                break;
            case 2 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.halfstar);
                star3.setImageBitmap(img);
                break;
            case 3 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.halfstar);
                star4.setImageBitmap(img);
                break;
            case 4 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.halfstar);
                star5.setImageBitmap(img);
                break;
        }
    }
}
