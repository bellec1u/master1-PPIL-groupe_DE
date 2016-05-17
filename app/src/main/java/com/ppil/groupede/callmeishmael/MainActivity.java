package com.ppil.groupede.callmeishmael;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.ppil.groupede.callmeishmael.data.SessionManager;
import com.ppil.groupede.callmeishmael.fragment.AccueilFragment;
import com.ppil.groupede.callmeishmael.fragment.ConnexionFragment;
import com.ppil.groupede.callmeishmael.fragment.ContactFragment;
import com.ppil.groupede.callmeishmael.fragment.FAQFragment;
import com.ppil.groupede.callmeishmael.fragment.InscriptionFragment;
import com.ppil.groupede.callmeishmael.fragment.RechercheFragment;
import com.ppil.groupede.callmeishmael.fragment.ReglagesFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView = null;
    private Toolbar toolbar = null;
    private boolean isConnected = false;
    private TextView nomPrenom;
    private TextView adresseMail;
    private ImageView imagePerso;
    private CallbackManager callbackManager;

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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
/*        nomPrenom = (TextView) findViewById(R.id.header_main).findViewById(R.id.header_nom_prenom);
        nomPrenom.setText("rerer");
        adresseMail = (TextView) findViewById(R.id.header_mail);
        imagePerso = (ImageView) findViewById(R.id.header_imagePerso);*/
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

        this.setConnection(false);
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
            this.setConnection(false);

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
            this.setTitle("Contact");
            // Set the fragment of view
            ContactFragment fragment = new ContactFragment();
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
            this.isConnected = true;
        } else { //deconnexion
            this.setMenuNoConnected();
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

        // Hide "Deconnection"
        mi = m.getItem(2).getSubMenu().getItem(2);
        mi.setVisible(false);

        return true;
    }


    public void connexionFacebook() {
        /*
            identifiant à utiliser :
            demir.yasar@sfr.fr
            Azerty123
         */
        /*FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginButton sign_in = (LoginButton) getLayoutInflater().inflate(R.layout.fragment_connexion, null).findViewById(R.id.login_button);
        sign_in.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
<<<<<<< HEAD
                System.out.println(loginResult.toString());
                setConnection(true);
                SessionManager sessionManager = new SessionManager(getBaseContext());
                String id = loginResult.getAccessToken().getUserId();
                System.out.println("---"+id);
                sessionManager.createUserSession(id);
                // Set the page's title
                setTitle("Accueil");
                // Set the fragment of view
                AccueilFragment fragment = new AccueilFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();

            }

*/

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void getInfo(LoginResult loginResult){
        String accessToken = loginResult.getAccessToken().getToken();
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.i("LoginActivity", response.toString());
                // Get facebook data from login
                try {
                    Bundle bFacebookData = getFacebookData(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
        request.setParameters(parameters);
        request.executeAsync();
    }

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
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;

    }
}
