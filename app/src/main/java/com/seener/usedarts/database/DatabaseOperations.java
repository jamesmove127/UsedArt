package com.seener.usedarts.database;

import android.util.Log;

import com.seener.usedarts.model.realm.CurrentUser;

import io.realm.Realm;
import io.realm.RealmResults;

public class DatabaseOperations {

    private static volatile DatabaseOperations INSTANCE;

    private Realm realm;

    private DatabaseOperations() {
    }

    public static DatabaseOperations getInstance() {
        if (INSTANCE == null) {
            synchronized (DatabaseOperations.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DatabaseOperations();
                }
            }
        }
        return INSTANCE;
    }

    public synchronized void insertOrUpdateCurrent(CurrentUser currentUser, TransactionCallback callback) {

        realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(currentUser);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                callback.onSuccess(true, "");
                realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d("REALM", "" + error.getMessage() + " error:" + error);
                callback.onSuccess(false, error.getMessage());
                realm.close();
            }
        });

    }

    public synchronized CurrentUser getCurrentUser() {
        realm = Realm.getDefaultInstance();
        try {
            RealmResults<CurrentUser> persons = realm.where(CurrentUser.class).findAll();
            if (persons == null || persons.isEmpty()) {
                return null;
            }
            return persons.get(0);
        } catch (Exception e) {
            Log.d("REALM", "getCurrentUser:" + e.getMessage() + " " + e);
        }
        return null;
    }

    public synchronized void close() {
        if (realm != null) {
            realm.close();
        }
    }

    public interface TransactionCallback {
        void onSuccess(boolean success, String message);
    }
}
