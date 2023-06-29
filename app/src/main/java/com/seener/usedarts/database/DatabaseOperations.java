package com.seener.usedarts.database;

import android.util.Log;

import com.seener.usedarts.model.realm.CurrentUser;

import io.realm.Realm;
import io.realm.RealmResults;

public class DatabaseOperations {

    private static volatile DatabaseOperations INSTANCE;

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

        Realm realm = Realm.getDefaultInstance();
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
        Realm realm = Realm.getDefaultInstance();
        RealmResults<CurrentUser> persons = realm.where(CurrentUser.class).findAll();
        realm.close();
        return persons.get(0);
    }

    public interface TransactionCallback {
        void onSuccess(boolean success, String message);
    }
}
