package com.byteshaft.laundryadmin;

import android.app.Application;

import com.google.firebase.FirebaseApp;

/**
 * Created by s9iper1 on 2/21/17.
 */

public class AppGlobals extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(getApplicationContext());
    }
}
