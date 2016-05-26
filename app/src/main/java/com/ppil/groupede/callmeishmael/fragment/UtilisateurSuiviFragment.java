package com.ppil.groupede.callmeishmael.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.SingletonBackPressed;
import com.ppil.groupede.callmeishmael.data.BitmapManager;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataManager;
import com.ppil.groupede.callmeishmael.data.DataReceiver;
import com.ppil.groupede.callmeishmael.data.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class UtilisateurSuiviFragment extends Fragment implements DataReceiver {
    private android.support.v4.app.FragmentTransaction ft;
    private TextView liste;

    public UtilisateurSuiviFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_utilisateur_suivi, container, false);
        liste = (TextView) view.findViewById(R.id.liste);

        final SessionManager sessionManager = new SessionManager(getContext());
        String adresse = Data.getData().getURLUtilisateurSuivi();
        byte[] infos = Data.getData().getPostUtilisayeurSuivi(sessionManager.getSessionEmail());
        DataManager dataManager = new DataManager(UtilisateurSuiviFragment.this);
        dataManager.execute(adresse, infos);

        //change d'état le bouton de retour
        SingletonBackPressed.getInstance().setCanBackView(false);

        // Inflate the layout for this fragment
        return view;
    }



    public void receiveData(String resultat) {
        int largeur = (int) (100 * getResources().getDisplayMetrics().density + 0.5f);
        int hauteur = (int) (150 * getResources().getDisplayMetrics().density + 0.5f);

        /*
            On instancie un JSONObject afin de parcourir le résultat
            si ce dernier est vide alors s'est que le compte n'existe pas
         */
        try {
            System.out.println(resultat);
            JSONArray array = new JSONArray(resultat) ; // convertie le résultat en JSONArray
            if(array.length() > 0 ){
                liste.setText("");
                liste.setVisibility(View.GONE);

                for(int i = 0 ; i  < array.length() ; i++) {
                    // On recupere le premier élement dans un Object, puis en JSONObject
                    Object object = array.get(i);
                    JSONObject jsonObject = new JSONObject(object.toString()); // instanciation du JSONObject

                   /* On récupère avant les attributs présent dans le JSONObject */
                    String first_name = jsonObject.getString("first_name");
                    String last_name = jsonObject.getString("last_name");
                    String date = jsonObject.getString("birth_date");
                    String mail = jsonObject.getString("email");


                    /*
                    Utilisation de la classe BitmapManager pour charger dans un Bitmap
                    une image venant d'un URL
                */
                    Bitmap bitmap = null; // parametre d'ImageManager
                    BitmapManager bitmapManager = new BitmapManager(bitmap); // instanciation ici...
                /*
                    On execute bitmapManager, donc requete,
                    le get() fait en sorte que l'UIThread attend le resultat.
                 */
                    bitmap = bitmapManager.execute(jsonObject.getString("profile_image")).get();
                    boolean image = false;
                    if (bitmap != null) {
                        image = true;
                /*
                    On redimensionne l'image (si besoin est)
                 */
                        bitmap = Bitmap.createScaledBitmap(bitmap, largeur, hauteur, true);
                    }


                    ResultatUtilisateurSuiviFragment com = new ResultatUtilisateurSuiviFragment(bitmap, image, first_name, last_name, date,mail);
                    ft = getFragmentManager().beginTransaction();
                    ft.add(R.id.layout_suivi, com, "");
                    ft.commit();
                }
            }else{
                liste.setText("Votre liste d'abonnement est vide");
                liste.setVisibility(View.VISIBLE);
            }
                } catch (JSONException e) {
            Toast.makeText(getContext(), "Erreur  !", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }


}
