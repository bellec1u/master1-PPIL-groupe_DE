package com.ppil.groupede.callmeishmael.data;

/**
 * Created by Pima on 16/05/16.
 */
public interface DataReceiver {

     void receiveData(String resultat);
    /* fonction appelée lorsque les requetes client<->serveurs sont terminés

        Elle permet de traiter les résultats d'une facon différentes selon les fonctionnalités de chaque
        activités.
        Certaines classes vont recevoir des booleens, d'autres des JSONArray etc...
     */
}
