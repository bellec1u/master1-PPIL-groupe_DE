package com.ppil.groupede.callmeishmael.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.DataReceiver;
import com.ppil.groupede.callmeishmael.data.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pima on 16/05/16.
 */
public class DetailsLivreFragment extends Fragment implements DataReceiver{
    /*
        Classe permettant d'afficher les détails d'un livre, ces détails sont :
        -le titre
        -le résumé
        -la date de parution
        -l'auteur
        -la note moyenne
        -le genre
        -la langue
        -l'image de couverture du livre
     */

    private String id; // id du livre à atteindre
    private Bitmap image ; // evite de recharger l'image inutilement

    private ImageView imageLivre; // objet contenant l'image de couverture
    private TextView dateParution; // objet contenant la date de parution
    private TextView resume; // objet contenant le résumé du livre
    private TextView note; // objet contenant la note du livre
    private TextView genre; // objet contenant le genre du livre
    private TextView auteur; // objet contenant le nom de l'auteur
    private TextView titre; // objet contenant le titre du livre
    private TextView langue; // objet contenant la langue de l'ouvrage

    private Button commenter; // bouton pour demander à effectuer un commentaire
    private Button ajouter; // permet à un utilisateur d'ajouter ce livre à sa liste de lecture
    private Boolean dejaCommenter; // indique si l'utilisateur a déjà commenter ou non
    private LinearLayout layoutCommentaire; // layout contenant les commentaires

    /*
        ImageView des étoiles correspondant à la note
     */
    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;

    /*
        Pour gérer les fragments commentaires
     */
    private android.support.v4.app.FragmentTransaction ft;

    /*
        Constructeur de DetailsLivreFragment,
        les parametres sont la Bitmap pour avoir l'image d'une manière plus rapide,
        l'id du livre afin de rechercher les infos du livre dans la base
     */
    public DetailsLivreFragment(String id, Bitmap img) {
        // Required empty public constructor
        this.id = id;
        image = img;
    }

    /*
        Retourne l'id du livre
     */
    public String getIdLivre()
    {
        return id;
    }

    /*
        Retourne l'image Bitmap du Livre
     */
    public Bitmap getImage()
    {
        return image;
    }

    /*
        Fonction permettant de créer et de retourner le Fragment avec les divers
        élements contenus dans ce dernier...
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*
            On récupère les élements à partir de la view
            et on les affecte à l'attribut de classe correspondant.
         */
        View view = inflater.inflate(R.layout.fragment_details_livre, container, false);
        dateParution = (TextView) view.findViewById(R.id.rep_parution_date);
        resume = (TextView) view.findViewById(R.id.rep_resume);
        note = (TextView) view.findViewById(R.id.rep_note);
        genre = (TextView) view.findViewById(R.id.rep_genre);
        auteur = (TextView) view.findViewById(R.id.rep_author);
        titre = (TextView) view.findViewById(R.id.rep_title);
        langue = (TextView) view.findViewById(R.id.rep_language);
        imageLivre = (ImageView)view.findViewById(R.id.cover_image);
        star1 = (ImageView)view.findViewById(R.id.star1);
        star2 = (ImageView)view.findViewById(R.id.star2);
        star3 = (ImageView)view.findViewById(R.id.star3);
        star4 = (ImageView)view.findViewById(R.id.star4);
        star5 = (ImageView)view.findViewById(R.id.star5);
        commenter = (Button) view.findViewById(R.id.commenter);
        ajouter = (Button) view.findViewById(R.id.ajouterListe);
        layoutCommentaire = (LinearLayout) view.findViewById(R.id.layout_commentaires);
        dejaCommenter = false;
        ft = getFragmentManager().beginTransaction();

        //On affecte à l'imageView notre image Bitmap
        imageLivre.setImageBitmap(image);

        /*
            On demande au serveur les informations du livre d'id 'id'
         */
        String adresse = Data.getData().getDetails(id); // adresse du serveur

        /*
            On instancie et execute DataManager avec adresse comme destination
         */
        DataManager dataManager = new DataManager(this);
        dataManager.execute(adresse);


        /*
            Mise en place du listener pour commenter un livre,
            on verifiera que l'utilisateur est bien connecté
         */
        commenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                    On demande à sessionManager si un utilisateur est log ou non
                 */
                SessionManager sessionManager = new SessionManager(getContext());
                if(sessionManager.isConnected())
                {
                    Toast.makeText(getContext(),"Vous devez être connecté pour commenter un livre",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    /*
                        L'utilisateur est connecté, on peut donc le rediriger vers la page de redaction d'un commentaire
                     */
                    if(dejaCommenter)
                    {
                        Toast.makeText(getContext(),"Vous avez déjà commenter!\nVeuillez modifier votre précédent commentaire.",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        setCommenter();
                    }
                }
            }
        });
        return view; // et on retourne la vue complétée de nos informations
    }

    /*
    Fonction permettant de traiter les résultats recu par une requete http (GET) du serveur,
    dans ce Fragment, on récupera ici les informations comme le titre du livre, l'auteur etc...
    */
    @Override
    public void receiveData(String resultat) {
        /*
            On instancie un JSONObject afin de trouver
            les informations plus facilement.
         */
        try {
            JSONObject object = new JSONObject(resultat); // instanciation ici...
            /*
                On remplie les champs ci-dessous en recuperant la premiere partie du tableau
                info
             */
            JSONObject o = new JSONObject(object.getString("info"));
            dateParution.setText("Date de parution : "+o.getString("publication_date"));
            resume.setText(o.getString("resume"));
            note.setText(o.getString("stars_average") + " / 5");
            genre.setText("Genre : "+ o.getString("genre"));
            auteur.setText(" Auteur : "+o.getString("author"));
            titre.setText(o.getString("title"));
            langue.setText("Langue : " + o.getString("language"));
            /*
                On affiche les étoiles selon la note trouvée
             */
            fillStars(o.getString("stars_average"));
            getActivity().setTitle(o.getString("title"));

            /*
                On charge maintenant les commentaires du livre
             */
            int indice = object.getInt("counter");
            System.out.println(indice);
            for(int i = 0 ; i < indice ; i++)
            {
                ft = getFragmentManager().beginTransaction();
                o = new JSONObject(object.getString("com"+i));
                String auteur = o.getString("first_name") + " " +  o.getString("last_name");
                float note = o.getInt("stars");
                String resume = o.getString("comment");
                int follow = o.getInt("following_allowed");
                String email = o.getString("email");
                int idCom = o.getInt("id");
                /*
                    On verifie si cet utilisateur est celui actuellement connecté
                    On utilise sessionManager pour cela
                 */
                SessionManager sessionManager = new SessionManager(getContext());
                boolean mine = (email.equals(sessionManager.getSessionEmail()));
                if(mine)
                {
                    dejaCommenter = true; // l'utilisateur ne pourra donc plus refaire de NOUVEAU commentaire
                }
                /*
                    On instancie le nouveau Commentaire et on l'ajoute au layout
                 */
                CommentaireFragment com = new CommentaireFragment(auteur,note,resume,follow,mine, idCom, this);
                ft.add(R.id.layout_commentaires, com, "");
                ft.commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
        Convertie la note en étoile
        avec des étoiles pleines ( note 1..2...3 etc)
        et avec des étoiles mi-pleines (note 1,5 ... 2.5 etc...) par exemple
     */
    public void fillStars(String note)
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
        Permet de rediriger l'utilisateur vers le fragment permettant la redaction d'un commentaire/note
     */
    public void setCommenter()
    {
        //// TODO: 17/05/16
        EcrireCommentaireFragment fragment = new EcrireCommentaireFragment(id,image); // id du livre
        getActivity().setTitle("Ecrire un commentaire");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
