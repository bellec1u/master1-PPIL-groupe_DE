package com.ppil.groupede.callmeishmael.fragment;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.ppil.groupede.callmeishmael.R;

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
public class LectureLivreFragment extends Fragment {


    private static final String TAG = "EpubBookContentActivity";
    private WebView webView;
    private Book book;

    private String idLivre ; // id du livre à lire, epub : 'idLivre.epub'

    int position = 0;
    String line;
    int i = 0;

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

        AssetManager assetManager = getActivity().getAssets();
        String[] files;

        try {

            files = assetManager.list("book");
            List<String> list = Arrays.asList(files);

            if (!this.makeDirectory("book")) {
                debug("faild to make books directory");
            }

            System.out.println("----------------------------------- " + list.get(position));

            copyBookToDevice(list.get(position));

            String basePath = Environment.getExternalStorageDirectory() + "/book/";

            InputStream epubInputStream = assetManager.open("book/" + list.get(position));

            book = (new EpubReader()).readEpub(epubInputStream);

            DownloadResource(basePath);

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

//            File file = new File(Environment.getExternalStorageDirectory(),"test.html");
//            file.createNewFile();
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            fileOutputStream.write(linez.getBytes());
//            fileOutputStream.close();


            webView.loadDataWithBaseURL("file://" + Environment.getExternalStorageDirectory() + "/book/", linez, "text/html", "utf-8", null);

        } catch (IOException e) {
            e.printStackTrace();
        }

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
        System.out.println("Copy Book to donwload folder in phone");
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

            Resources rst = book.getResources();
            Collection<Resource> clrst = rst.getAll();
            Iterator<Resource> itr = clrst.iterator();

            while (itr.hasNext()) {
                Resource rs = itr.next();

                if ((rs.getMediaType() == MediatypeService.JPG)
                        || (rs.getMediaType() == MediatypeService.PNG)
                        || (rs.getMediaType() == MediatypeService.GIF)) {

                    Log.d(TAG, rs.getHref());

                    File oppath1 = new File(directory, rs.getHref().replace("OEBPS/", ""));

                    oppath1.getParentFile().mkdirs();
                    oppath1.createNewFile();

                    System.out.println("Path : " + oppath1.getParentFile().getAbsolutePath());


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
}