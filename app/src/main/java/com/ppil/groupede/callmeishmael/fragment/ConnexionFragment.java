package com.ppil.groupede.callmeishmael.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.SessionManager;

import org.json.JSONStringer;


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
                DataManager dm = new DataManager();
                dm.setUrlLogin(emailS, pwdS);
                dm.run();
                String ok = dm.getResult();
                Log.i("INFO", ok);
                if(ok.equals("\"0\""))
                {
                    Toast.makeText(getActivity(),"Combinaison email/mot de passe incorrect !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SessionManager session = new SessionManager(getContext());
                    session.createUserSession(emailS);
                    // Set the fragment of view
                    AccueilFragment fragment = new AccueilFragment();
                    getActivity().setTitle("Accueil");
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                //// TODO: 10/05/16 connexion to database
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
            if(verif.length() > 7)
            {
                pwdOk = true;
            }
            else
            {
                pwdOk = false;
                //Toast.makeText(getActivity(), "Le mot de passe doit faire au moins 8 caractères !", Toast.LENGTH_SHORT).show();
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
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(verif).matches()) {
                    mailOk = true;
                }
                else
                {
                    mailOk = false;
                    //Toast.makeText(getActivity(), "Email incorrect !", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                mailOk = false;
                //Toast.makeText(getActivity(), "Email incorrect !", Toast.LENGTH_SHORT).show();
            }
            logIn.setClickable(mailOk && pwdOk);
        }
    }
}