package com.seener.usedarts.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.seener.usedarts.databinding.ActivityWelcomeBinding;
import com.seener.usedarts.ui.welcome.WelcomeViewModel;

import io.reactivex.rxjava3.core.Observable;

public class WelcomeActivity extends AppCompatActivity {
    private ActivityWelcomeBinding binding;
    private WelcomeViewModel viewModel;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(WelcomeViewModel.class);

        checkGooglePlayServicesAvailable();
        askNotificationPermission();
        getNotificationToken();

        initViewModel();

    }

    private void initViewModel() {
        viewModel.getLogStatus().observe(this, status -> {
            switch (status) {
                case SIGN_OUT:
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    break;
                case SIGN_IN:
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    break;
                case FIRST_START:
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    break;
                default:
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    break;
            }
        });
        viewModel.checkLoginStatus();
    }

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
                ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        // FCM SDK (and your app) can post notifications.
                    } else {
                        Toast.makeText(this, "your app will not show notifications.", Toast.LENGTH_LONG);
                    }
                });
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
