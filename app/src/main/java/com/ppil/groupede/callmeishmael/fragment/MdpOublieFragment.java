package com.ppil.groupede.callmeishmael.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ppil.groupede.callmeishmael.R;

/**
 * Created by catherine on 18/05/2016.
 */
public class MdpOublieFragment extends Fragment {

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

        return view;
    }
}
