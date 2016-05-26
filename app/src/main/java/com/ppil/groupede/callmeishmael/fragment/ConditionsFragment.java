package com.ppil.groupede.callmeishmael.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.SingletonBackPressed;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConditionsFragment extends Fragment {

    private TextView lire;
    public ConditionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conditions, container, false);

        lire = (TextView) view.findViewById(R.id.idLire);
        lire.setText("conditions ...");

        //change d'état le bouton de retour
        SingletonBackPressed.getInstance().setCanBackView(false);
        return view;
    }


}
