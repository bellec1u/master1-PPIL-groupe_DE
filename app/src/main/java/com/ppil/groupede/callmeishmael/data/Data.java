package com.ppil.groupede.callmeishmael.data;

/**
 * Created by Pima on 16/05/16.
 */
public class Data {
    /*
        Classe dans laquelle sera contenu les différents url vers la base de donnée
        elle contiendra aussi les informationd telles que l'ip et le port du serveur.
     */

    public String ipMachine; // ip du serveur

    public String port; // port du serveur

    public String adresse; // adresse = ip : port

    public static Data data = new Data(); // instance de Data

    private Data()
    {
<<<<<<< HEAD
        ipMachine = "http://192.168.212.157" ;
        port = "8888" ;
=======
        ipMachine = "http://192.168.43.136" ;
        port = "" ;
>>>>>>> 317f040290081247dd3590d4c1419396255bb6b7
        adresse = ipMachine + ":" + port;
    }

    /*
        Retourne l'adresse du serveur
     */
    public String getAdresse() {
        return adresse;
    }

    /*
        Retourne l'URL nécessaire pour effectuer une inscription standard
     */
    public String getInscription(String nom, String prenom, String email, String password, String profile_image, String genre, String date) {
        return (adresse + "/requetes/register.php?" +
                "email=" + email +
                "&password=" + password +
                "&prenom=" + prenom +
                "&nom=" + nom +
                "&sexe=" + genre +
                "&date=" + date +
                "&cover_url=" + profile_image);

    }

    /*
    Retourne l'URL nécessaire pour effectuer une modification des informations personnelles de l'utilisateur
 */
    public String getModification(String nom, String prenom, String email, String password, String profile_image, String genre, String date, String oldMail, String oldPwd) {
        return (adresse + "/requetes/modification.php?" +
                "email=" + email +
                "&password=" + password +
                "&prenom=" + prenom +
                "&nom=" + nom +
                "&sexe=" + genre +
                "&date=" + date +
                "&cover_url=" + profile_image +
                "&oldemail=" + oldMail +
                "&oldpwd=" + oldPwd);

    }

    /*
        Retourne l'URL nécessaire pour vérifier une connexion
     */
    public String getConnexion(String email, String password) {
        return (adresse + "/requetes/login.php?" +
                "email=" + email +
                "&password=" + password);
    }

    /*
        Retourne l'URL nécessaire pour obtenir le top 10 des meilleurs livres
     */
    public String getTop10() {
        return (adresse + "/requetes/10Best.php");
    }

    /*
        Retourne l'URL nécessaire pour obtenir les informations d'un livre d'identifiant 'id'
     */
    public String getDetails(String id) {
        return (adresse + "/requetes/bookDetails.php?id=" + id);
    }

    /*
        Retourne l'URL nécessaire pour supprimer un utilisateur avec un email comme parametre
     */
    public String getDeleteUser(String email) {
        return (adresse + "/requetes/delete.php?email=" + email);
    }

    /*
        Retourne l'URL nécessaire pour demander à la base d'insérer un commentaire
     */
    public String getURLCommentaire(String idLivre, String emailUtilisateur, String commentaire, int note) {
        return (adresse + "/requetes/commenter.php?id=" + idLivre +
                "&email=" + emailUtilisateur +
                "&com=" + commentaire +
                "&note=" + note);
    }

    /*
        Retourne l'URL nécessaire pour modifier un commentaire dans la base
     */
    public String getURLModifierCommentaire(String idLivre, String emailUtilisateur, String commentaire, int note) {
        return (adresse + "/requetes/editer.php?id=" + idLivre +
                "&email=" + emailUtilisateur +
                "&com=" + commentaire +
                "&note=" + note);
    }

    /*
        Retourne l'URL nécessaire pour demander la suppression d'un commentaire
     */
    public String getURLSupprimerCommentaire(String emailUtilisateur, int idCommentaire) {
        return (adresse + "/requetes/decommenter.php?email=" + emailUtilisateur +
                "&id=" + idCommentaire);
    }

    /*
        Retourne l'URL pour demander la liste de lecture d'un utilisateur
     */
    public String getURLecture() {
        return (adresse + "/requetes/lecture.php");
    }

    /*
        Retourne l'URL pour demander la suppression d'un livre 'id' dans la liste de lecture de l'utilisateur
        ayant comme email 'email'
     */
    public String getURLSupprimerLivre(String id, String email) {
        return (adresse + "/requetes/supprimerLivre.php?id=" + id +
                "&email=" + email);
    }

    /*
        Retourne l'URL pour demander l'ajout dans la liste de lecture de l'utilisateur ayant comme email 'email'
        le livre d'identifiant idLivre
     */
    public String getURLAJouterLivre(String email, String idLivre) {
        return (adresse + "/requetes/ajouterLivre.php?id=" + idLivre +
                "&email=" + email);
    }

    /*
        Retourne l'instance de Data, car singleton
     */
    public static Data getData() {
        return data;
    }


    /*
           Retourne l'URL nécessaire pour la recherche
        */
    public String getRecherhce(String s) {
        return (adresse + "/requetes/recherche.php?" +
                "mot=" + s);
    }

}
