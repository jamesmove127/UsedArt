package com.seener.usedarts.ui.login;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.seener.usedarts.model.LoginInfos;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginInfos> loginResult = new MutableLiveData<>();
    private FirebaseAuth mAuth;

    public LoginViewModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    public void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loginResult.setValue(new LoginInfos(true, ""));
                    } else {
                        Exception e = task.getException();
                        if (e != null) {
                            Log.d("LoginViewModel", "" + e.getMessage());
                            loginResult.setValue(new LoginInfos(false, "" + e.getMessage()));
                        }
                    }
                });
    }


    public MutableLiveData<LoginInfos> getLoginResult() {
        return loginResult;
    }

}
