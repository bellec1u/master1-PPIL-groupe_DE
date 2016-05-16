package com.ppil.groupede.callmeishmael.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.MainActivity;
import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.BitmapManager;
import com.ppil.groupede.callmeishmael.data.DataManager;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccueilFragment extends Fragment {

    public AccueilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accueil, container, false);

        // ---------- ---------- ---------- ---------- ---------- top 10
        /*

        Chargement des images et titres des livres du top10
         */
        DataManager dm = new DataManager();
        dm.setUrl10();
        dm.run();
        String res = dm.getResult(); // pas en json par contre (que en string)
        ArrayList<String> combinaisonURLTitre = new ArrayList<>(); // avec l'id
        String [] split = res.split("\\|\\|\\|");
        for(int i = 0; i < split.length ; i++)
        {
            combinaisonURLTitre.add(split[i]);
        }

        int nbLivre = 10;
        int nbColonnes = 3;
        int nbLignes = ((int)(nbLivre/3)) + 1;

        GridLayout gl = (GridLayout) view.findViewById(R.id.top_dix);
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
            final String[] nomTitreEtId = combinaisonURLTitre.get(i).split("\\|\\|");
            final String id = nomTitreEtId[1] ; // recupere l'id du livre
            final String nomTitre[] = nomTitreEtId[0].split("\\|");
            final BitmapManager bm = new BitmapManager(nomTitre[0]);
            bm.setUrl(nomTitre[0]);
            bm.run();
            Bitmap bitmap = bm.getImage();
            bitmap = Bitmap.createScaledBitmap(bitmap, largeur, hauteur, true);
            ib.setImageBitmap(bitmap);
            ib.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    /*
                        ON est redirigé vers la vue contenant les details d'un livre
                        avec comme titre, le titre de l'ouvrage
                     */
                    final String titre = nomTitre[1];
                    final String imgUrl = nomTitre[0];
                    Toast.makeText(getActivity(), titre, Toast.LENGTH_SHORT).show();
                    DetailsLivreFragment fragment = new DetailsLivreFragment(id,bm.getImage());
                    getActivity().setTitle(titre);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

            });

            GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(GridLayout.spec(r), GridLayout.spec(c));
            gl.addView(ib, gridParam);
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

            GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(GridLayout.spec(r), GridLayout.spec(c));
            gl.addView(ib, gridParam);
        }

        return view;
    }
}
