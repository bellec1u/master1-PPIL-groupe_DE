package com.ppil.groupede.callmeishmael.data;

import android.os.Environment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

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
        ipMachine = "http://192.168.43.57" ;
        port = "" ;
>>>>>>> d0f5b1e0672e462bb73a7ec1bab0d4747ef49bf6
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
        Retourne l'URL pour s'inscrire
     */
    public String getURLInscription()
    {
        return ( adresse + "/requetes/register.php" );
    }

    /*
        Prepare les arguments nécessaires pour une requete POST, pour inscription
     */
    public byte[] getPostInscription(String nom, String prenom, String email, String password, String profile_image, String genre, String date)
    {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("email", email); // on associe au champs email = emailUser
        params.put("password", password); // password
        params.put("prenom", prenom);
        params.put("nom", nom);
        params.put("sexe", genre);
        params.put("date", date);
        params.put("cover_url",profile_image);

        /*
            Charge les parametres
        */
        try {
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> para : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(para.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(para.getValue()), "UTF-8"));
        }

        return postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[1]; // unreachable
        }
    }

    /*
    Prepare les arguments nécessaires pour une requete POST, pour modifier le profil
 */
    public byte[] getPostProfile(String nom, String prenom, String email, String password, String profile_image, String genre, String date, String oldMail, String oldPwd)
    {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("email", email); // on associe au champs email = emailUser
        params.put("password", password); // password
        params.put("prenom", prenom);
        params.put("nom", nom);
        params.put("sexe", genre);
        params.put("date", date);
        params.put("cover_url",profile_image);
        params.put("oldemail", oldMail);
        params.put("oldpwd", oldPwd);

        /*
            Charge les parametres
        */
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> para : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(para.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(para.getValue()), "UTF-8"));
            }

            return postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[1]; // unreachable
        }
    }
    /*
    Retourne l'URL nécessaire pour effectuer une modification des informations personnelles de l'utilisateur
    */
    public String getURLModification() {
        return (adresse + "/requetes/modification.php");

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
        URL pour la connexion
     */
    public String getURLConnexion()
    {
        return (adresse + "/requetes/login.php");
    }

    /*
Prepare les arguments nécessaires pour une requete POST, pour modifier la connexion
*/
    public byte[] getPostConnexion(String email, String password) {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("email", email); // on associe au champs email = emailUser
        params.put("password", password); // password

        /*
            Charge les parametres
        */
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> para : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(para.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(para.getValue()), "UTF-8"));
            }

            return postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[1]; // unreachable
        }
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
        URL pour demander les details d'un livre
     */
    public String getURLDetails()
    {
        return (adresse + "/requetes/bookDetails.php");
    }
    /*
Prepare les arguments nécessaires pour une requete POST, pour avoir les details d'un livre
*/
    public byte[] getPostDetails(String id, String email)
    {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("id", id); // on associe au champs id = id
        params.put("email", email);

        /*
            Charge les parametres
        */
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> para : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(para.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(para.getValue()), "UTF-8"));
            }

            return postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[1]; // unreachable
        }
    }

    /*
        Retourne l'URL nécessaire pour supprimer un utilisateur avec un email comme parametre
     */
    public String getURLDeleteUser() {
        return (adresse + "/requetes/delete.php");
    }

    /*
        Prepare les arguments nécessaires pour une requete POST, pour avoir supprimer un utilisateur
     */
    public byte[] getPostDeleteUser(String email)
    {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("email", email); // on associe au champs id = id

        /*
            Charge les parametres
        */
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> para : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(para.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(para.getValue()), "UTF-8"));
            }

            return postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[1]; // unreachable
        }
    }

    /*
        Retourne l'URL nécessaire pour demander à la base d'insérer un commentaire
     */
    public String getURLCommentaire() {
        return (adresse + "/requetes/commenter.php") ;
    }

    /*
    Prepare les arguments nécessaires pour une requete POST, pour avoir commenter un livre
    */
    public byte[] getPostCommenter(String idLivre, String emailUtilisateur, String commentaire, int note)
    {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("id", idLivre); // on associe au champs id = idLivre
        params.put("email", emailUtilisateur);
        params.put("com", commentaire);
        params.put("note", note);

        /*
            Charge les parametres
        */
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> para : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(para.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(para.getValue()), "UTF-8"));
            }

            return postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[1]; // unreachable
        }
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
        Identique a getPostCommenter ...
     */
    public byte[] getPostModifierCommentaire(String idLivre, String emailUtilisateur, String commentaire, int note)
    {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("id", idLivre); // on associe au champs id = idLivre
        params.put("email", emailUtilisateur);
        params.put("com", commentaire);
        params.put("note", note);

        /*
            Charge les parametres
        */
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> para : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(para.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(para.getValue()), "UTF-8"));
            }

            return postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[1]; // unreachable
        }
    }

    /*
        Retourne l'URL nécessaire pour demander la suppression d'un commentaire
     */
    public String getURLSupprimerCommentaire() {
        return (adresse + "/requetes/decommenter.php");
    }

    /*
        Requete POST, pour supprimer un commentaire
     */
    public byte[] getPostSupprimerCommentaire(String emailUtilisateur, int idCommentaire)
    {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("id", idCommentaire); // on associe au champs id = idLivre
        params.put("email", emailUtilisateur);

        /*
            Charge les parametres
        */
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> para : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(para.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(para.getValue()), "UTF-8"));
            }

            return postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[1]; // unreachable
        }
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
        Requete POST pour supprimer un livre de la base
     */
    public byte[] getPostSupprimerLivre(String id, String email)
    {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("id", id); // on associe au champs id = idLivre
        params.put("email", email);

        /*
            Charge les parametres
        */
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> para : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(para.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(para.getValue()), "UTF-8"));
            }

            return postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[1]; // unreachable
        }
    }
    /*
        Retourne l'URL pour demander l'ajout dans la liste de lecture de l'utilisateur ayant comme email 'email'
        le livre d'identifiant idLivre
     */
    public String getURLAJouterLivre() {
        return (adresse + "/requetes/ajouterLivre.php");
    }

    /*
        Requete POST pour ajouter un livre dans la liste de lecture
     */
    public byte[] getPostAjouterLivre(String email, String idLivre) {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("id", idLivre); // on associe au champs id = idLivre
        params.put("email", email);
        params.put("directory", Environment.getExternalStorageDirectory()+"/epub");

        /*
            Charge les parametres
        */
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> para : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(para.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(para.getValue()), "UTF-8"));
            }

            return postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[1]; // unreachable
        }
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
    public String getRecherche(String s) {
        return (adresse + "/requetes/recherche.php?" +
                "mot=" + s);
    }

    /*
        Retourne l'URL permettant d'inscrire ou de connecter un utilisateur via facebook
     */
    public String getFacebook(String idProvided, String email, String prenom, String nom, String genre, String cover, String date){
        return (adresse + "/requetes/facebook.php?id=" + idProvided +
                            "&nom=" + nom +
                            "&prenom=" + prenom +
                            "&genre=" + genre +
                            "&cover=" + cover +
                            "&email=" + email +
                            "&date=" + date);
    }

    /*
        URL de connexion facebook
     */
    public String getURLFacebook()
    {
        return (adresse + "/requetes/facebook.php");
    }

    /*
        Requete POST, pour demander l'ajout d'un compte facebook
     */
    public byte[] getPostFacebook(String idProvided, String email, String prenom, String nom, String genre, String cover, String date)
    {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("id", idProvided); // on associe au champs id = idProvided
        params.put("email", email);
        params.put("prenom", prenom);
        params.put("nom", nom);
        params.put("genre", genre);
        params.put("cover", cover);
        params.put("date", date);


        /*
            Charge les parametres
        */
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> para : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(para.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(para.getValue()), "UTF-8"));
            }

            return postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[1]; // unreachable
        }
    }
    /*
        Retourne l'URL permettant de faire une recherche dans la base de donnée
     */
    public String getURLRecherche() {
        return (adresse + "/requetes/recherche.php");
    }

    /*
        Permet de faire une requete POST, pour rechercher un livre, auteur etc...
     */
    public byte[] getPostRechercher(String auteur, String ordre, String langue, String genre, String triPar, String recherche)
    {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("auteur", auteur);
        params.put("ordre", ordre);
        params.put("langue", langue);
        params.put("genre", genre);
        params.put("tri", triPar);
        params.put("recherche", recherche);


        /*
            Charge les parametres
        */
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> para : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(para.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(para.getValue()), "UTF-8"));
            }

            return postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[1]; // unreachable
        }
    }

    /*
        Retourne l'URL nécessaire pour modifier la portée follow de l'utilisateur
     */
    public String getURLFollow()
    {
        return (adresse + "/requetes/follow.php");
    }

    /*
        On remplie les champs POST pour modifier le follow d'un utilisateur
     */
    public byte[] getPostFollow(String email)
    {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("email", email);


        /*
            Charge les parametres
        */
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> para : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(para.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(para.getValue()), "UTF-8"));
            }

            return postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[1]; // unreachable
        }
    }

    /*
        Retourne l'URL nécessaire pour demander l'URL
     */
    public String getURLFAQ() {
        return (adresse + "/requetes/faq.php");
    }

    /*
        Retourne l'url nécessaire pour suivre un utilisateur
     */
    public String getURLFollowUser() {
        return (adresse + "/requetes/suivre.php");
    }

    /*
        Remplit le tableau de byte[] pour effectuer une requete POST pour suivre un utilisateur
     */
    public byte[] getPostFollowUser(String emailSuivi, String emailSuiveur)
    {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("emailSuivi", emailSuivi);
        params.put("emailSuiveur", emailSuiveur);


        /*
            Charge les parametres
        */
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> para : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(para.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(para.getValue()), "UTF-8"));
            }

            return postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[1]; // unreachable
        }
    }

    /*
        Retourne l'URL nécessaire pour trouver les informations personnelles d'un utilisateur
     */
    public String getURLDetailsUser() {
        return (adresse + "/requetes/infosUtilisateur.php");
    }

    /*
        Retourne le tableau de byte[] nécessaire pour effectuer une requete POST
        pour obtenir les informations de l'utilisateur ayat comme email 'email'
     */
    public byte[] getPostDetailsUser(String email)
    {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("email", email);
        /*
            Charge les parametres
        */
        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String,Object> para : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(para.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(para.getValue()), "UTF-8"));
            }

            return postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[1]; // unreachable
        }
    }
}
