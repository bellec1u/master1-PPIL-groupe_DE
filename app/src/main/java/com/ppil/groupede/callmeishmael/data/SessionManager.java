package com.ppil.groupede.callmeishmael.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Pima on 11/05/16.
 */
public class SessionManager {

    //Object used to keep user's preference
    SharedPreferences preferences;

    //Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    //Context
    Context __context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpreferences file name
    private static final String PREFER_NAME = "PPIL";

    // Shared Preferences Keys
    private static  final String IS_USER_LOGIN = "LogIn";

    // Email adresse ( public access)
    public static final String KEY_EMAIL = "email";

    // user's id
    public static final String KEY_ID = "id";

    // user's firstname
    public static final String KEY_FIRSTNAME = "firstname";

    // user's lastname
    public static final String KEY_LASTNAME = "lastname";

    // user's password
    public static final String KEY_PASSWORD = "password";

    // user's image URL
    public static final String KEY_IMAGE = "image";

    // user's follower's decision
    public static final String KEY_FOLLOW = "follow";

    // user's birthdate
    public static final String KEY_DATE = "date";


    public SessionManager(Context context)
    {
        this.__context = context;
        preferences = __context.getSharedPreferences(PREFER_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }

    //create a login session
    public void createUserSession(String email, String id)
    {
        //Indicate that a user is log
        editor.putBoolean(IS_USER_LOGIN,true);

        editor.putString(KEY_EMAIL,email);

        editor.putString(KEY_ID,id);

        //commit changes
        editor.commit();
    }

    //remove a login session
    public void logOut()
    {
        //clear editor's attributes
        editor.clear();
        editor.commit();
    }

    /*
        Retourne l'id de session de l'utilisateur connecté
     */
    public String getSessionId()
    {
        return preferences.getString(KEY_EMAIL,"email");
    }

    /*
        Créé une session complète avec toutes les informations de l'utilisateur
     */

    public void createSession(String nom, String prenom, String email, String mdp, String date, String img, String follow)
    {
        //Indicate that a user is log
        editor.putBoolean(IS_USER_LOGIN,true);

        editor.putString(KEY_EMAIL, email);

        editor.putString(KEY_LASTNAME, nom);

        editor.putString(KEY_FIRSTNAME, prenom);

        editor.putString(KEY_EMAIL, email);

        editor.putString(KEY_PASSWORD, mdp);

        editor.putString(KEY_DATE, date);

        editor.putString(KEY_IMAGE, img);

        editor.putString(KEY_FOLLOW, follow);

        //commit changes
        editor.commit();
    }

}
