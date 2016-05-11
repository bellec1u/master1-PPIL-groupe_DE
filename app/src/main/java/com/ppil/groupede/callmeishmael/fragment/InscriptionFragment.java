package com.ppil.groupede.callmeishmael.fragment;

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

import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.SessionManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class InscriptionFragment extends Fragment {

    final static String DATE_FORMAT = "dd-MM-yyyy"; // format de la date

    private Button register;
    private ImageButton imageButton;
    private RadioButton genre;
    private EditText dateJour;
    private EditText dateMois;
    private EditText dateAnnee;
    private EditText mdp;
    private EditText mdpConfirmer;
    private EditText email;
    private EditText emailConfirmer;
    private EditText nom;
    private EditText prenom;

    public InscriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inscription, container, false);
        prenom = (EditText)view.findViewById(R.id.idPrenom);
        nom =  (EditText)view.findViewById(R.id.idNom);
        email =  (EditText)view.findViewById(R.id.idEmail);
        emailConfirmer =  (EditText)view.findViewById(R.id.idEmailConfirm);
        mdp =  (EditText)view.findViewById(R.id.idPassword);
        mdpConfirmer =  (EditText)view.findViewById(R.id.idPasswordConfirm);
        dateJour =  (EditText)view.findViewById(R.id.dateDay);
        dateMois =  (EditText)view.findViewById(R.id.dateMonth);
        dateAnnee =  (EditText)view.findViewById(R.id.dateYear);
        genre = (RadioButton)view.findViewById(R.id.Female);
        imageButton = (ImageButton)view.findViewById(R.id.ProfilImage);
        register = (Button)view.findViewById(R.id.idConfirm);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail, mailC;
                mail = email.getText().toString();
                mailC = emailConfirmer.getText().toString();
                boolean mailOK = true;
                if(!mail.equals(mailC))
                {
                    Toast.makeText(getContext(),"Les emails ne correspondent pas !", Toast.LENGTH_SHORT).show();
                    mailOK = false;
                }
                else
                {
                    if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()))
                    {
                        mailOK = false;
                    }
                }
                if(mailOK) {
                    String pwd, pwdC;
                    pwd = mdp.getText().toString();
                    pwdC = mdpConfirmer.getText().toString();
                    boolean pwdOK = true;
                    if (!pwd.equals(pwdC)) {
                        Toast.makeText(getContext(), "Les mots de passe ne correspondent pas !", Toast.LENGTH_SHORT).show();
                        pwdOK = false;
                    }
                    if(pwdOK){
                        String jour,mois,annee;
                        jour = dateJour.getText().toString();
                        mois = dateMois.getText().toString();
                        annee = dateAnnee.getText().toString();
                        String date = jour+'-'+mois+'-'+annee ;
                        boolean dateOK = isDateValid(date);
                        if(dateOK)
                        {
                            //On inscrit l'utilisateur
                            String prenm = prenom.getText().toString();
                            String nm = nom.getText().toString();
                            String sexe = "M";
                            if(genre.isChecked())
                            {
                                sexe = "F";
                            }
                            DataManager dm = new DataManager();
                            dm.setUrlRegister(prenm, nm, mail, pwd, sexe, date);
                            dm.run();
                            String result = dm.getResult();
                            if(result.equals("true"))
                            {
                                Toast.makeText(getContext(),"Bienvenue "+prenm,Toast.LENGTH_SHORT);
                                SessionManager session = new SessionManager(getContext());
                                session.createUserSession(mail);
                                // Set the fragment of view
                                AccueilFragment fragment = new AccueilFragment();
                                getActivity().setTitle("Accueil");
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_container, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                            Log.i("INFO", result);
                        }
                        else
                        {
                            Toast.makeText(getContext(), "La date est incorrecte !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        return view;
    }

    public static boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}

