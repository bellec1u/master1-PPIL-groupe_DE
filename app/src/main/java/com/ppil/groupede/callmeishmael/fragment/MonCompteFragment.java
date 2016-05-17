package com.ppil.groupede.callmeishmael.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ppil.groupede.callmeishmael.MainActivity;
import com.ppil.groupede.callmeishmael.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonCompteFragment extends Fragment {


    public MonCompteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mon_compte, container, false);

        Button button= (Button) view.findViewById(R.id.action_modification);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMonCompteModification();
            }
        });

        return view;
    }

    public void setMonCompteModification() {
        ModificationMonProfilFragment fragment = new ModificationMonProfilFragment();
        getActivity().setTitle("Modification du compte");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        ((MainActivity)getActivity()).setConnection(true); // l'utilisateur est connecté
    }

}
