package com.ppil.groupede.callmeishmael.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.DataReceiver;
import com.ppil.groupede.callmeishmael.data.SessionManager;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class CommentaireFragment extends Fragment implements DataReceiver{

    /*
        Fragment permettant la mise en place d'un commentaire,
        plusieurs champs seront disponibles :
        -Auteur
        -Etoile1...2...5
        -Commentaire
        -Bouton modifier
        -Bouton Supprimer
        -Bouton Suivre
     */

    private TextView auteur; //auteur du commentaire
    private ImageView star1; // etoile 1 ...
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5; // derniere etoile
    private TextView resume; // resume
    private Button modifier; // bouton modifier si, auteur = utilisateur connecté
    private Button suivre; // bouton pour suivre l'auteur du commentaire
    private Button supprimer ; // bouton pour supprimer un commentaire

    /*
        Champ propre au commentaire
     */
    private String auteurCommentaire; // auteur du commentaore
    private float noteCommentaire; // note du commentaire
    private String resumeCommentaire; // commentaire
    private boolean suiviCommentaire; // indique si l'utilisateur veut etre suivi ou non
    private boolean monCommentaire; // indique si ce commentaire est celui de l'utilisateur connecté
    private String emailCommentaire;
    private int idCommentaire; // id du commentaire dans la base de donnée
    private DetailsLivreFragment fragmentHome;

    @SuppressLint("ValidFragment")
    public CommentaireFragment(String email, String auteur, float note, String resume, int follow, boolean mine, int id, DetailsLivreFragment frag) {
        // Required empty public constructor
        auteurCommentaire = auteur;
        noteCommentaire = note;
        resumeCommentaire = resume;
        emailCommentaire = email;
        suiviCommentaire = (follow == 1) ; // indique si l'utilisateur veut etre suivi = à 1 donc...
        monCommentaire = mine;
        idCommentaire = id;
        fragmentHome = frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_commentaire, container, false);
        auteur = (TextView) view.findViewById(R.id.commentaire_auteur);
        star1 = (ImageView) view.findViewById(R.id.commentaire_star1);
        star2 = (ImageView) view.findViewById(R.id.commentaire_star2);
        star3 = (ImageView) view.findViewById(R.id.commentaire_star3);
        star4 = (ImageView) view.findViewById(R.id.commentaire_star4);
        star5 = (ImageView) view.findViewById(R.id.commentaire_star5);

        resume = (TextView) view.findViewById(R.id.commentaire_resume);

        modifier = (Button) view.findViewById(R.id.commentaire_modifier);
        supprimer = (Button) view.findViewById(R.id.commentaire_supprimer);
        suivre = (Button) view.findViewById(R.id.commentaire_suivre);

        /*
            On met à jour la visiblité des boutons selon les booléens présents
         */
        if(!(suiviCommentaire && !monCommentaire)){
            suivre.setVisibility(View.GONE);
        }
        if(!monCommentaire)
        {
            modifier.setVisibility(View.GONE);
            supprimer.setVisibility(View.GONE);
            if(!auteurCommentaire.equals("Anonyme")){
            auteur.setOnClickListener(new DetailsUtilisateur());}
        }
        /*
            On affecte aux bons éléments les bonnes valeurs
         */
        auteur.setText(auteurCommentaire);
        resume.setText(resumeCommentaire);
        setStars(noteCommentaire);

        /*
            On affecte aux boutons les listeners correspondants
         */
        modifier.setOnClickListener(new ModifierCommentaire());
        supprimer.setOnClickListener(new SupprimerCommentaire());
        suivre.setOnClickListener(new SuivreCommentaire());

        return view;
    }

    /*
        Convertie la note en étoile
     */
    public void setStars(float note)
    {
        float notation = Float.valueOf(note);
        for(int i = 0 ; i <(int)notation ; i++)
        {
            setStar(i);
        }
        int entier = (int)notation;
        float difference = notation - entier;
        if(Math.round(difference) == 0.5)
        {
            setHalfStar((int) notation);
        }
    }

    /*
       Affecte à l'étoile numero i une image d'étoile pleine
    */
    public void setStar(int i)
    {
        Bitmap img = null;
        switch(i)
        {
            case 0 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.fullstar);
                star1.setImageBitmap(img);
                break;
            case 1 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.fullstar);
                star2.setImageBitmap(img);
                break;
            case 2 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.fullstar);
                star3.setImageBitmap(img);
                break;
            case 3 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.fullstar);
                star4.setImageBitmap(img);
                break;
            case 4 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.fullstar);
                star5.setImageBitmap(img);
                break;
        }
    }

    /*
        Affecte à l'étoile numéro i une étoile mi-pleine
     */
    public void setHalfStar(int i)
    {
        Bitmap img = null;
        switch(i)
        {
            case 0 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.halfstar);
                star1.setImageBitmap(img);
                break;
            case 1 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.halfstar);
                star2.setImageBitmap(img);
                break;
            case 2 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.halfstar);
                star3.setImageBitmap(img);
                break;
            case 3 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.halfstar);
                star4.setImageBitmap(img);
                break;
            case 4 :
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.halfstar);
                star5.setImageBitmap(img);
                break;
        }
    }

    /*
        Fonction appelée lorsque le fragment recoit la reponse venant du serveur.
     */
    @Override
    public void receiveData(String resultat) {
        // effectue un refresh
        DetailsLivreFragment fragment = new DetailsLivreFragment(fragmentHome.getIdLivre(),fragmentHome.getImage());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /*
        Listener appelé lorsque le bouton 'modifier' est cliqué
     */
    class ModifierCommentaire implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //On envoie l'utilisateur vers la page d'écriture d'un commentaire
            EcrireCommentaireFragment ecrire = new EcrireCommentaireFragment(fragmentHome.getIdLivre(),fragmentHome.getImage());
            ecrire.setModification(true);
            getActivity().setTitle("Ecrire un commentaire");
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, ecrire);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    /*
        Listener appelé lorsque le bouton 'supprimer' est cliqué
        L'utilisateur se
     */
    class SupprimerCommentaire implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(v.getContext())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Confirmation de suppression")
                    .setMessage("Voulez vous vraiment supprimer votre commentaire ?")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                /*
                                    L'utilisateur confirme donc son choix, on demande
                                    au serveur de supprimer son commentaire.
                                    On charge l'email de l'utilisateur grace à sessionManager
                                 */
                            SessionManager sessionManager = new SessionManager(getContext());
                            String emailSession = sessionManager.getSessionEmail();
                            String adresse = Data.getData().getURLSupprimerCommentaire();
                            byte[] delete = Data.getData().getPostSupprimerCommentaire(emailSession, idCommentaire);
                            System.out.println(adresse);
                                /*
                                    On instancie et execute DataManager
                                 */
                            DataManager dataManager = new DataManager(CommentaireFragment.this);
                            dataManager.execute(adresse, delete);
                        }

                    })
                    .setNegativeButton("Non", null)
                    .show();
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
            SessionManager sessionManager = new SessionManager(getContext()); // pour verifier si un user est connecté

            if(!sessionManager.isConnected()) {
                String adresse = Data.getData().getURLFollowUser();
                byte[] infos = Data.getData().getPostFollowUser(emailCommentaire, sessionManager.getSessionEmail());

                /*
                    ON appel mainenant DataManager, pour demander le suivi de cet utilisateur
                    et ensuite la fonction ReceiveData
                 */
                DataManager dataManager = new DataManager(CommentaireFragment.this);
                Toast.makeText(getContext()," Vous suivez maintenant "+ auteurCommentaire + ".",Toast.LENGTH_SHORT).show();
                dataManager.execute(adresse,infos);

            }else{
                Toast.makeText(getContext()," Vous devez être connecté pour suivre cet utilisateur !", Toast.LENGTH_SHORT).show();
            }
        }
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
            DetailsUtilisateurFragment ecrire = new DetailsUtilisateurFragment(emailCommentaire); // email de l'auteur du commentaire
            getActivity().setTitle("Profil de " + auteurCommentaire);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, ecrire);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
