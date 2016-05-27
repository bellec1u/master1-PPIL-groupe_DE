package com.ppil.groupede.callmeishmael;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Leopold on 27/05/2016.
 */
public class ProgressTask extends AsyncTask<String, Void, Boolean> {

    private ProgressDialog dialog;
    private Activity activity;

    public ProgressTask(Activity a) {
        this.activity = a;
    }

    /** progress dialog to show user that the backup is processing. */

    protected void onPreExecute() {
        // ---------- ---------- ---------- ---------- popup de chargement

        dialog = new ProgressDialog(activity);
        dialog.setTitle("Chargement");
        dialog.setMessage("Chargement du livre ...");
        dialog.show();

        // ---------- ---------- ---------- ----------
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if (success) {
            System.out.println("- - - - - ok");
        } else {
            System.out.println("- - - - - pas ok");
        }
    }

    protected Boolean doInBackground(final String... args) {
        try{
            Thread.sleep(3000);
            return true;
        } catch (Exception e){
            Log.e("tag", "error", e);
            return false;
        }
    }


}
