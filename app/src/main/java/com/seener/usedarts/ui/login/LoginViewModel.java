package com.seener.usedarts.ui.login;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.seener.usedarts.constants.FirebaseContants;
import com.seener.usedarts.database.DatabaseOperations;
import com.seener.usedarts.model.realm.CurrentUser;
import com.seener.usedarts.model.LoginInfos;
import com.seener.usedarts.model.SaveUserInfo;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginInfos> loginResult = new MutableLiveData<>();
    private MutableLiveData<SaveUserInfo> saveUserResult = new MutableLiveData<>();

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

    public void saveUserInfo(CurrentUser currentUser) {

        FirebaseContants.TOKEN = currentUser.getToken();
        // TODO
        DatabaseOperations.getInstance().insertOrUpdateCurrent(
                new CurrentUser(currentUser.getUserId(), currentUser.getEmail(), currentUser.getEmail(), FirebaseContants.TOKEN),
                (success, message) -> saveUserResult.setValue(new SaveUserInfo(success, message)));
    }


    public MutableLiveData<LoginInfos> getLoginResult() {
        return loginResult;
    }

    public MutableLiveData<SaveUserInfo> getSaveUserResult() {
        return saveUserResult;
    }

}
