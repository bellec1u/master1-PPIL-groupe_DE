package com.ppil.groupede.callmeishmael.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConnexionFragment extends Fragment {

    private Button logIn;
    private AutoCompleteTextView email;
    private EditText password;
    public ConnexionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connexion, container, false);
        logIn = (Button) view.findViewById(R.id.action_sign_in);
        email = (AutoCompleteTextView) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailS, pwdS;
                emailS = email.getText().toString();
                pwdS = password.getText().toString();
                if (emailS.equals("") || pwdS.equals("")) {
                    Toast.makeText(getActivity(), "Champ(s) vide(s) !", Toast.LENGTH_SHORT);
                }
            }
        });
        return view;
    }

}