package com.ppil.groupede.callmeishmael.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.R;

/**
 * Created by paul on 18/05/2016.
 */
public class RenvoieValidationFragment extends Fragment {

    private Button ok;
    private EditText email;

    public RenvoieValidationFragment(){
        // required empty constructor

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*
            On récupère les élements à partir de la view
            et on les affecte à l'attribut de classe correspondant.
         */

        View view = inflater.inflate(R.layout.fragment_renvoi_mail_valditation, container, false);

        ok = (Button) view.findViewById(R.id.button_ok);
        email = (EditText) view.findViewById(R.id.editText_mail);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailChecker(email.getText().toString());
//REQUETE EMAIL ENVOI VALIDATION
            }});

        return view;
    }

    /*
 Vérifie l'intégrité des emails, s'ils sont vides, ou différents ou si les adresses ne sont pas correctes
*/
    public boolean emailChecker(String email)
    {
        String emailtmp; // pour stocker les strings sans les espaces
        /*
            On supprime les espaces
         */
        emailtmp = email.replaceAll("\\s","");

        if(emailtmp.length() == 0)
        {
            Toast.makeText(getContext()," L'email est vide !", Toast.LENGTH_SHORT).show();
            return false;
        }


        if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(emailtmp).matches()))
        {
            Toast.makeText(getContext()," L'adresse mail n'est pas correcte !", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
