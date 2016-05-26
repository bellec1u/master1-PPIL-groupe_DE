package com.ppil.groupede.callmeishmael;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.ppil.groupede.callmeishmael.data.BitmapManager;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.SessionManager;
import com.ppil.groupede.callmeishmael.fragment.AccueilFragment;
import com.ppil.groupede.callmeishmael.fragment.ConnexionFragment;
import com.ppil.groupede.callmeishmael.fragment.ConditionsFragment;
import com.ppil.groupede.callmeishmael.fragment.FAQFragment;
import com.ppil.groupede.callmeishmael.fragment.ImporterFragment;
import com.ppil.groupede.callmeishmael.fragment.InscriptionFragment;
import com.ppil.groupede.callmeishmael.fragment.MonCompteFragment;
import com.ppil.groupede.callmeishmael.fragment.RechercheFragment;
import com.ppil.groupede.callmeishmael.fragment.ReglagesFragment;
import com.ppil.groupede.callmeishmael.fragment.UtilisateurSuiviFragment;

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
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/*
    Activity principale, permet de switcher d'un fragment à l'autre,
    cette activity genre aussi les modes de connexion : anonyme, et connecté.
    On peut également avoir accès à un menu sur le côté à partir du clic sur l'icone en haut à gauche
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView = null; // attribut pour recuperer le NavigationView
    private Toolbar toolbar = null; // toolbar
    private boolean isConnected = false; // booleen qui indique si l'utilisateur est connecté
    private TextView nomPrenom; // Permet de recupérer le nomPrenom dans notre nav_header_main
    private TextView adresseMail; // Permet de recupérer l'adresse mail dans notre nav_header_main
    private ImageView imagePerso; // Permet de recupérer l'image de l'utilisateur
    private CallbackManager callbackManager; // permet de gérer les callBacks lors de la connexion avec Facebook est établit.

    /*
    Fonction permettant de créer et de retourner le Fragment avec les divers
    élements contenus dans ce dernier...
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connexionFacebook(); // initialise la connexion à Facebook
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_main);
        /*
            On récupère les élements du profil,
            nomPrenom,
            adresseMail,
            imagePerso
         */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        nomPrenom = (TextView) header.findViewById(R.id.header_nom_prenom);
        adresseMail = (TextView) header.findViewById(R.id.header_mail);
        imagePerso = (ImageView) header.findViewById(R.id.header_imagePerso);
        /* --------------------------------------------------------------------- */

        // Set the title initially
        this.setTitle("Accueil");
        // Set the fragment initially
        AccueilFragment fragment = new AccueilFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Lock the screen's orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
            On récupère la session pour voir si un utilisateur etait toujours connecté ou non
         */
        SessionManager sessionManager = new SessionManager(getBaseContext());
        this.setConnection(!sessionManager.isConnected()); // et on affecte la connection a vrai ou faux
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_accueil) {
            // Set the page's title
            this.setTitle("Accueil");
            // Set the fragment of view
            AccueilFragment fragment = new AccueilFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_recherche) {
            // Set the page's title
            this.setTitle("Recherche");
            // Set the fragment of view
            RechercheFragment fragment = new RechercheFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_inscription) {
            // Set the page's title
            this.setTitle("Inscription");
            // Set the fragment of view
            InscriptionFragment fragment = new InscriptionFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_connexion) {
            // Set the page's title
            this.setTitle("Connexion");
            // Set the fragment of view
            ConnexionFragment fragment = new ConnexionFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_deconnexion) {
            // Deconnect the user
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.logOut();
            LoginManager.getInstance().logOut();
            this.setConnection(false);
            this.setTitle("Accueil");
            // Set the fragment of view
            AccueilFragment fragment = new AccueilFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_mon_compte) {
            // Set the page's title
            this.setTitle("Mon compte");
            // Set the fragment of view
            MonCompteFragment fragment = new MonCompteFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_suivi) {

            // Set the page's title
            this.setTitle("Utilisateur suivi");
            // Set the fragment of view
            UtilisateurSuiviFragment fragment = new UtilisateurSuiviFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_importer) {

            // Set the page's title
            this.setTitle("Importer");
            // Set the fragment of view
            ImporterFragment fragment = new ImporterFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_reglages) {
            // Set the page's title
            this.setTitle("Reglages");
            // Set the fragment of view
            ReglagesFragment fragment = new ReglagesFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_faq) {
            // Set the page's title
            this.setTitle("FAQ");
            // Set the fragment of view
            FAQFragment fragment = new FAQFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_contact) {

            // Set the page's title
            this.setTitle("CGU");
            // Set the fragment of view
            ConditionsFragment fragment = new ConditionsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public boolean setConnection(boolean b) {
        if (b == true) { //connection
            this.setMenuConnected();
            /*
                On recupère les informations liées à l'utilisateur
             */
            SessionManager sessionManager = new SessionManager(this);
            String nom;
            String prenom;
            String email;
            String url;
            Bitmap img = null;
            nom = sessionManager.getSessionName();
            prenom = sessionManager.getSessionFirstName();
            email = sessionManager.getSessionEmail();
            url = sessionManager.getSessionURL();
            /*
                On charge l'image à partir de l'URL si l'URL n'est pas vide,
                donc on va utiliser BitmapManager
             */
            if(!url.equals("")) {
                if(url.startsWith("http")) {
                    try {
                        BitmapManager bitmapManager = new BitmapManager(img);
                        img = bitmapManager.execute(url).get();
                        imagePerso.setImageBitmap(img);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeFile(url,bmOptions);
                    bitmap = Bitmap.createScaledBitmap(bitmap,48,48,true);
                    imagePerso.setImageBitmap(bitmap);

                }
            }else{
                        /*
                            On convertie le drawable en Bitmap (image par défault)
                        */
                img = BitmapFactory.decodeResource(getResources(),
                        R.drawable.whale);
                imagePerso.setImageBitmap(img);
            }
            this.setProfileNavigation(nom, prenom, email, img); // on demande la mise à jour du menu de navigation
            this.isConnected = true;
        } else { //deconnexion
            this.setMenuNoConnected();
            this.setProfileDefault(); // on affecte le profil par défaut
            this.isConnected = false;
        }

        return true;
    }

    public boolean setMenuConnected() {
        android.support.design.widget.NavigationView navigationView =
                (android.support.design.widget.NavigationView) findViewById(R.id.nav_view);
        Menu m = navigationView.getMenu();

        // Hide "Inscription" and "Connection"
        MenuItem mi = m.getItem(2).getSubMenu().getItem(0);
        mi.setVisible(false);
        mi = m.getItem(2).getSubMenu().getItem(1);
        mi.setVisible(false);

        // Set visible "Deconnection"
        mi = m.getItem(2).getSubMenu().getItem(2);
        mi.setVisible(true);
        mi = m.getItem(2).getSubMenu().getItem(3);
        mi.setVisible(true);
        mi = m.getItem(2).getSubMenu().getItem(4);
        mi.setVisible(true);

        mi = m.getItem(2).getSubMenu().getItem(5);
        mi.setVisible(true);

        return true;
    }

    public boolean setMenuNoConnected() {
        android.support.design.widget.NavigationView navigationView =
                (android.support.design.widget.NavigationView) findViewById(R.id.nav_view);
        Menu m = navigationView.getMenu();

        // Set visible "Inscription" and "Connection"
        MenuItem mi = m.getItem(2).getSubMenu().getItem(0);
        mi.setVisible(true);
        mi = m.getItem(2).getSubMenu().getItem(1);
        mi.setVisible(true);

        // Hide "Deconnection" and "mon compte" and "utilisateur suivi"
        mi = m.getItem(2).getSubMenu().getItem(2);
        mi.setVisible(false);
        mi = m.getItem(2).getSubMenu().getItem(3);
        mi.setVisible(false);
        mi = m.getItem(2).getSubMenu().getItem(4);
        mi.setVisible(false);
        mi = m.getItem(2).getSubMenu().getItem(5);
        mi.setVisible(false);
        return true;
    }

    /*
        Affecte au profil situé en haut a gauche de l'application les données suivantes,
        cette fonction est appelée lorsque l'utilisateur vient de se connecter, si l'utilisateur se déconnecte
        on appelera plutôt la fonction setProfileDefault
     */
    public void setProfileNavigation(String nom, String prenom, String email, Bitmap imageProfile)
    {
        /*
            On change les valeurs du profil en fonction des attributs liés à l'utilisateur
         */
        nomPrenom.setText(prenom+ " "+nom);
        adresseMail.setText(email);
        imagePerso.setImageBitmap(imageProfile);
    }

    /*
        Affecte au profil les données standards
     */
    public void setProfileDefault()
    {
        String nomPm = "anonyme"; // nomPrenom par default
        String mail = "anonyme@anonyme.fr"; // adresse mail de notre utilisateur anonyme
        nomPrenom.setText(nomPm); // on y affecte la bonne valeure
        adresseMail.setText(mail); // on y affecte l'adresse mail

        /*
            On convertie le drawable en Bitmap
         */
        Bitmap img = BitmapFactory.decodeResource(getResources(),
                R.drawable.whale);
        imagePerso.setImageBitmap(img);

    }

    /*
        Gere la connexion avec l'API Facebook
     */
    public void connexionFacebook() {
        /*
            identifiant à utiliser :
            demir.yasar@sfr.fr
            Azerty123
         */
        //initialisation du sdk
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginButton sign_in = (LoginButton) getLayoutInflater().inflate(R.layout.fragment_connexion, null).findViewById(R.id.login_button);
        sign_in.setReadPermissions(Arrays.asList("public_profile", "email"));
        sign_in.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            //connexion avec succès
            @Override
            public void onSuccess(LoginResult loginResult) {
                getInfo(loginResult);
            }

            // connexion annulé
            @Override
            public void onCancel() {
            }

            //erruer lors de la connexion
            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    //recupération des infos de l'utilisateur
      public void getInfo(LoginResult loginResult){
        String accessToken = loginResult.getAccessToken().getToken();
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.i("LoginActivity", response.toString());
                // Get facebook data from login
                try {
                    Bundle bFacebookData = getFacebookData(object);
                    /*
                        On inscrit l'utilisateur dans la base,
                        on recupere l'url avec data avant
                     */
                    String genre = bFacebookData.getString("gender").charAt(0) + "";
                    System.out.println(genre);
                    String adresse = Data.getData().getFacebook(
                            bFacebookData.getString("idFacebook"),
                                    bFacebookData.getString("email"),
                                    bFacebookData.getString("first_name"),
                                    bFacebookData.getString("last_name"),
                                    genre,
                                    bFacebookData.getString("profile_pic"),
                                    bFacebookData.getString("birthday"));
                    /*
                        On appelle data Manager
                     */
                    System.out.println(adresse);
                    SocialFacebook facebook = new SocialFacebook();
                    String res = null; // on lance la requete ici
                    try {
                        res = facebook.execute(adresse).get();
                        JSONObject json = new JSONObject(res);
                         /*
                Si on ne rentre pas dans l'exception alors on a un résultat,
                on va parcourir ce dernier et créé un sessionManager
             */
                        SessionManager sessionManager = new SessionManager(MainActivity.this);

                        // On commence le parcour du jsonObject
                        sessionManager.createSession(json.getString("nom"),
                                json.getString("prenom"),
                                json.getString("email"),
                                json.getString("password"),
                                json.getString("ddn"),
                                json.getString("image"),
                                json.getString("follow"),
                                json.getString("sexe"));
                        Toast.makeText(MainActivity.this.getBaseContext(), "Bonjour " + json.getString("prenom") + " !", Toast.LENGTH_SHORT).show();
                        MainActivity.this.setTitle("Accueil");
                        // Set the fragment of view
                        setConnection(true);
                        AccueilFragment fragment = new AccueilFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction =
                                getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.commit();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
        request.setParameters(parameters);
        request.executeAsync();
          System.out.println(parameters);
    }

    //recupération des infos de l'utilisateur à partir de facebook
    private Bundle getFacebookData(JSONObject object) throws JSONException {
        Bundle bundle = new Bundle();
        String id = object.getString("id");
        try {
            URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
            Log.i("profile_pic", profile_pic + "");
            bundle.putString("profile_pic", profile_pic.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        bundle.putString("idFacebook", id);
        if (object.has("first_name"))
            bundle.putString("first_name", object.getString("first_name"));
        if (object.has("last_name"))
            bundle.putString("last_name", object.getString("last_name"));
        if (object.has("email"))
            bundle.putString("email", object.getString("email"));
        else
            bundle.putString("email", "");
        if (object.has("gender"))
            bundle.putString("gender", object.getString("gender"));
        if (object.has("birthday"))
            bundle.putString("birthday", object.getString("birthday"));
        else
            bundle.putString("birthday", "");
        if (object.has("location"))
            bundle.putString("location", object.getJSONObject("location").getString("name"));
        return bundle;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    //----------------------------------------------------------------------------
    
    /*
        Gere l'API Google+
     */
    public void connexionGoogle()
    {
        //// TODO: 17/05/16
    }
}

class SocialFacebook extends AsyncTask<String,String,String>
{
    @Override
    protected String doInBackground(String... params) {
        String response = ""; // attribut contenant notre futur résultat
        try {
            /*
                On parcourt tous les paramètres
             */
            for(String url : params) {
                URL serv = new URL(url); // on instancie l'URL à atteindre
                /*
                    On établit la connexion avec le serveur
                 */
                HttpURLConnection urlConnection = (HttpURLConnection) serv.openConnection();
                /*
                    On utilise la méthode GET pour le transfert de données
                 */
                urlConnection.setRequestMethod("GET");
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
            }
        } catch (MalformedURLException e) {
            e.printStackTrace(); // pas atteignable logiquement !
        } catch (IOException e) {
            e.printStackTrace(); // pas atteignable logiquement !
        }
        return response; // résultat contenant la réponse du serveur retourné...
    }
}
