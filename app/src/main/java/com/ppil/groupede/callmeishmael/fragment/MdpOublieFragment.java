package com.ppil.groupede.callmeishmael.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ppil.groupede.callmeishmael.R;

/**
 * Created by catherine on 18/05/2016.
 */
public class MdpOublieFragment extends Fragment {

    private Button ok;
    private EditText email;

    public MdpOublieFragment(){
        // required empty constructor

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*
            On récupère les élements à partir de la view
            et on les affecte à l'attribut de classe correspondant.
         */

        View view = inflater.inflate(R.layout.fragment_mdp_oublie, container, false);

        ok = (Button) view.findViewById(R.id.button_ok);
        email = (EditText) view.findViewById(R.id.editText_mail);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //REQUETE EMAIL MDP OUBLIE
                }});

        return view;
    }
}
