package com.seener.usedarts;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;


import androidx.multidex.BuildConfig;
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
        if (BuildConfig.DEBUG) {
            initStrictMode();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initStrictMode() {
        StrictMode.ThreadPolicy.Builder builder = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog();
        StrictMode.VmPolicy.Builder vmBuilder = new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog();
        StrictMode.setThreadPolicy(builder.build());
        StrictMode.setVmPolicy(vmBuilder.build());
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
