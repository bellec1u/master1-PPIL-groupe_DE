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

    public SessionManager(Context context)
    {
        this.__context = context;
        preferences = __context.getSharedPreferences(PREFER_NAME,PRIVATE_MODE);
        editor = preferences.edit();
    }

    //create a login session
    public void createUserSession(String email)
    {
        //Indicate that a user is log
        editor.putBoolean(IS_USER_LOGIN,true);

        editor.putString(KEY_EMAIL,email);

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

    public String getSessionId()
    {
        return preferences.getString(KEY_EMAIL,"email");
    }

}
