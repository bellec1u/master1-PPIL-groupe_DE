package com.ppil.groupede.callmeishmael.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.BitmapManager;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.DataReceiver;
import com.ppil.groupede.callmeishmael.data.SessionManager;

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
    private boolean follow; // si vrai alors l'utilisateur connecté suit actuellement cet utilisateur
    private TextView identite; // nom et prenom
    private ImageView image; // image personnelle de l'utilisateur
    private TextView date; // date de naissance de l'utilisateur
    private TextView sexe; // sexe de l'utilisateur
    private LinearLayout listeLecture ; // liste de lecture de l'utilisateur
    private LinearLayout listeCommentaire; // liste des commentaires de l'utilisateur
    private Button suivre; // permet de suivre maintenant l'utilisateur

    public DetailsUtilisateurFragment(String email, boolean suivre) {
        // Required empty public constructor
        this.email = email;
        this.follow = suivre;
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
        suivre = (Button) view.findViewById(R.id.utilisateur_suivre);

        /*
            On va maintenant demander à la base de remplir les champs ci-dessus
            on va tout d'abord récupérer l'adresse et remplir le champ POST nécessaire
         */
        String adresse = Data.getData().getURLDetailsUser();
        byte[] infos = Data.getData().getPostDetailsUser(email);

        DataManager dataManager = new DataManager(this);
        dataManager.execute(adresse,infos);

        /*
            En cas de clique,
            l'utilisateur actuellement connecté, suivra l'utilisateur ayant le profil
            actuellement visité
         */
        if(follow)
        {
            suivre.setText("Ne plus suivre");
            suivre.setOnClickListener(new SuivreCommentaire(follow));
        }
        else
        {
            suivre.setText("Suivre");
            suivre.setOnClickListener(new SuivreCommentaire(follow));
        }

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
            identite.setText(utilisateur.getString("prenom") + " " + utilisateur.getString("nom"));
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
                String id, Bitmap img,boolean image,String titre, String genre,String note
             */
            for(int i = 0 ; i < lectures.length() ; i++)
            {
                JSONObject livre = new JSONObject(lectures.getString("livre" + i));
                img = null;
                urlImage = livre.getString("cover_url");
                boolean image = false;
                if(urlImage.equals(""))
                {
                        /*
                            On convertie le drawable en Bitmap (image par défault)
                        */
                    img = BitmapFactory.decodeResource(getResources(),
                            R.drawable.whale);
                    image = false;
                }
                else
                {
            /*
                Télécharge une image à partir de la cover_url de l'utilisateur.
             */
                    if(!urlImage.startsWith("http")) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        img = BitmapFactory.decodeFile(urlImage, options);
                    }else {

                        BitmapManager bitmapManager = new BitmapManager(img);
                        try {
                            img = bitmapManager.execute(urlImage).get();
                        } catch (ExecutionException | InterruptedException e) {
                            Toast.makeText(getContext()," Une erreur s'est passé dans le chargement de l'image", Toast.LENGTH_SHORT).show();
                            img = BitmapFactory.decodeResource(getResources(),
                                    R.drawable.whale);
                        }
                    }
                    image = true;
                }
                Resultat_RechercheFragment response = new Resultat_RechercheFragment(
                        livre.getString("id"),
                        img,
                        image,
                        livre.getString("title"),
                        livre.getString("genre"),
                        livre.getString("stars_average"));
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.layout_liste_lecture, response, "");
                ft.commit();
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
                JSONObject comment = new JSONObject(comments.getString("comment" + i));

                /*
                    Structure de CommentaireFragment :
                    String title, String id, Bitmap image, String comment, int evaluation
                 */
                urlImage = comment.getString("cover_url");
                BitmapManager bitmapManager = new BitmapManager(img);
                try {
                    img = bitmapManager.execute(urlImage).get();
                }catch(ExecutionException | InterruptedException e){}
                EvaluationFragment response = new EvaluationFragment(comment.getString("title"),
                        comment.getString("id"),
                        img,
                        comment.getString("comment"),
                        comment.getInt("stars"));
                android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.add(R.id.layout_liste_evaluation, response, "");
                ft.commit();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
Listener appelé lorsque le bouton 'suivre' est enclenché,
cependant cette fonctionnalité n'est pas encore implanté
*/
    class SuivreCommentaire implements View.OnClickListener{

        private boolean suivre;

        public SuivreCommentaire(boolean b)
        {
            suivre = b;
        }

        @Override
        public void onClick(View v) {
            //// TODO: 18/05/16
            SessionManager sessionManager = new SessionManager(getContext()); // pour verifier si un user est connecté

            if(!sessionManager.isConnected()) {
                String adresse;
                if(follow){
                    adresse = Data.getData().getURLUnFollowUser();
                }
                else{
                    adresse = Data.getData().getURLFollowUser();
                }
                byte[] infos = Data.getData().getPostFollowUser(email, sessionManager.getSessionEmail());

                /*
                    ON appel mainenant DataManager, pour demander le suivi de cet utilisateur
                    et ensuite la fonction ReceiveData
                 */
                DataManager dataManager = new DataManager(new RefreshEvaluation(DetailsUtilisateurFragment.this));
                if(follow)
                {
                    Toast.makeText(getContext()," Vous ne suivez plus maintenant cet utilisateur.",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), " Vous suivez maintenant cet utilisateur.", Toast.LENGTH_SHORT).show();
                }
                follow = !follow;
                dataManager.execute(adresse,infos);

            }else{
                Toast.makeText(getContext(), " Vous devez être connecté pour suivre cet utilisateur !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
    Classe utilisé pour mettre a jour la classe DetailsUtilisateurFragment
 */
    class RefreshEvaluation implements DataReceiver {

        private DetailsUtilisateurFragment classeMere;

        public RefreshEvaluation(DetailsUtilisateurFragment duf) {
            classeMere = duf;
        }

        /*
            Fonction appelée lorsque le serveur a fini d'envoyer les informations au client
         */
        @Override
        public void receiveData(String resultat) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(DetailsUtilisateurFragment.this).attach(DetailsUtilisateurFragment.this).commit();
        }
    }

}
