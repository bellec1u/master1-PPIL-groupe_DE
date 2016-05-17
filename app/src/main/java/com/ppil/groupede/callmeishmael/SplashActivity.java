package com.ppil.groupede.callmeishmael;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
    Classe SplasActivity, affiche uniquement pendant 1 seconde l'image de note baleine, avec le nom de l'application
 */
public class SplashActivity extends AppCompatActivity {

    /*
    Fonction permettant de créer et de retourner le SplashActivity
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*
            Thread anonyme pour effectuer un timer durant 1000 ms = 1s
         */
        Thread timerThread = new Thread(){
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    /*
                        Dès que le timer est terminé, on ouvre l'Activity principale
                        MainActivity
                     */
                    Intent login = new Intent(SplashActivity.this, MainActivity.class); // instanciation ici...
                    startActivity(login); // on commence l'Intent
                    finish(); // fin
                }
            }};
        timerThread.start(); // le Thread est lancé !
    }

}
