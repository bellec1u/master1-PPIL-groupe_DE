package com.ppil.groupede.callmeishmael.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by Pima on 12/05/16.
 */
public class BitmapManager implements Runnable {

    private String url;
    private Bitmap res;

    private static final int IO_BUFFER_SIZE = 4 * 1024; // taille du buffer pour dl les images


    public BitmapManager(String coverlink)
    {
        super();
        url = coverlink;
        res = null;
    }

    public void setUrl(String link)
    {
        url = link;
    }
    @Override
    public void run() {
        Bitmap bitmap = null;
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

        res = bitmap;
    }


    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[IO_BUFFER_SIZE];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
    }

    public Bitmap getImage()
    {
        return res;
    }
}
