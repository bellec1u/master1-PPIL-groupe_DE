package com.ppil.groupede.callmeishmael.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.DataReceiver;
import com.ppil.groupede.callmeishmael.data.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonCompteFragment extends Fragment implements DataReceiver{

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

        getActivity().setTitle("Profil");


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
        final SessionManager sessionManager = new SessionManager(getContext());

        /*
            On les place dans les bons éléments du Fragment
         */
        identite.setText(sessionManager.getSessionName()+ " " + sessionManager.getSessionFirstName());
        mail.setText(sessionManager.getSessionEmail());
        ddn.setText(sessionManager.getSessionDate());
        String genre = sessionManager.getSessionGenre();
        if(genre.equals("m") || genre.equals("M"))
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
                Toast.makeText(getContext()," Une erreur s'est passé dans le chargement de l'image", Toast.LENGTH_SHORT).show();
                image.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                        R.drawable.whale)); // on affecte l'image ici..
            }
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

        /*
            Listener pour la demande de désinscription de l'utilisateur
         */
        desinscrire.setOnClickListener(new View.OnClickListener() {
            /*
                Création d'une fenetre de dialogue pour demander confirmation à l'utilisateur de son action
             */
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Confirmation de désinscription")
                        .setMessage("Voulez vous vraiment supprimer votre compte ?")
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /*
                                    L'utilisateur confirme donc son choix, on demande
                                    au serveur de supprimer son compte.
                                    On charge l'email de l'utilisateur grace à sessionManager
                                 */
                                SessionManager sessionManager = new SessionManager(getContext());
                                String emailSession = sessionManager.getSessionEmail();
                                String adresse = Data.getData().getURLDeleteUser();
                                byte[] infos = Data.getData().getPostDeleteUser(emailSession);
                                /*
                                    On instancie et execute DataManager
                                 */
                                DataManager dataManager = new DataManager(MonCompteFragment.this);
                                dataManager.execute(adresse, infos);
                            }

                        })
                        .setNegativeButton("Non", null)
                        .show();

            }
        });
        String portee = sessionManager.getSessionFollow();
        System.out.println("portee : " + portee);
        if(portee.equals("1")){
            prive.setText("PROFIL PUBLIC");
        }else{
            prive.setText("PROFIL PRIVE");
        }
        prive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                    On demande a la base de modifier le 'follow' de l'utilisateur
                 */
                String adresse = Data.getData().getURLFollow();
                byte[] infos = Data.getData().getPostFollow(sessionManager.getSessionEmail());
                AccountAsync account = new AccountAsync();
                account.execute(adresse,infos);
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

    /*
Renvoie l'utilisateur vers le fragment Accueil, en vue cette fois ci connecté
*/
    public void setAccueil()
    {
        AccueilFragment fragment = new AccueilFragment();
        SessionManager sessionManager = new SessionManager(getContext());
        sessionManager.logOut();
        getActivity().setTitle("Accueil");
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        ((MainActivity)getActivity()).setConnection(false); // l'utilisateur est connecté
    }
    /*
    Fonction permettant de traiter les résultats recu par une requete http (GET) du serveur,
    dans ce Fragment, on récupera ici si la suppresion s'est bien passé ou non.
    */
    @Override
    public void receiveData(String resultat) {
        /*
            On verifie directement le résultat, soit true soit false
        */
        if(resultat.equals("true"))
        {
            // La desinscription a été faite avec succès
            Toast.makeText(getContext()," Votre désinscription a bien été prise en compte",Toast.LENGTH_SHORT).show();
            setAccueil();
        }
        else {
            // Une erreur s'est produite
            Toast.makeText(getContext(), " Oups ! Une erreur s'est produite...", Toast.LENGTH_SHORT).show();
        }

    }

    /*
    Renvoie l'utilisateur vers son profil
 */
    public void setProfile()
    {
        Fragment frg = this;
        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }

    class AccountAsync extends AsyncTask<Object, String, String>
    {
        @Override
        protected void onPostExecute(String resultat) {
            super.onPostExecute(resultat);
            /*
            Si la modification s'est bien passé alors on convertie le String en JSONObject et on le traite
         */
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
                            json.getString("sexe"));
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

        @Override
        protected String doInBackground(Object... params) {
            String response = ""; // attribut contenant notre futur résultat
            try {
            /*
                On parcourt tous les paramètres
             */
                String url = (String) params[0];
                URL serv = new URL(url); // on instancie l'URL à atteindre
                /*
                    On établit la connexion avec le serveur
                 */
                HttpURLConnection urlConnection = (HttpURLConnection) serv.openConnection();
                /*
                    On utilise la méthode GET pour le transfert de données
                 */
                urlConnection.setRequestMethod("POST");

                if(params.length > 1) {
                    byte[] infos = (byte[]) params[1];
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setRequestProperty("Content-Length", String.valueOf(infos.length));
                    urlConnection.setDoOutput(true);
                    urlConnection.getOutputStream().write(infos);
                }
                /*
                    On récupère le résultat dans in
                 */
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in)); // on le lit ici...
                /*
                    Maintenant on le parcourt, puis on affectera à res le resultat.
                 */
                String line;
                StringBuilder sb = new StringBuilder("");
                line = reader.readLine();
                sb.append(line);
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                response = sb.toString(); // résultat affecté ici !
                urlConnection.disconnect(); // on ferme la connexion
            } catch (MalformedURLException e) {
                e.printStackTrace(); // pas atteignable logiquement !
            } catch (IOException e) {
                e.printStackTrace(); // pas atteignable logiquement !
            }
            return response; // résultat contenant la réponse du serveur retourné...
        }
    }
}
