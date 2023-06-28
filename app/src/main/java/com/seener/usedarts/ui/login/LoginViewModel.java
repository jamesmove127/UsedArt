package com.seener.usedarts.ui.login;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<Boolean> loginResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> registerResult = new MutableLiveData<>();
    private FirebaseAuth mAuth;

    public LoginViewModel() {
        // 初始化 FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
    }

    public void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loginResult.setValue(true);
                    } else {
                        loginResult.setValue(false);
                    }
                });
    }

    public void register(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        registerResult.setValue(true);
                    } else {
                        registerResult.setValue(false);
                    }
                });
    }

    public MutableLiveData<Boolean> getLoginResult() {
        return loginResult;
    }

    public MutableLiveData<Boolean> getRegisterResult() {
        return registerResult;
    }
}
