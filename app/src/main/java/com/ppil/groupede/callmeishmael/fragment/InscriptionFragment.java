package com.ppil.groupede.callmeishmael.fragment;


import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import com.ppil.groupede.callmeishmael.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class InscriptionFragment extends Fragment {

    private Button register;
    private ImageButton imageButton;
    private RadioButton genre;
    private EditText dateJour;
    private EditText dateMois;
    private EditText dateAnnee;
    private EditText mdp;
    private EditText mdpConfirmer;
    private EditText email;
    private EditText emailConfirmer;
    private EditText nom;
    private EditText prenom;

    public InscriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inscription, container, false);
        prenom = (EditText)container.findViewById(R.id.idPrenom);
        nom =  (EditText)container.findViewById(R.id.idNom);
        email =  (EditText)container.findViewById(R.id.idEmail);
        emailConfirmer =  (EditText)container.findViewById(R.id.idEmailConfirm);
        mdp =  (EditText)container.findViewById(R.id.idPassword);
        mdpConfirmer =  (EditText)container.findViewById(R.id.idPasswordConfirm);
        dateJour =  (EditText)container.findViewById(R.id.dateDay);
        dateMois =  (EditText)container.findViewById(R.id.dateMonth);
        dateAnnee =  (EditText)container.findViewById(R.id.dateYear);
        genre = (RadioButton)container.findViewById(R.id.Female);
        imageButton = (ImageButton)container.findViewById(R.id.ProfilImage);
        register = (Button)container.findViewById(R.id.idConfirm);
        return view;
    }

}
