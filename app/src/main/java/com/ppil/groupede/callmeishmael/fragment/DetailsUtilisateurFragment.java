package com.ppil.groupede.callmeishmael.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.BitmapManager;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.DataReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsUtilisateurFragment extends Fragment implements DataReceiver{
    /*
        Ce fragment affiche le profil d'un utilisateur,
        ce profil est différent de MonCompteFragment car ce dernier n'affiche pas toutes les
        informations liées à l'utilisateur, on aura accès ici aux commentaires de l'utilisateur,
        accès également à sa liste de lecture, et à ses informations publiques que sont :
        - nom
        - prenom
        - date de naissance
        - image personnelle
     */
    private String email; // email de l'utilisateur
    private TextView identite; // nom et prenom
    private ImageView image; // image personnelle de l'utilisateur
    private TextView date; // date de naissance de l'utilisateur
    private TextView sexe; // sexe de l'utilisateur
    private LinearLayout listeLecture ; // liste de lecture de l'utilisateur
    private LinearLayout listeCommentaire; // liste des commentaires de l'utilisateur

    public DetailsUtilisateurFragment(String email) {
        // Required empty public constructor
        this.email = email;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_utilisateur, container, false);

        /*
            On remplie chaque attribut avec la bonne balise xml
         */
        identite = (TextView) view.findViewById(R.id.identite);
        date = (TextView) view.findViewById(R.id.ddn);
        sexe = (TextView) view.findViewById(R.id.utilisateur_sexe);
        image = (ImageView) view.findViewById(R.id.imagePerso);
        listeLecture = (LinearLayout) view.findViewById(R.id.layout_liste_lecture);
        listeCommentaire = (LinearLayout) view.findViewById(R.id.layout_liste_evaluation);

        /*
            On va maintenant demander à la base de remplir les champs ci-dessus
            on va tout d'abord récupérer l'adresse et remplir le champ POST nécessaire
         */
        String adresse = Data.getData().getURLDetailsUser();
        byte[] infos = Data.getData().getPostDetailsUser(email);

        DataManager dataManager = new DataManager(this);
        dataManager.execute(adresse,infos);


        return view; // retourne la view
    }


    /*
        Fonction appelée lorsque DataManager a finit de récupérer les informations de l'utilisateur
     */
    @Override
    public void receiveData(String resultat) {
        System.out.println(resultat);
        try {
            JSONObject array= new JSONObject(resultat); // la taille est forcement de 3
            Object infosUtilisateur = array.get("infos");
            Object listeLecture = array.get("lecture");
            Object listeCommentaire = array.get("comments");

            //On instancie ensuite les JSONObjects correspondants
            JSONObject utilisateur = new JSONObject(infosUtilisateur.toString());


            /*
                On remplie les champs relatifs à l'utilisateur grace au JSONObject utilisateur
             */
            identite.setText(utilisateur.getString("prenom") + utilisateur.getString("nom"));
            date.setText(utilisateur.getString("ddn"));
            String sex = utilisateur.getString("sexe");
            if(sex.equals("m") || sex.equals("M")){
                sexe.setText("Homme");
            }else{
                sexe.setText("Femme");
            }

            String urlImage = utilisateur.getString("image");
            Bitmap img = null;
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
            /*
                Télécharge une image à partir de la cover_url de l'utilisateur.
             */
                if(!urlImage.startsWith("http")) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(urlImage, options);
                    image.setImageBitmap(bitmap);
                }else {

                    BitmapManager bitmapManager = new BitmapManager(img);
                    try {
                        img = bitmapManager.execute(urlImage).get();
                        image.setImageBitmap(img); // on affecte l'image ici..
                    } catch (ExecutionException | InterruptedException e) {
                        Toast.makeText(getContext(), " Une erreur s'est passé dans le chargement de l'image", Toast.LENGTH_SHORT).show();
                        image.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                                R.drawable.whale)); // on affecte l'image ici..
                    }
                }
            }

            /*
                ON s'interesse maintenant a la liste de lecture
             */
            JSONObject lectures = new JSONObject(listeLecture.toString());
            /*
                Besoin d'instancier les bons éléments
             */
            for(int i = 0 ; i < lectures.length() ; i++)
            {
                System.out.println(lectures.getString("livre"+i));
            }

            /*
                On s'interesse maitnenant aux commentaires de l'utilisateur
             */
            JSONObject comments = new JSONObject(listeCommentaire.toString());

            /*
                On parcourt les élements, et on les isntancie dans les futurs bons élements
             */
            for(int i = 0 ; i < comments.length() ; i++)
            {
                System.out.println(comments.getString("comment"+i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
