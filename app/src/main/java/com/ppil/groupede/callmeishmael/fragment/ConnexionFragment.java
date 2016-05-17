package com.ppil.groupede.callmeishmael.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.ppil.groupede.callmeishmael.MainActivity;
import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.DataReceiver;
import com.ppil.groupede.callmeishmael.data.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConnexionFragment extends Fragment implements DataReceiver{

    /*
        Classe permettant à l'utilisateur de se connecter, soit en remplissant les champs
        Email et Password ou bien en cliquant sur les boutons facebook ou google+
     */
    private Button logIn; // bouton de logIn
    private AutoCompleteTextView email; // champ où l'utilisateur écrit son mail
    private EditText password; // champ contenant le mot de passe

    /*
        Constructeur de ConnexionFragment, vide ...
     */
    public ConnexionFragment() {
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
        View view = inflater.inflate(R.layout.fragment_connexion, container, false);
        logIn = (Button) view.findViewById(R.id.action_sign_in); // bouton de connexion
        email = (AutoCompleteTextView) view.findViewById(R.id.email); // on recupere le champ email
        password = (EditText) view.findViewById(R.id.password); // on recupere le champ password

        /*
            On créé un listener anonyme pour le bouton de connexion
         */
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean okEmail, okPassword;
                /*
                    On récupère la validité dans chacun de ces booléens
                 */
                okEmail = isValid(email.getText().toString(), "email");
                okPassword = isValid(password.getText().toString(), "mot de passe");

                if(okEmail && okPassword)
                {
                    /*
                        On demande l'adresse à Data pour effectuer une connexion
                     */
                    String adresse = Data.getData().getConnexion(email.getText().toString(),password.getText().toString());
                    System.out.println(adresse);

                    /*
                        On instancie DataManager pour effectuer une requete à la base
                     */
                    DataManager dataManager = new DataManager(ConnexionFragment.this);
                    dataManager.execute(adresse); // execution ici...
                }
            }
        });


        return view;
    }

    /*
        Vérifie si le champs aVerifier est vide ou non
     */
    public boolean isValid(String aVerifier, String nomChamp)
    {
        /*
            On enleve les espaces...
         */
        String tmp = aVerifier.replaceAll("\\s","");
        if(tmp.length() == 0)
        {
            Toast.makeText(getContext(),"Le champ "+nomChamp+" est vide !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*
        Fonction permettant de traiter les résultats recu par une requete http (GET) du serveur,
        dans ce Fragment, on récupera ici si la connexion s'est bien déroulée ou non
     */
    @Override
    public void receiveData(String resultat) {
        /*
            On instancie un JSONObject afin de parcourir le résultat
            si ce dernier est vide alors s'est que le compte n'existe pas
         */
        try {
            JSONObject json = new JSONObject(resultat);
            /*
                Si on ne rentre pas dans l'exception alors on a un résultat,
                on va parcourir ce dernier et créé un sessionManager
             */
            SessionManager sessionManager = new SessionManager(getContext());

            // On commence le parcour du jsonObject
            sessionManager.createSession(json.getString("nom"),
                    json.getString("prenom"),
                    json.getString("email"),
                    json.getString("password"),
                    json.getString("ddn"),
                    json.getString("image"),
                    json.getString("follow"),
                    json.getString("sexe"));
            Toast.makeText(getContext(),"Bonjour "+json.getString("nom")+" !",Toast.LENGTH_SHORT).show();
            setAccueil();

        } catch (JSONException e) {
            Toast.makeText(getContext()," Combinaison email / mot de passe erronée ! ", Toast.LENGTH_SHORT);
        }
    }

    /*
    Renvoie l'utilisateur vers le fragment Accueil, en vue cette fois ci connecté
 */
    public void setAccueil()
    {
        AccueilFragment fragment = new AccueilFragment();
        getActivity().setTitle("Accueil");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        ((MainActivity)getActivity()).setConnection(true); // l'utilisateur est connecté
    }
}