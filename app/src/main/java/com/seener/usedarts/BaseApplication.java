package com.seener.usedarts;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
        initDatabase();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initFirebase() {
        FirebaseAnalytics.getInstance(this);
        FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);
    }

    private void initDatabase() {
        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("usedart.realm")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
