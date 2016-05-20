package com.ppil.groupede.callmeishmael.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.DataReceiver;

public class Resultat_RechercheFragment extends Fragment implements DataReceiver {
    private TextView titre,genre,id,note;
    private String titre_res,genre_res,id_res,note_res;
    private RechercheFragment fragmentHome;
    private Button info;
    private ImageView img;
    private Bitmap img_res;
    private boolean image_res;

    public Resultat_RechercheFragment(String id, Bitmap img,boolean image,String titre, String genre,String note, RechercheFragment frag) {
        titre_res = titre;
        genre_res = genre ;
        fragmentHome = frag;
        id_res = id;
        if(image) {
            img_res = img;
            image_res = true;
        }
        note_res = note;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resultat_recherche, container, false);
        titre = (TextView) view.findViewById(R.id.titre);
        titre.setText(titre_res);
        genre = (TextView) view.findViewById(R.id.genre);
        genre.setText(genre_res);
        info = (Button) view.findViewById(R.id.info);
        img = (ImageView) view.findViewById(R.id.image);

        //on met l'image que si y 'en a une
        if(image_res)
            img.setImageBitmap(img_res);
        note = (TextView) view.findViewById(R.id.note);

        //on affiche la note que si y en a une
        if(!(note_res.equals("0")))
            note.setText(note_res + "/5");


        //affichage du titre en entier
        titre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),titre_res, Toast.LENGTH_SHORT).show();

            }
        });

        //affichage du genre en entier
        genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),genre_res, Toast.LENGTH_SHORT).show();

            }
        });

        //ouverture de la page détails du livre
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity(); // activity courante
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager(); // objet permettant la gestion des Fragments
                DetailsLivreFragment fragment = new DetailsLivreFragment(id_res,img_res);
                activity.setTitle(titre_res);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit(); // on confirme le changement

            }
        });
        return view;

    }

    /*
      Fonction appelée lorsque le fragment recoit la reponse venant du serveur.
   */
    @Override
    public void receiveData(String resultat) {

        RechercheFragment fragment = new RechercheFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
