package com.ppil.groupede.callmeishmael.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.DataReceiver;
import com.ppil.groupede.callmeishmael.data.SessionManager;


public class ResultatUtilisateurSuiviFragment extends Fragment implements DataReceiver{
    private TextView nom,date;
    private String nom_res,prenom_res,date_res,email_res;
    private Button info,suivre;
    private ImageView img;
    private Bitmap img_res;
    private boolean image_res;

    public ResultatUtilisateurSuiviFragment(Bitmap img, boolean image, String first_name, String last_name, String date,String email) {
       nom_res = last_name;
        prenom_res = first_name;
        date_res = date;
        email_res = email;
        if(image) {
            img_res = img;
            image_res = true;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_resultat_utilisateur_suivi, container, false);
        nom = (TextView) view.findViewById(R.id.nom);
        nom.setText(nom_res + " " +prenom_res);
        date = (TextView) view.findViewById(R.id.date);
        System.out.println(date_res);
        date.setText(date_res);
        info = (Button) view.findViewById(R.id.info);
        suivre = (Button) view.findViewById(R.id.suivre);

        img = (ImageView) view.findViewById(R.id.image);

        //on met l'image que si y 'en a une
        if(image_res)
            img.setImageBitmap(img_res);

        //affichage du titre en entier
        nom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),nom_res + " " + prenom_res, Toast.LENGTH_SHORT).show();

            }
        });


        info.setOnClickListener(new DetailsUtilisateur());
        suivre.setOnClickListener(new SuivreCommentaire());


        // Inflate the layout for this fragment
        return view;
    }


    public void receiveData(String resultat) {

        UtilisateurSuiviFragment fragment = new UtilisateurSuiviFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /*
     En cas de click, permet à l'utilisateur d'etre rediriger vers le profil d'un autre utilisateur
  */
    class DetailsUtilisateur implements View.OnClickListener{

        /*
            Fonction appelée en cas de clic
         */
        @Override
        public void onClick(View v) {
            //renvoie vers le profil de l'utilisateur
            System.out.println(email_res);
            DetailsUtilisateurFragment ecrire = new DetailsUtilisateurFragment(email_res, true); // email de l'auteur du commentaire
            getActivity().setTitle("Profil de " + nom_res + " " + prenom_res);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, ecrire);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    /*
Listener appelé lorsque le bouton 'suivre' est enclenché,
cependant cette fonctionnalité n'est pas encore implanté
*/
    class SuivreCommentaire implements View.OnClickListener{


        @Override
        public void onClick(View v) {
            //// TODO: 18/05/16
            SessionManager sessionManager = new SessionManager(getContext());


                String adresse = Data.getData().getURLUnFollowUser();

                byte[] infos = Data.getData().getPostFollowUser(email_res, sessionManager.getSessionEmail());

                /*
                    ON appel mainenant DataManager, pour demander le suivi de cet utilisateur
                    et ensuite la fonction ReceiveData
                 */
            DataManager dataManager = new DataManager(ResultatUtilisateurSuiviFragment.this);
            dataManager.execute(adresse, infos);

                    Toast.makeText(getContext()," Vous ne suivez plus maintenant cet utilisateur.",Toast.LENGTH_SHORT).show();

        }
    }

}
