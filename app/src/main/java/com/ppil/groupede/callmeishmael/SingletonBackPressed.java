package com.ppil.groupede.callmeishmael;

/**
 * Created by Leopold on 26/05/2016.
 */
public class SingletonBackPressed {

    private static SingletonBackPressed mInstance = null;

    private boolean canBackView;

    public static SingletonBackPressed getInstance() {
        if (mInstance == null) {
            mInstance = new SingletonBackPressed();
        }
        return mInstance;
    }

    public boolean getCanBackView() {
        return this.canBackView;
    }

    public void setCanBackView(boolean value) {
        this.canBackView = value;
    }

}
