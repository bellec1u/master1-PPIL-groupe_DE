package com.ppil.groupede.callmeishmael.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.FileChooser.FileChooser;
import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.BitmapManager;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.DataReceiver;
import com.ppil.groupede.callmeishmael.data.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ModificationMonProfilFragment extends Fragment implements DataReceiver{

    /*
        Classe permettant de modifier le profil d'un utilisateur
     */

    private Button save; // bouton pour s'inscrire
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
    private String cover_url; // url de l'image choisi par l'utilisateur.

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
        View view = inflater.inflate(R.layout.fragment_modification_mon_profil, container, false);
        /*
               On affecte à chaque attribut le bon élement
         */
        prenom = (EditText) view.findViewById(R.id.editText8);
        nom = (EditText) view.findViewById(R.id.editText);
        email = (EditText) view.findViewById(R.id.editText9);
        emailConfirmer = (EditText) view.findViewById(R.id.editText10);
        mdp = (EditText) view.findViewById(R.id.editText11);
        mdpConfirmer = (EditText) view.findViewById(R.id.editText12);
        dateJour = (EditText) view.findViewById(R.id.editText13);
        dateMois = (EditText) view.findViewById(R.id.editText14);
        dateAnnee = (EditText) view.findViewById(R.id.editText15);
        genreF = (RadioButton) view.findViewById(R.id.radioButtonFemme);
        genreM = (RadioButton) view.findViewById(R.id.radioButtonHomme);
        imageButton = (ImageButton) view.findViewById(R.id.imageButton11);
        save = (Button) view.findViewById(R.id.action_confirmer);
        cover_url = "";

        /*
            Ajout d'un onClickListener sur imageButton,
            pour ouvrir une fenetre de recherche d'image
         */
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser fileChooser = new FileChooser(getActivity());
                fileChooser.setFileListener(new FileChooser.FileSelectedListener() {
                    @Override
                    public void fileSelected(final File file) {
                        cover_url = file.getAbsolutePath();
                        /*
                            Si l'URL est choisit alors on peut load la nouvelle image avec BitmapManager
                         */
                        Bitmap bitmap = null;
                        BitmapManager bitmapManager = new BitmapManager(bitmap);
                        try {
                            bitmap = bitmapManager.execute(cover_url).get();
                            imageButton.setImageBitmap(bitmap);
                        } catch (ExecutionException |InterruptedException e) {
                            Toast.makeText(getContext()," Oups ! Une erreur s'est produite...",Toast.LENGTH_SHORT).show();
                        }
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
            On précharge les champs avec les valeurs connues sur cet utilisateur.
            Pour cela, on utilise SessionManager
         */
        final SessionManager sessionManager = new SessionManager(getContext());
        prenom.setText(sessionManager.getSessionFirstName());
        nom.setText(sessionManager.getSessionName());
        email.setText(sessionManager.getSessionEmail());
        emailConfirmer.setText(sessionManager.getSessionEmail());
        String date = sessionManager.getSessionDate();
        String[] dateSpliter = date.split("-");

        /*
            On remplie la date
         */
        dateAnnee.setText(dateSpliter[0]);
        dateMois.setText(dateSpliter[1]);
        dateJour.setText(dateSpliter[2]);

        /*
            On verifie le sexe, et on coche la bonne case
         */
        String sexe = sessionManager.getSessionGenre();
        if(sexe.equals("M"))
        {
            genreM.setChecked(true);
        }else{
            genreF.setChecked(true);
        }

        /*
            On charge l'image de l'utilisateur
         */
        String url = sessionManager.getSessionURL();
        Bitmap img = null;
        if(!url.equals(""))
        {
            if(url.startsWith("http")) {
                BitmapManager bitmapManager = new BitmapManager(img);
                try {
                    img = bitmapManager.execute(url).get();
                    imageButton.setImageBitmap(img); // on affecte l'image ici..
                } catch (ExecutionException | InterruptedException e) {
                    //nothing
                }
            }else{
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(url,bmOptions);
                bitmap = Bitmap.createScaledBitmap(bitmap,48,48,true);
                imageButton.setImageBitmap(bitmap);
            }
        }

        /*
            Ajout du listener sur le bouton enregistrer les modifications
         */
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok1, ok2, ok3, ok4, ok5;

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

                /*
                    On recherche le genre
                 */
                String genre = "";
                if(genreF.isChecked())
                {
                    genre = "F";
                }else{
                    genre = "M";
                }

                /*
                    Si tous les champs sont corrects alors on valide les changements, sinon
                    on informe l'utilisateur que la modification n'est pas possible
                 */
                if(ok1 && ok2 && ok3 && ok4 && ok5)
                {
                    /*
                        On instancie DataManager, pour effectuer les changements.
                        Mais tout d'abord nous devons charger l'adresse via la classe Data
                     */
                    String adresse = Data.getData().getModification(nom.getText().toString(),
                            prenom.getText().toString(),
                            email.getText().toString(),
                            mdp.getText().toString(),
                            cover_url,
                            genre,
                            (annee + "-" + mois + "-" + jour) ,
                            sessionManager.getSessionEmail(),
                            sessionManager.getSessionPassword());
                    //Acces a la base de donnée ici...
                    DataManager dataManager = new DataManager(ModificationMonProfilFragment.this);
                    dataManager.execute(adresse); // execution en arriere plan
                }else{
                    Toast.makeText(getContext(),"Modification(s) impossible !",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return  view;
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
                Toast.makeText(getContext()," Année incorrecte !",Toast.LENGTH_SHORT);
                return false;
            }
            if (mois < 1 || mois > 12) {
                Toast.makeText(getContext()," Mois incorrect !",Toast.LENGTH_SHORT);
                return false;
            }
            if (jour < 1 || jour > 31) {
                Toast.makeText(getContext()," Jour incorrect !",Toast.LENGTH_SHORT);
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
 dans ce Fragment, on indiquera si les modifications se sont bien déroulés ou non
    */
    @Override
    public void receiveData(String resultat) {
        /*
            Si la modification s'est bien passé alors on convertie le String en JSONObject et on le traite
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
                sessionManager.logOut();
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
                        json.getString("sex"));
                setProfile();
            }
            else
            {
                Toast.makeText(getContext(),"Une erreur s'est produite\n" +
                        " Veuillez réessayer",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Toast.makeText(getContext(),"Une erreur s'est produite\n Veuillez réessayer", Toast.LENGTH_SHORT).show();
        }
    }

    /*
        Renvoie l'utilisateur vers son profil
     */
    public void setProfile()
    {
        MonCompteFragment fragment = new MonCompteFragment();
        getActivity().setTitle("Profil");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
