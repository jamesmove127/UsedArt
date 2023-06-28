package com.seener.usedarts.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.messaging.FirebaseMessaging;
import com.seener.usedarts.constants.FirebaseContants;
import com.seener.usedarts.databinding.ActivityLoginBinding;
import com.seener.usedarts.ui.login.LoginViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.seener.usedarts.ui.login.SignUpDialog;

public class LoginActivity extends AppCompatActivity implements SignUpDialog.SignUpDialogListener {

    private ActivityLoginBinding binding;

    private LoginViewModel viewModel;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.registerButton.setEnabled(true);
        askNotificationPermission();
        checkGooglePlayServicesAvailable();
        getNotificationToken();
        initUi();
    }

    private void initUi() {
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailEditText.getText().toString();
                String password = binding.passwordEditText.getText().toString();
                viewModel.login(email, password);
            }
        });

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterDialog();
            }
        });

        binding.emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkButtonEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkButtonEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        viewModel.getLoginResult().observe(this, loginInfos -> {
            if (loginInfos.isSuccess()) {

                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser != null) {
                    FirebaseContants.EMAIL = currentUser.getEmail();

                    Log.d("FirebaseUser", "getProviderId:" + currentUser.getProviderId());
                    Log.d("FirebaseUser", "getTenantId:" + currentUser.getTenantId());
                    Log.d("FirebaseUser", "getUid:" + currentUser.getUid());
                    Log.d("FirebaseUser", "getEmail:" + currentUser.getEmail());
                    Log.d("FirebaseUser", "getDisplayName:" + currentUser.getDisplayName());
                    if (currentUser.getProviderData() != null) {
                        Log.d("FirebaseUser", "getProviderData size:" + currentUser.getProviderData().size());
                    }

                    currentUser.getIdToken(false).addOnSuccessListener(getTokenResult -> {
                        FirebaseContants.TOKEN = getTokenResult.getToken();
                        // TODO
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    });
                }
            } else {
                String errorMessage = loginInfos.getMessage();
                if (!TextUtils.isEmpty(errorMessage)) {
                    if (errorMessage.contains("mail")) {
                        binding.emailLayout.setError(errorMessage);
                    } else if (errorMessage.contains("password")) {
                        binding.passwordLayout.setError(errorMessage);
                    } else {
                        binding.passwordLayout.setError(errorMessage);
                    }

                }

            }
        });
    }

    private void checkButtonEnable() {
        String email = binding.emailEditText.getText().toString();
        String password = binding.passwordEditText.getText().toString();

        boolean isEnable = !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password);

        binding.loginButton.setEnabled(isEnable);
        binding.passwordLayout.setError(null);
        binding.emailLayout.setError(null);
    }

    private void showRegisterDialog() {
        SignUpDialog registerDialog = new SignUpDialog();
        registerDialog.show(getSupportFragmentManager(), "Sign Up");
    }

    @Override
    public void onSignUpSuccess() {
        binding.passwordEditText.setText("");
        binding.emailEditText.setText("");
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    Toast.makeText(this, "your app will not show notifications.", Toast.LENGTH_LONG);
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    private void checkGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability = new GoogleApiAvailability();
        if (ConnectionResult.SUCCESS == apiAvailability.isGooglePlayServicesAvailable(this)) {

        } else {
            apiAvailability.makeGooglePlayServicesAvailable(this);
        }
    }

    private void getNotificationToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();

                    // Log and toast

                    Log.d("FCM", "token:" + token);
                });
    }
}
