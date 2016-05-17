package com.ppil.groupede.callmeishmael.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Pima on 14/05/16.
 */
public class BitmapManager extends AsyncTask<String, String, Bitmap> {

    public Bitmap img; // objet dans lequel sera stocké le Bitmap resultat
    private static final int IO_BUFFER_SIZE = 4 * 1024; // taille du buffer pour dl les images

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    /*
        Affecte à l'attribut de classe img, le Bitmap résultat
     */
    @Override
    protected void onPostExecute(Bitmap s) {
        super.onPostExecute(s);
        img = s;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /*
        Constructeur de BitmapManager, le parametre est un Bitmap
     */
    public BitmapManager(Bitmap image) {
        super();
        img = image;
    }

    /*
        Effectue une requete http, et decode le stream entrant en bitmap
     */
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        for(String url : params)
        {
            InputStream in = null;
            BufferedOutputStream out = null;

            try {
                in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);

                final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
                out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
                copy(in, out);
                out.flush();

                final byte[] data = dataStream.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            } catch (IOException e) {
            } finally {
                closeStream(in);
                closeStream(out);
            }
        }
        return bitmap;
    }

    /*
        Copie les donnée de in vers out
     */
    private void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    /*
        Ferme le stream de transfert de donnée
     */
    private void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
    }
}