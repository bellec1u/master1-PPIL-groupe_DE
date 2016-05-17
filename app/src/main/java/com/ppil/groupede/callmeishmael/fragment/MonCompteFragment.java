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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.MainActivity;
import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.BitmapManager;
import com.ppil.groupede.callmeishmael.data.SessionManager;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonCompteFragment extends Fragment {

    /*
        Classe permettant de visualiser le Compte d'un utilisateur, et de modifier ses informations personnelles
        ou bien de se désinscrire du site ou bien de mettre son profil en privé
     */

    private Button modifierInfos; // bouton permettant d'aller au fragment ModificationMonProfil
    private Button desinscrire; // bouton entrainant la suppression de l'utilisateur
    private Button prive; // bouton mettant le profil en privé
    private ImageView image; // image personnelle de l'utilisateur
    private TextView identite; // contient le nom ET le prenom de l'utilisateur
    private TextView mail; // affichera l'adresse mail de l'utilisateur
    private TextView ddn; // date de naissance de l'utilisateur
    private TextView sexe; // genre de l'utilisateur

    public MonCompteFragment() {
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
        View view = inflater.inflate(R.layout.fragment_mon_compte, container, false);

        /*
            On charge les divers éléments du fragment
         */
        modifierInfos = (Button) view.findViewById(R.id.action_modification);
        desinscrire = (Button) view.findViewById(R.id.action_desinscription);
        prive = (Button) view.findViewById(R.id.action_profil_prive);
        image = (ImageView) view.findViewById(R.id.imageView);
        identite = (TextView) view.findViewById(R.id.textView);
        mail = (TextView) view.findViewById(R.id.textView5);
        ddn = (TextView) view.findViewById(R.id.textView6);
        sexe = (TextView) view.findViewById(R.id.textView8);

        /*
            On récupère les informations via SessionManager
         */
        SessionManager sessionManager = new SessionManager(getContext());

        /*
            On les place dans les bons éléments du Fragment
         */
        identite.setText(sessionManager.getSessionName()+ " " + sessionManager.getSessionFirstName());
        mail.setText(sessionManager.getSessionEmail());
        ddn.setText(sessionManager.getSessionDate());
        String genre = sessionManager.getSessionGenre();
        if(genre.equals("M"))
        {
            genre = " Homme ";
        }
        else
        {
            genre = " Femme ";
        }
        sexe.setText(genre);
        String urlImage = sessionManager.getSessionURL(); // on charge l'URL

        /*
            On charge BitmapManager afin de récupérer l'image personnelle,
            SI urlImage n'est pas vide, sinon image par défault
         */
        Bitmap img = null; // Bitmap dans lequel sera stocke l'image
        if(urlImage.equals(""))
        {
                        /*
                            On convertie le drawable en Bitmap (image par défault)
                        */
            img = BitmapFactory.decodeResource(getResources(),
                    R.drawable.whale);
            image.setImageBitmap(img); // on affecte l'image ici..
        }
        else
        {
            BitmapManager bitmapManager = new BitmapManager(img);
            try {
                img = bitmapManager.execute(urlImage).get();
                image.setImageBitmap(img); // on affecte l'image ici..
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(getContext()," Une erreur s'est passé dans le chargement de l'image", Toast.LENGTH_SHORT).show();
                image.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                        R.drawable.whale)); // on affecte l'image ici..
            }
        }

        /*
            Listener pour changer de fragment en 1 clic
         */
        modifierInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMonCompteModification();
            }
        });

        return view;
    }

    /*
        Redirige l'utilisateur vers la fragment de modification de compte
     */
    public void setMonCompteModification() {
        ModificationMonProfilFragment fragment = new ModificationMonProfilFragment();
        getActivity().setTitle("Modification du compte");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        ((MainActivity)getActivity()).setConnection(true); // l'utilisateur est connecté
    }

}
