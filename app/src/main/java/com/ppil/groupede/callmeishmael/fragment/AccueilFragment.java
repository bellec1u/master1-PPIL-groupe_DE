package com.ppil.groupede.callmeishmael.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
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

        int nbLivre = 14;
        int nbColonnes = 3;
        int nbLignes = ((int)(nbLivre/3)) + 1;

        GridLayout gl = (GridLayout) view.findViewById(R.id.liste_de_lecture);
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
