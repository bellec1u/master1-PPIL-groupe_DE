package com.ppil.groupede.callmeishmael.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ppil.groupede.callmeishmael.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModificationMonProfilFragment extends Fragment {

    /*
        Classe permettant de modifier le profil d'un utilisateur
     */
    public ModificationMonProfilFragment() {
        // Required empty public constructor
    }


    /*
    Fonction permettant de créer et de retourner le Fragment avec les divers
    élements contenus dans ce dernier...
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modification_mon_profil, container, false);
    }

}
