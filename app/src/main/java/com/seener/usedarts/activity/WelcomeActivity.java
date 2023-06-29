package com.seener.usedarts.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.seener.usedarts.databinding.ActivityWelcomeBinding;
import com.seener.usedarts.ui.welcome.WelcomeViewModel;

public class WelcomeActivity extends AppCompatActivity {
    private ActivityWelcomeBinding binding;
    private WelcomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(WelcomeViewModel.class);
    }
}
