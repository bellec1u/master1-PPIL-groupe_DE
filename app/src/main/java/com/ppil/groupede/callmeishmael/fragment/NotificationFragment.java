package com.ppil.groupede.callmeishmael.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ppil.groupede.callmeishmael.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    /*
        Elements contenu dans ce fragment
        1 ImageView
        4 TextView (type, nom d'utilisateur, nom Livre, details)
     */
    private ImageView imageLivre;
    private TextView typeNotification;
    private TextView nomUtilisateur;
    private TextView nomLivre;
    private TextView detailsNotification;

    private Bitmap img;
    private String type;
    private String nomU;
    private String nomL;
    private String detailsN;

    public NotificationFragment(Bitmap image, String type, String utilisateur, String livre, String details) {
        // Required empty public constructor
        img = image;
        this.type = type;
        nomU = utilisateur;
        nomL = livre;
        detailsN = details;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        /*
            On va maintenant lier chaque attribut avec son element correspondant du Layout
         */
        imageLivre = (ImageView) view.findViewById(R.id.notification_image);
        typeNotification = (TextView) view.findViewById(R.id.notification_type);
        nomUtilisateur = (TextView) view.findViewById(R.id.notification_utilisateur);
        nomLivre = (TextView) view.findViewById(R.id.notification_livre);
        detailsNotification = (TextView) view.findViewById(R.id.notification_details);

        imageLivre.setImageBitmap(img);
        typeNotification.setText(type);
        nomUtilisateur.setText(nomU);
        nomLivre.setText(nomL);
        detailsNotification.setText(detailsN);

        /*
            Pas de listener pour le moment
         */
        return view;
    }

}
