package com.ppil.groupede.callmeishmael.fragment;


import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.ppil.groupede.callmeishmael.MainActivity;
import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.DataReceiver;
import com.ppil.groupede.callmeishmael.data.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConnexionFragment extends Fragment implements DataReceiver, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    /*
        Classe permettant à l'utilisateur de se connecter, soit en remplissant les champs
        Email et Password ou bien en cliquant sur les boutons facebook ou google+
     */
    private Button logIn; // bouton de logIn
    private AutoCompleteTextView email; // champ où l'utilisateur écrit son mail
    private EditText password; // champ contenant le mot de passe


    //Boutons g+
    private static final int RC_SIGN_IN = 0;
    private ConnectionResult connResult;
    private boolean intentEnCours;
    private TextView mdpOublie;

    private int requestCode;

    private boolean btSignInCliqueGoogle;


    private Button bt_deco_google;
    private SignInButton bt_login_google;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog pg;


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
        mdpOublie = (TextView) view.findViewById(R.id.mdpOub); // txtview mdpoub


        /**                       *********************GOOGLE+*******************                 */
        //récupération de l'id du bouton g+
        bt_login_google = (SignInButton) view.findViewById(R.id.bt_sign_in_google);
        bt_deco_google = (Button) view.findViewById(R.id.bt_deconnexion);

        affichageBoutonGoogle(false);
        //construction api client Google
        constructionApiClientGoogle();

        customizeBtGoogle();

        pg= new ProgressDialog(this.getContext());
        btSignInCliqueGoogle=false;

        //listener sur le clic du bouton g+
        bt_login_google.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //switch (v.getId()) {
                //case R.id.bt_sign_in_google:

                //Toast.makeText(getContext(), "Connexion en cours", Toast.LENGTH_SHORT).show();
                System.out.println("***************touch**************");
                //mGoogleApiClient.connect();
                btSignInCliqueGoogle=true;
                connexionGoogle();
                //getProfileInfo();
                //break;
                //}
                //onClick(v);
            }


        });


        //listener sur le bouton de déconnexion de g+
        bt_deco_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Déconnexion en cours", Toast.LENGTH_SHORT).show();
                System.out.println("deconnexion en cours");
                deconnexionGoogle();
                //getProfileInfo();
            }
        });

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

        mdpOublie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMdpOublie();

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
                        json.getString("sex"));
                Toast.makeText(getContext(), "Bonjour " + json.getString("prenom") + " !", Toast.LENGTH_SHORT).show();
                setAccueil();

            } catch (JSONException e) {
                Toast.makeText(getContext(), " Combinaison email / mot de passe erronée ! ", Toast.LENGTH_SHORT).show();
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

    public void setMdpOublie()
    {
        MdpOublieFragment fragment = new MdpOublieFragment();
        getActivity().setTitle("Mdp oublié");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        ((MainActivity)getActivity()).setConnection(true); // l'utilisateur est connecté
    }

    /*    ***************************************Google + ****************************************** */


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        btSignInCliqueGoogle=false;
        //getProfileInfo();
        affichageBoutonGoogle(true);
        System.out.println("*on connected*");
        System.out.println("Connexion : "+mGoogleApiClient.isConnected());
        Toast.makeText(getContext(), "Connexion Réussie !", Toast.LENGTH_LONG).show();
        //this.setAccueil();
        this.getActivity();

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
        affichageBoutonGoogle(false);
        Toast.makeText(getContext(), "La connexion a été suspendue", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.bt_sign_in_google:

                Toast.makeText(getContext(), "Connexion en cours", Toast.LENGTH_SHORT).show();

                mGoogleApiClient.connect();
                connexionGoogle();
                break;
        }*/
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        System.out.println("*************connection failed***********");
        if (!(connectionResult.hasResolution())) {
            GoogleApiAvailability.getInstance().getErrorDialog(this.getActivity(), connectionResult.getErrorCode(), requestCode).show();
        }

        if (!(intentEnCours)) {
            connResult=connectionResult;
            System.out.println("********non intent en cours********* --D "+connectionResult);
            System.out.println("********connResult********* --D "+connResult);

            if (btSignInCliqueGoogle) {
                resolveSignInError();
            }
        }
    }


    @Override
    public void onActivityResult(int rCode, int resultCode, Intent data) {
        System.out.println("requestCode :"+rCode);
        if (rCode == RC_SIGN_IN) {
            requestCode=rCode;
            if (resultCode != Activity.RESULT_OK) {
                btSignInCliqueGoogle = false;
                pg.dismiss();
            }

            intentEnCours = false;

            if (!(mGoogleApiClient.isConnecting())) {
                mGoogleApiClient.connect();
                System.out.println("***connect***");
            }
        }
    }



    /*
           Gere l'API Google+
           et la connexion via le bouton Google+
        */
    public void connexionGoogle()
    {
        mGoogleApiClient.connect();

        if (mGoogleApiClient.isConnecting()) {
            Log.d("Utilisateur connecté(e)", "connected");
            Toast.makeText(this.getContext(), "Connexion en cours", Toast.LENGTH_SHORT).show();

            pg.show();
            //verification de connResult (resultat de la tentative de connexion
            //si null, connResult aura les caracteritique d'une connexion
            if (connResult == null) {
                connResult=new ConnectionResult(ConnectionResult.SIGN_IN_REQUIRED, PendingIntent.readPendingIntentOrNullFromParcel(Parcel.obtain()), null);
            }
            resolveSignInError();

            System.out.println("*********statut : "+mGoogleApiClient.isConnecting()+"***********");
        }
        System.out.println("********Connexion : "+mGoogleApiClient.isConnected()+"************");

    }

    /*
           Gere l'API Google+
           et la déconnexion via Google+
        */
    public void deconnexionGoogle() {
        System.out.println("Connexion2 : "+mGoogleApiClient.isConnected());
        if (mGoogleApiClient.isConnected()) {
            System.out.println("*******signout*********");
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            //mGoogleApiClient.connect();
            affichageBoutonGoogle(false);
        }
    }

    //Gestion de l'API Google Client
    public void constructionApiClientGoogle() {
        //construction API Client
        mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();
        //Fin
    }

    /*
    * Méthode alternant l'affichage des boutons de connexion et de deco g+ en fonction de l'état
    * etatBtGoogle l'état (true si connecté)
     */
    public void affichageBoutonGoogle(boolean etatBtGoogle) {
        System.out.println("***Affichage bouton*** --D" + etatBtGoogle);
        if (etatBtGoogle) {
            bt_login_google.setVisibility(View.GONE);
            bt_deco_google.setVisibility(View.VISIBLE);
        } else {
            bt_login_google.setVisibility(View.VISIBLE);
            bt_deco_google.setVisibility(View.GONE);
            bt_login_google.setVisibility(View.VISIBLE);
            bt_deco_google.setVisibility(View.GONE);
        }
    }

    //customisation du bouton de connexion g+ aux standards g+
    public void customizeBtGoogle() {
        bt_login_google.setSize(SignInButton.SIZE_STANDARD);
        bt_login_google.setScopes(new Scope[]{Plus.SCOPE_PLUS_LOGIN});
    }


    //Méthode de résolution d'erreur de connexion G+
    public void resolveSignInError() {
        System.out.println("********connResult2********* --D "+connResult);
        System.out.println("********hasResult ?********* --D "+connResult.hasResolution());

        if (connResult.hasResolution()) {
            try {
                intentEnCours = true;
                connResult.startResolutionForResult(this.getActivity(), RC_SIGN_IN);
                Log.d("Erreur résolue","Erreur de connexion résolue");
                System.out.println("********Connexion after resolve: "+mGoogleApiClient.isConnected()+"************");
            } catch (IntentSender.SendIntentException e) {
                intentEnCours = false;
                mGoogleApiClient.connect();
            }
        }
    }


    /* méthodes de récupérations des données G+
    private void getProfileInfo() {

        if (mGoogleApiClient.isConnected()) {
            try {
                if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                //récupérer la personne courante de g+
                    Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                    //setInformationsPersonnelles(currentPerson);
                } else {
                    Toast.makeText(getContext(), "Aucune information personnelle", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
*/
    /*
    //récuperer les infos personnelles de la personne
    private void setInformationsPersonnelles(Person person) {
        String pNom = person.getDisplayName();
        String pUrlPhoto = person.getImage().getUrl();
        String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
        user_name.setText("Nom : "+pNom);
        idMail.setText("Adresse mail : " +email);
        date_Naissance.setText("Date de naissance : "+person.getBirthday());
        tag_line.setText("Tag : " +person.getTagline());
        aPropos.setText("À propos de moi : "+person.getAboutMe());
        setPhotoProfile(pUrlPhoto);
        pg.dismiss();
        Toast.makeText(this, "Voici vos informations personnelles", Toast.LENGTH_LONG).show();
    }*/



}