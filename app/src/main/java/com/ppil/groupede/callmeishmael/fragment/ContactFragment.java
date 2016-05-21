package com.ppil.groupede.callmeishmael.fragment;


import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.ppil.groupede.callmeishmael.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    private WebView lire;
    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);


        lire = (WebView) view.findViewById(R.id.idLire);
        EpubReader epubReader = new EpubReader();
        try {
            // find InputStream for book

            AssetManager assetManager = this.getActivity().getAssets();
            InputStream epubInputStream = assetManager.open("book/pg22595-images.epub");
            Book book = epubReader.readEpub(epubInputStream);
            // Log the book's authors

            Log.i("epublib", "author(s): " + book.getMetadata().getAuthors());


            // Log the book's title

            Log.i("epublib", "title: " + book.getTitle());


            // Log the tale of contents

            logTableOfContents(book.getTableOfContents().getTocReferences(), 0);

        } catch (IOException e) {

            Log.e("epublib", e.getMessage());

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
            try {
                InputStream is = tocReference.getResource().getInputStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder("");
                String line = "";
                while ((line = r.readLine()) != null) {
                    System.out.println(line);
                    sb.append(line);
                }
                // lire.loadData(sb.toString(),"text/html; charset=UTF-8",null);
                AssetManager assetManager = this.getActivity().getAssets();
                lire.loadDataWithBaseURL("book/pg22595-images.epub/", sb.toString(), "text/html", "utf-8", null);
            } catch (IOException e) {

            }
        }
    }


}
