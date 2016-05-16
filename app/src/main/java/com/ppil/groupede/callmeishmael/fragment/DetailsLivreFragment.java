package com.ppil.groupede.callmeishmael.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.DataManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsLivreFragment extends Fragment {

    private String idUrl; // url du livre à atteindre
    private Bitmap image ; // evite de recharger l'image inutilement

    private ImageView imageLivre;
    private TextView dateParution;
    private TextView resume;
    private TextView note;
    private TextView genre;
    private TextView auteur;
    private TextView titre;
    private TextView langue;

    public DetailsLivreFragment(String url, Bitmap img) {
        // Required empty public constructor
        idUrl= url;
        image = img;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_livre, container, false);
        dateParution = (TextView) view.findViewById(R.id.rep_parution_date);
        resume = (TextView) view.findViewById(R.id.rep_resume);
        note = (TextView) view.findViewById(R.id.rep_note);
        genre = (TextView) view.findViewById(R.id.rep_genre);
        auteur = (TextView) view.findViewById(R.id.rep_author);
        titre = (TextView) view.findViewById(R.id.rep_title);
        langue = (TextView) view.findViewById(R.id.rep_language);
        imageLivre = (ImageView)view.findViewById(R.id.cover_image);

        //On remplit les champs avec les details du livre
        imageLivre.setImageBitmap(image);

        // nouveau Thread, pour récupérer les details d'un livre
        DataManager dm = new DataManager();
        dm.setUrlBookDetail(idUrl);
        dm.run();
        String informations = dm.getResult();
        try {
            JSONObject o = new JSONObject(informations);
            dateParution.setText("Date de parution : "+o.getString("publication_date"));
            resume.setText(o.getString("resume"));
            note.setText(o.getString("stars_average") + " / 5");
            genre.setText("Genre : "+ o.getString("genre"));
            auteur.setText(" Auteur : "+o.getString("author"));
            titre.setText(o.getString("title"));
            langue.setText("Langue : "+o.getString("language"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

}
