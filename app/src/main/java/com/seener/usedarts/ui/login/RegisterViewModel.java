package com.seener.usedarts.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<Boolean> registerResult = new MutableLiveData<>();

    private FirebaseAuth mAuth;

    public RegisterViewModel() {
        mAuth = FirebaseAuth.getInstance();
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

    public LiveData<Boolean> getRegisterResult() {
        return registerResult;
    }
}

