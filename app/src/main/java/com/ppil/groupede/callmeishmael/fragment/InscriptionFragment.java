package com.ppil.groupede.callmeishmael.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.FileChooser.FileChooser;
import com.ppil.groupede.callmeishmael.MainActivity;
import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.DataReceiver;
import com.ppil.groupede.callmeishmael.data.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.sql.SQLOutput;

/**
 * Created by Pima on 16/05/16.
 */
public class InscriptionFragment extends Fragment implements DataReceiver {

    /*
        Classe permettant à un utilisateur de s'inscrire en remplissant le formulaire
        et/ou en cliquant sur un bouton Facebook ou Google+
        L'inscription est soumise à certaines conditions, champs non vide etc...
     */
    final static String DATE_FORMAT = "dd-MM-yyyy"; // format de la date

    private Button register; // bouton pour s'inscrire
    private ImageButton imageButton; // imagebutton pour choisir l'image personnelle
    private RadioButton genreF; // le genre de l'utilisateur
    private RadioButton genreM ; // le genre de l'utilisateur
    private EditText dateJour; // date du jour
    private EditText dateMois; // date du mois
    private EditText dateAnnee; // date de l'année
    private EditText mdp; // mot de passe
    private EditText mdpConfirmer; // confimation de mot de passe
    private EditText email; // email
    private EditText emailConfirmer; // confirmation de mail
    private EditText nom; // nom de l'utilisateur
    private EditText prenom; // prenom de l'utilisateur
    private Button dejaInscris; // bouton pour retourner à la vue connexion

    private String cover_url ; // url de l'image de profile de l'utilisateur

    /*
        Constructeur d'InscriptionFragment vide...
     */
    public InscriptionFragment() {
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
        View view = inflater.inflate(R.layout.fragment_inscription, container, false);
        /*
            On affecte à chaque attribut le bon élement XML correspondant
         */
        cover_url = "";
        prenom = (EditText) view.findViewById(R.id.idPrenom);
        nom = (EditText) view.findViewById(R.id.idNom);
        email = (EditText) view.findViewById(R.id.idEmail);
        emailConfirmer = (EditText) view.findViewById(R.id.idEmailConfirm);
        mdp = (EditText) view.findViewById(R.id.idPassword);
        mdpConfirmer = (EditText) view.findViewById(R.id.idPasswordConfirm);
        dateJour = (EditText) view.findViewById(R.id.dateDay);
        dateMois = (EditText) view.findViewById(R.id.dateMonth);
        dateAnnee = (EditText) view.findViewById(R.id.dateYear);
        genreF = (RadioButton) view.findViewById(R.id.Female);
        genreM = (RadioButton) view.findViewById(R.id.Male);
        imageButton = (ImageButton) view.findViewById(R.id.ProfilImage);

        /*
            Listener permttant d'ouvrir une fenetre de recherche de fichier dans le device
         */
        imageButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               FileChooser fileChooser = new FileChooser(getActivity());
                                               fileChooser.setFileListener(new FileChooser.FileSelectedListener() {
                                                   @Override
                                                   public void fileSelected(final File file) {
                                                       cover_url = file.getAbsolutePath();
                                                   }
                                               });
                                               /*
                                                    On ne cherche que des fichiers de type image, donc jpg ici
                                                */
                                               fileChooser.setExtension(".jpg");
                                               /*
                                                    On ouvre la fenetre de dialogue
                                                */
                                               fileChooser.showDialog();
                                           }
                                       });

        /*
            les deux boutons
         */
        register = (Button) view.findViewById(R.id.idConfirm);
        dejaInscris = (Button) view.findViewById(R.id.dejaInscris);

        /*
            Ajout du listener setConnexion, lors d'un click l'utilisateur sera redirigé vers la vue connexion
         */
        dejaInscris.setOnClickListener(new SetConnexion(getActivity(),getFragmentManager()));

        /*
            Ajout du listener (anonyme) qui va vérifier l'intégrité des champs entrés par l'utilisateur
         */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok1,ok2,ok3,ok4,ok5; // contiendra les resultats des tests de validités
                ok1 = isValid(prenom.getText().toString(), "Prenom"); // verification du champs prenom
                ok2 = isValid(nom.getText().toString(), "Nom"); // verification du champs nom
                ok3 = passwordChecker(mdp.getText().toString(), mdpConfirmer.getText().toString()); // verification des mots de passe
                ok4 = emailChecker(email.getText().toString(), emailConfirmer.getText().toString()); // verification des emails
                /*
                    Verification de la date
                 */
                String jour,mois,annee;
                jour = dateJour.getText().toString();
                mois = dateMois.getText().toString();
                annee = dateAnnee.getText().toString();
                ok5 = isDateValid(annee, mois, jour); // on verifie l'intégrité de la date ici...

                System.out.println(ok1 + " "+ ok2 + " "+ ok3 + " "+ ok4 + " "+ ok5);
                /*
                    On verifie le genre
                 */
                String sexe = "";
                if(genreF.isChecked() || genreM.isChecked()) {
                    if (genreF.isChecked()) {
                        sexe = "F";
                    }
                    if (genreM.isChecked()) {
                        sexe = "M";
                    }
                }
                else
                {
                    //Le genre n'est pas coché
                    Toast.makeText(getContext(),"Veuillez cocher votre genre",Toast.LENGTH_SHORT).show();
                }

                /*
                    On verifie la validité des champs, s'ils sont tous true, alors on inscrira l'utilisateur
                 */
                if(ok1 && ok2 && ok3 && ok4 && ok5)
                {
                    /*
                        On instancie Data, pour récupérer le bon URL
                        puis on instanciera DataManager et on lancera la procedure d'inscription
                     */
                    byte[] data = Data.getData().getPostInscription(nom.getText().toString(),
                            prenom.getText().toString(),
                            email.getText().toString(),
                            mdp.getText().toString(),
                            cover_url,
                            sexe,
                            (annee + "-" + mois + "-" + jour));

                    String adresse = Data.getData().getURLInscription();
                    // Procedure d'inscription
                    DataManager dataManager = new DataManager(InscriptionFragment.this);
                    dataManager.execute(adresse,data);
                }

            }
        });
        return view;
    }

    /*
        Fonction vérifiant la validité de la date
     */
    public boolean isDateValid(String anneeS, String moisS, String jourS) {

        /*
            On verifie que les parametres ne sont pas vides
         */
        if (!anneeS.equals("") && !moisS.equals("") && !jourS.equals("")) {
            // On vérifie l'année, le mois et le jour
            int annee, mois, jour;
            annee = Integer.valueOf(anneeS);
            mois = Integer.valueOf(moisS);
            jour = Integer.valueOf(jourS);
            if (annee < 1900 || annee > 2016) {
                Toast.makeText(getContext()," Année incorrecte !",Toast.LENGTH_SHORT).show();
                return false;
            }
            if (mois < 1 || mois > 12) {
                Toast.makeText(getContext()," Mois incorrect !",Toast.LENGTH_SHORT).show();
                return false;
            }
            if (jour < 1 || jour > 31) {
                Toast.makeText(getContext()," Jour incorrect !",Toast.LENGTH_SHORT).show();
                return false;
            }

            return true; // sinon la date semble correcte
        }
        else
        {
            return false;
        }
    }

    /*
        Vérifie la validité du champ en parametre
        si vide etc...
     */
    public boolean isValid(String champ, String nomChamp)
    {
        String tmp = champ.replaceAll("\\s",""); // on supprime les espaces
        /*
            Si vide alors on affiche message d'erreur dans un Toast
         */
        if(tmp.length() == 0){
            Toast.makeText(getContext()," Le champ "+ nomChamp + " est vide !", Toast.LENGTH_SHORT).show();
            return false;
        }
        champ = tmp.replaceAll("-","");
        if(champ.length() == 0)
        {
            Toast.makeText(getContext()," Le champ "+ nomChamp + " est incorrect !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*
        Vérifie l'intégrité des champs password et passwordConfirmer
        si les champs sont vides, ou différents
     */
    public boolean passwordChecker(String password, String passwordConfirmer)
    {
        String mdpTmp, mdpConfirmerTmp; // pour stocker les strings sans espace
        /*
            On supprime les espaces
         */
        mdpTmp = password.replaceAll("\\s","");
        mdpConfirmerTmp = passwordConfirmer.replaceAll("\\s","");

        if(mdpTmp.length() == 0)
        {
            Toast.makeText(getContext()," Le mot de passe est vide !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(mdpConfirmerTmp.length() == 0)
        {
            Toast.makeText(getContext()," La confirmation du mot de passe est vide !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(mdpTmp.length() < 7)
        {
            Toast.makeText(getContext()," Le mot de passe ne fait pas au minimum 8 caractères !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(mdpConfirmerTmp.length() < 7)
        {
            Toast.makeText(getContext()," La confirmation du mot de passe ne fait pas au minimum 8 caractères !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!mdpTmp.equals(mdpConfirmerTmp))
        {
            Toast.makeText(getContext()," Les mots de passes ne sont pas identiques !", Toast.LENGTH_SHORT).show();
            return false;
        }
    return true;
    }

    /*
        Vérifie l'intégrité des emails, s'ils sont vides, ou différents ou si les adresses ne sont pas correctes
     */
    public boolean emailChecker(String email, String emailConfirmer)
    {
        String emailTmp, emailConfirmerTmp; // pour stocker les strings sans les espaces
        /*
            On supprime les espaces
         */
        emailTmp = email.replaceAll("\\s","");
        emailConfirmerTmp = emailConfirmer.replaceAll("\\s","");

        if(emailTmp.length() == 0)
        {
            Toast.makeText(getContext()," L'email est vide !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(emailConfirmerTmp.length() == 0)
        {
            Toast.makeText(getContext()," L'email de confirmation est vide !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!emailTmp.equals(emailConfirmerTmp))
        {
            Toast.makeText(getContext()," Les emails ne sont pas identiques !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(emailTmp).matches()))
        {
            Toast.makeText(getContext()," L'adresse mail n'est pas correcte !", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(emailConfirmerTmp).matches()))
        {
            Toast.makeText(getContext()," L'adresse mail n'est pas correcte !", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /*
     Fonction permettant de traiter les résultats recu par une requete http (GET) du serveur,
     dans ce Fragment, on récupera ici si l'inscription s'est bien déroulé ou non
    */
    @Override
    public void receiveData(String resultat) {

        /*
            Si l'inscription s'est bien passé alors on convertie le String en JSONObject et on le traite
         */
        System.out.println(resultat);
        try {
            JSONObject json = new JSONObject(resultat);
            /*
                S'il a le champ nom alors c'est que l'inscription s'est bien déroulé, sinon
                j'informe l'utilisateur qu'une erreur s'est produite lors de l'inscription.
             */
            if(json.getString("email") != null)
            {
                /*
                    On charge SessionManager de facon à mieux gérer l'utilisateur
                 */
                SessionManager sessionManager = new SessionManager(getContext());
                /*
                    On créé la session
                 */
                sessionManager.createSession(json.getString("nom"),
                        json.getString("prenom"),
                        json.getString("email"),
                        json.getString("password"),
                        json.getString("ddn"),
                        json.getString("image"),
                        json.getString("follow"),
                        json.getString("sexe"));

                Toast.makeText(getContext(),"Un mail vous à été envoyé !",Toast.LENGTH_SHORT).show();
                setAccueil(); // envoie vers l'accueil
            }
            else
            {
                Toast.makeText(getContext(),"L'adresse email indiquée est déjà présente !",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(getContext(),"L'adresse email indiquée est déjà présente !", Toast.LENGTH_SHORT).show();
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
        fragmentTransaction.addToBackStack("Accueil");
        fragmentTransaction.commit();
        ((MainActivity)getActivity()).setConnection(true); // l'utilisateur est connecté
    }
}

/*
    Listener permettant lors d'un click d'être rediriger vers le fragment Connexion
 */
class SetConnexion implements View.OnClickListener{

    private Activity activity; // activity courante
    private FragmentManager fragmentManager; // permet de gérer les activitys / fragments
    /*
        Constructeur de SetConnexion
     */
    public SetConnexion(Activity act, FragmentManager fragment)
    {
        activity = act;
        fragmentManager = fragment;
    }

    /*
        Fonction permettant de gérer le click sur le bouton 'deja inscrit ?'
     */
    @Override
    public void onClick(View v) {
        ConnexionFragment fragment = new ConnexionFragment();
        activity.setTitle("Connexion");
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
