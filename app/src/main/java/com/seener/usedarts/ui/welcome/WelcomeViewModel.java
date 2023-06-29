package com.seener.usedarts.ui.welcome;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WelcomeViewModel extends ViewModel {

    private final MutableLiveData<Drawable> welcomeImageView;
    private final MutableLiveData<Boolean> isSignedIn;

    public WelcomeViewModel() {
        welcomeImageView = new MutableLiveData<>();
        isSignedIn = new MutableLiveData<>();
    }

    public void setWelcomeImageView() {

    }

    public MutableLiveData<Drawable> getWelcomeImageView() {
        return welcomeImageView;
    }

    public void checkSignedIn() {

    }

    public MutableLiveData<Boolean> isSignedIn() {
        return isSignedIn;
    }

}
