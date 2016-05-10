package com.ppil.groupede.callmeishmael.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
    private boolean mailOk,pwdOk;
    public ConnexionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connexion, container, false);
        logIn = (Button) view.findViewById(R.id.action_sign_in);
        mailOk = false;
        pwdOk = false;
        logIn.setClickable(mailOk && pwdOk);
        email = (AutoCompleteTextView) view.findViewById(R.id.email);
        email.addTextChangedListener(new EmailWatcher());
        password = (EditText) view.findViewById(R.id.password);
        password.addTextChangedListener(new PasswordWatcher());

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailS, pwdS;
                emailS = email.getText().toString();
                pwdS = password.getText().toString();
                Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private class PasswordWatcher implements TextWatcher
    {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String verif = s.toString();
            verif.replaceAll("\\s*", "");
            if(verif.length() > 0)
            {
                pwdOk = true;
            }
            else
            {
                pwdOk = false;
                Toast.makeText(getActivity(), "Mot de passe incorrect !", Toast.LENGTH_SHORT).show();
            }
            logIn.setClickable(mailOk && pwdOk);
        }
    }

    private class EmailWatcher implements TextWatcher
    {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String verif = s.toString();
            verif.replaceAll("\\s*", "");
            if(verif.length() > 0)
            {
                if(verif.contains("@")) {
                    mailOk = true;
                }
                else
                {
                    mailOk = false;
                    Toast.makeText(getActivity(), "Email incorrect !", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                mailOk = false;
                Toast.makeText(getActivity(), "Email incorrect !", Toast.LENGTH_SHORT).show();
            }
            logIn.setClickable(mailOk && pwdOk);
        }
    }
}