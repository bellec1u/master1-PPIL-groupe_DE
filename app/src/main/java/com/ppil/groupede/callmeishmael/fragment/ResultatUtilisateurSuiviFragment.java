package com.ppil.groupede.callmeishmael.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.R;


public class ResultatUtilisateurSuiviFragment extends Fragment {
    private TextView nom,date;
    private String nom_res,prenom_res,date_res,id_res;
    private Button info;
    private ImageView img;
    private Bitmap img_res;
    private boolean image_res;

    public ResultatUtilisateurSuiviFragment(String id, Bitmap img, boolean image, String first_name, String last_name, String date) {
       nom_res = last_name;
        prenom_res = first_name;
        id_res = id;
        date_res = date;
        if(image) {
            img_res = img;
            image_res = true;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_resultat_utilisateur_suivi, container, false);
        nom = (TextView) view.findViewById(R.id.nom);
        nom.setText(nom_res + " " +prenom_res);
        date = (TextView) view.findViewById(R.id.date);
        System.out.println(date_res);
        date.setText(date_res);
        info = (Button) view.findViewById(R.id.info);
        img = (ImageView) view.findViewById(R.id.image);

        //on met l'image que si y 'en a une
        if(image_res)
            img.setImageBitmap(img_res);

//affichage du titre en entier
        nom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),nom_res + " " + prenom_res, Toast.LENGTH_SHORT).show();

            }
        });

        // Inflate the layout for this fragment
        return view;
    }


    public void receiveData(String resultat) {

        UtilisateurSuiviFragment fragment = new UtilisateurSuiviFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
