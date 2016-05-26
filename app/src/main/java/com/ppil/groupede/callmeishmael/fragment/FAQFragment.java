package com.ppil.groupede.callmeishmael.fragment;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.SingletonBackPressed;
import com.ppil.groupede.callmeishmael.data.BitmapManager;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.DataReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class FAQFragment extends Fragment implements DataReceiver{


    private TextView faq; // contient les resultas de la FAQ
    public FAQFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        faq = (TextView) view.findViewById(R.id.faq);
        /*
            On demande a questionner la base
         */
        String adresse = Data.getData().getURLFAQ();
        DataManager dataManager = new DataManager(this);
        try {
            String wait = dataManager.execute(adresse).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        //change d'état le bouton de retour
        SingletonBackPressed.getInstance().setCanBackView(false);

        return view;
    }

    /*
        Fonction appelée lorsque le serveur a envoyé les données nécessaires.
     */
    @Override
    public void receiveData(String resultat) {
        StringBuilder sb = new StringBuilder("");
        System.out.println(resultat);
        try {
            JSONArray array = new JSONArray(resultat) ; // convertie le résultat en JSONArray
            /*
                On parcourt ensuite le JSONArray et on instancie les images correspondantes
             */
            int indice = 1;
            for(int i = 0 ; i  < array.length() ; i++){
                // On recupere le premier élement dans un Object, puis en JSONObject
                Object object = array.get(i);
                JSONObject jsonObject = new JSONObject(object.toString()); // instanciation du JSONObject

                /*
                    On lit les FAQs existantes et on les inseres dans le TextView
                */
                sb.append(indice + ". " + jsonObject.getString("question") + "\n");
                sb.append("\t- " + jsonObject.getString("response") + "\n\n");
                indice++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        faq.setText(sb.toString());
    }
}