package com.ppil.groupede.callmeishmael.data;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Leopold on 12/05/2016.
 */
public class EPubDownloader {

    public void fonctionBidon() {
        System.out.println("---------------------------------------------------------------------------------------- 1");
        //System.out.println(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS));
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        System.out.println("---------------------------------------------------------------------------------------- 2");
        this.getFilesFromDir(file);
        System.out.println("---------------------------------------------------------------------------------------- 3");


    }

    public void getFilesFromDir(File filesFromSD) {

        File listAllFiles[] = filesFromSD.listFiles();

        if (listAllFiles != null && listAllFiles.length > 0) {
            for (File currentFile : listAllFiles) {
                if (currentFile.isDirectory()) {
                    getFilesFromDir(currentFile);
                } else {
                    if (currentFile.getName().endsWith("")) {
                        // File absolute path
                        Log.e("File path", currentFile.getAbsolutePath());
                        // File Name
                        Log.e("File path", currentFile.getName());

                    }
                }
            }
        }
    }

}
