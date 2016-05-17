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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Spine;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;


/**
 * A simple {@link Fragment} subclass.
 */
public class LectureLivreFragment extends Fragment {

    public LectureLivreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lecture_livre, container, false);

        WebView webView = (WebView) view.findViewById(R.id.webViewLectureLivre);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);



        /*
        AssetManager assetManager = getActivity().getAssets();
        try {
            // find InputStream for book
            InputStream epubInputStream = assetManager
                    .open("book/pg22595-images.epub");

            // Load Book from inputStream
            Book book = (new EpubReader()).readEpub(epubInputStream);

            // Log the book's authors
            //Log.i("epublib", "author(s): " + book.getMetadata().getAuthors());

            // Log the book's title
            //Log.i("epublib", "title: " + book.getTitle());

            // Log the book's coverimage property
            //Bitmap coverImage = BitmapFactory.decodeStream(book.getCoverImage().getInputStream());

            //Log.i("epublib", "Coverimage is " + coverImage.getWidth() + " by "
              //      + coverImage.getHeight() + " pixels");

            // Log the tale of contents
            //logTableOfContents(book.getTableOfContents().getTocReferences(), 0);

        } catch (IOException e) {

            Log.e("epublib", e.getMessage());

        }
*/



        AssetManager assetManager = getActivity().getAssets();
        try {
            // find InputStream for book
            InputStream epubInputStream = assetManager.open("book/pg22595-images.epub");
            // Load Book from inputStream
            Book book = (new EpubReader()).readEpub(epubInputStream);

            String linez = "";
            String line = "";
            Spine spine = book.getSpine();
            //for (SpineReference bookSection : spine.getSpineReferences()) {
            SpineReference bookSection = spine.getSpineReferences().get(0);
            Resource res = bookSection.getResource();

            try {
                InputStream is = res.getInputStream();
                StringBuffer string = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                while ((line = reader.readLine()) != null) {
                    linez = string.append(line + "\n").toString();
                    //System.out.println(line);
                }

                //do something with stream
            } catch (IOException e) {
                e.printStackTrace();
            }

            webView.loadData(linez, "text/html; charset=utf-8", "utf-8");

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

}