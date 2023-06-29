package com.seener.usedarts.ui.welcome;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.seener.usedarts.constants.LoginStatus;
import com.seener.usedarts.database.DatabaseOperations;
import com.seener.usedarts.model.realm.CurrentUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WelcomeViewModel extends ViewModel {

    private final MutableLiveData<Drawable> welcomeImageView;

    private final MutableLiveData<LoginStatus> loginStatus;

    private FirebaseAuth mAuth;

    public WelcomeViewModel() {
        welcomeImageView = new MutableLiveData<>();
        loginStatus = new MutableLiveData<>();
        mAuth = FirebaseAuth.getInstance();
    }

    public void setWelcomeImageView() {

    }

    public MutableLiveData<Drawable> getWelcomeImageView() {
        return welcomeImageView;
    }


    private Observable checkNotificationToken() {
        return Observable.create(emitter -> {
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            emitter.onError(task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        //TODO upload notification token to server

                        emitter.onNext(token);
                        emitter.onComplete();
                    });
        });
    }


    private Observable getCurrentUsersTokenFromDb() {
        return Observable.create(emitter -> {
            CurrentUser currentUser = DatabaseOperations.getInstance().getCurrentUser();
            if (currentUser != null) {
                String token = currentUser.getToken();
                if (!TextUtils.isEmpty(token)) {
                    emitter.onNext(token);
                } else {
                    emitter.onNext("");
                }
            } else {
                emitter.onNext("");
            }
            DatabaseOperations.getInstance().close();
            emitter.onComplete();
        });

    }

    private Observable getCurrentUsersTokenFromFirebase() {
        return Observable.create(emitter -> {
            mAuth.getCurrentUser().getIdToken(false).addOnSuccessListener(getTokenResult -> {
                String token = getTokenResult.getToken();
                if (TextUtils.isEmpty(token)) {
                    emitter.onError(new Exception("getFirebaseTokenNow is empty"));
                } else {
                    emitter.onNext(token);
                    emitter.onComplete();
                }
            });
        });
    }

    public void checkLoginStatus() {
        Observable.zip(checkNotificationToken(), getCurrentUsersTokenFromDb(), getCurrentUsersTokenFromFirebase(), (notificationToken, dbToken, fbToken) -> {
                    boolean combinedResult = TextUtils.equals(String.valueOf(dbToken), String.valueOf(fbToken));
                    return combinedResult;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(combinedResult -> {

                    if ((boolean) combinedResult) {
                        loginStatus.setValue(LoginStatus.SIGN_IN);
                    } else {
                        loginStatus.setValue(LoginStatus.SIGN_OUT);
                    }
                }, throwable -> {
                    // TODO
                });

    }

    public MutableLiveData<LoginStatus> getLogStatus() {
        return loginStatus;
    }
}
