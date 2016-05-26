package com.ppil.groupede.callmeishmael.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.SingletonBackPressed;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReglagesFragment extends Fragment {


    public ReglagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //change d'état le bouton de retour
        SingletonBackPressed.getInstance().setCanBackView(false);
        return inflater.inflate(R.layout.fragment_conditions, container, false);
    }

}
