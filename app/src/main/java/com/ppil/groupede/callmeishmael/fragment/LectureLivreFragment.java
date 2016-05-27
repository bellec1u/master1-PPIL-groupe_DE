package com.ppil.groupede.callmeishmael.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ppil.groupede.callmeishmael.R;
import com.ppil.groupede.callmeishmael.SingletonBackPressed;
import com.ppil.groupede.callmeishmael.data.Data;
import com.ppil.groupede.callmeishmael.data.DataReceiver;
import com.ppil.groupede.callmeishmael.data.SessionManager;
import com.ppil.groupede.callmeishmael.data.DataManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.domain.TableOfContents;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;


/**
 * A simple {@link Fragment} subclass.
 */
public class LectureLivreFragment extends Fragment implements DataReceiver{


    private static final String TAG = "EpubBookContentActivity";
    private WebView webView;
    private Book book;

    private String idLivre ; // id du livre à lire, epub : 'idLivre.epub'

    int position = 0;
    String line;
    float xChangePage = 0;

    public LectureLivreFragment(String id) {
        // Required empty public constructor
        idLivre = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lecture_livre, container, false);



        webView = (WebView) view.findViewById(R.id.webViewLectureLivre);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptEnabled(true); // on rend actif le javaScript
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        // empeche l'utilisateur de scroll vers le abs
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN :
                        xChangePage = event.getX();
                        break;
                    case MotionEvent.ACTION_UP :
                        //retourne la hauteur de la webView - 5% -> pour ne pas perdre de texte
                        int height = (int)(webView.getMeasuredHeight() * 0.95);
                        if (xChangePage > event.getX() && xChangePage - event.getX() > 200 && havePageAfter(height)) {
                            //passer une page -> x > newX
                            //page suivante
                            float pourcent = calculateProgression(); // je recup position
                            sauvegarder(pourcent); // on sauvegarde
                            webView.scrollBy(0, height);
                        } else if (xChangePage < event.getX() && event.getX() - xChangePage > 200 && havePageBefore(height)) {
                            //retour d'une page -> x < newX
                            //page suivante
                            float pourcent = calculateProgression(); // je recup position
                            sauvegarder(pourcent); // on sauvegarde
                            webView.scrollBy(0, -height);
                        }
                        break;
                }

                return true;
            }
        });

        try {
            String basePath = Data.getData().getPath() + "/" + idLivre;
            //System.out.println(basePath);
            File test = new File(basePath);
            if(test.exists())
            {
                //System.out.println("OK");
            }
            book = (new EpubReader()).readEpub(new FileInputStream(basePath));

            DownloadResource(Data.getData().getPath() + "/");

            String linez = "";
            Spine spine = book.getSpine();
            List<SpineReference> spineList = spine.getSpineReferences();
            int count = spineList.size();

            StringBuilder string = new StringBuilder();
            for (int i = 0; count > i; i++) {

                Resource res = spine.getResource(i);

                try {
                    InputStream is = res.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    try {
                        while ((line = reader.readLine()) != null) {
                            linez = string.append(line + "\n").toString();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            linez = linez.replace("../", "");

            webView.loadDataWithBaseURL("file://" + Environment.getExternalStorageDirectory() + "/book/", linez, "text/html", "utf-8", null);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // ---------- ---------- ---------- ---------- demande de reprise de lecture
/*
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        // set title
        alertDialogBuilder.setTitle("Reprise de lecture");
        // set dialog message
        alertDialogBuilder
                .setMessage("Voulez-vous reprendre la lecture au dernier point de sauvegarde ?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*
                            ON recupere la page sauvegarder grace a Data et DataManager et SessionManager pour le mail
                         */
 /*                       SessionManager sessionManager = new SessionManager(getContext());
                        String email = sessionManager.getSessionEmail();
                        String adresse = Data.getData().getURLPageCourante();
                        byte[] infos = Data.getData().getPostPageCourante(email, idLivre);
                        DataManager dataManager = new DataManager(LectureLivreFragment.this);
                        dataManager.execute(adresse, infos);
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
*/
        // ---------- ---------- ---------- ---------- 

        //change d'état le bouton de retour
        SingletonBackPressed.getInstance().setCanBackView(true);

        return view;
    }

    private void logTableOfContents(List<TOCReference> tocReferences, int depth) {

        if (tocReferences == null) {
            return;
        }

        for (TOCReference tocReference : tocReferences) {
            StringBuilder tocString = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                tocString.append("\t");
            }

            tocString.append(tocReference.getTitle());
            Log.i("epublib", tocString.toString());

            logTableOfContents(tocReference.getChildren(), depth + 1);
        }

    }

    public boolean makeDirectory(String dirName) {
        boolean res;

        String filePath = new String(Environment.getExternalStorageDirectory()+"/"+dirName);

        debug(filePath);
        File file = new File(filePath);
        if (!file.exists()) {
            res = file.mkdirs();
        }else {
            res = false;
        }
        return res;
    }

    public void debug(String msg) {
        //      if (Setting.isDebug()) {
        Log.d("EPub", msg);
        //      }
    }

    /*
        Recopie les donnée du dossier asset au systeme de stockage du device concerné
        fileName est le nom du livre
     */
    public void copyBookToDevice(String fileName) {
        //System.out.println("Copy Book to donwload folder in phone");
        try
        {
            InputStream localInputStream = getActivity().getAssets().open("book/"+fileName);
            String path = Environment.getExternalStorageDirectory() + "/book/"+fileName;
            FileOutputStream localFileOutputStream = new FileOutputStream(path);

            byte[] arrayOfByte = new byte[1024];
            int offset;
            while ((offset = localInputStream.read(arrayOfByte))>0)
            {
                localFileOutputStream.write(arrayOfByte, 0, offset);
            }
            localFileOutputStream.close();
            localInputStream.close();
            Log.d(TAG, fileName+" copied to phone");

        }
        catch (IOException localIOException)
        {
            localIOException.printStackTrace();
            Log.d(TAG, "failed to copy");
            return;
        }
    }


    /*
        Telecharge un livre ainsi que ses medias à partir du dossier asset,
        permet en autre d'afficher le style (css) et les images (cover, etc...)
        de facon à rendre la lecture de la manière la plus fidèle possible.
     */
    private void DownloadResource(String directory) {
        try {
            //System.out.println("URL " + directory);
            Resources rst = book.getResources();
            Collection<Resource> clrst = rst.getAll();
            Iterator<Resource> itr = clrst.iterator();

            while (itr.hasNext()) {
                Resource rs = itr.next();
                //System.out.println(rs.toString());
                if ((rs.getMediaType() == MediatypeService.JPG)
                        || (rs.getMediaType() == MediatypeService.PNG)
                        || (rs.getMediaType() == MediatypeService.GIF)) {

                    Log.d(TAG, rs.getHref());

                    File oppath1 = new File(directory, rs.getHref().replace("OEBPS/", ""));

                    oppath1.getParentFile().mkdirs();
                    oppath1.createNewFile();

                    //System.out.println("Path : " + oppath1.getParentFile().getAbsolutePath());


                    FileOutputStream fos1 = new FileOutputStream(oppath1);
                    fos1.write(rs.getData());
                    fos1.close();

                } else if (rs.getMediaType() == MediatypeService.CSS) {

                    File oppath = new File(directory, rs.getHref());

                    oppath.getParentFile().mkdirs();
                    oppath.createNewFile();

                    FileOutputStream fos = new FileOutputStream(oppath);
                    fos.write(rs.getData());
                    fos.close();

                }

            }


        } catch (Exception e) {

        }
    }

    // Calculate the % of scroll progress in the actual web page content
    private float calculateProgression() {
        float totalHeightWebView = (webView.getScale() * webView.getContentHeight())-webView.getHeight();
        float cursor = webView.getScrollY();
        return (((float)(cursor*100))/((float)(totalHeightWebView)));
    }

    //test si l'utilisateur n'est pas en fin de webview
    private boolean havePageAfter(int heightScrollBar) {
        boolean res = false;

        float totalHeightWebView = (webView.getScale() * webView.getContentHeight())-webView.getHeight();
        float cursor = webView.getScrollY();
        if (cursor + heightScrollBar < totalHeightWebView) {
            res = true;
        }

        return res;
    }

    //test si l'utilisateur n'est pas en debut de webview
    private boolean havePageBefore(int heightScrollBar) {
        boolean res = false;

        float cursor = webView.getScrollY();
        if (cursor - heightScrollBar >= 0) {
            res = true;
        }

        return res;
    }

    /*
        Appel DataManager, pour demander l'ajout dans la base du pourcentage
        du livre lu par l'utilisateur
     */
    public void sauvegarder(float pourcent) {
        //On doit recuperer l'email de l'utilisateur connecté
        SessionManager sessionManager = new SessionManager(getContext());
        if (!sessionManager.isConnected()) {
            String email = sessionManager.getSessionEmail();
            String adresse = Data.getData().getURLMarquePage();
            byte[] infos = Data.getData().getPostMarquePage(email,pourcent, idLivre);
            //vas a un certain pourcentage du livre
            DataManager dataManager = new DataManager(null);
            dataManager.execute(adresse,infos);
        }
    }
    private void goToPart(float percent) {

        //retourne la hauteur de la webView - 5% -> pour ne pas perdre de texte
        int height = (int)(webView.getMeasuredHeight() * 0.95);

        //si c'est pas le bas du livre et on a tjrs pas atteint le bon % on continu de décendre
        while (havePageAfter(height) && calculateProgression() <= percent) {
            webView.scrollBy(0, height);
        }
    }

    @Override
    public void receiveData(String resultat) {
        float values = Float.valueOf(resultat);
        goToPart(values);
    }
}