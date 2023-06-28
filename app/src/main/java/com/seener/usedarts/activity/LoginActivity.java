package com.seener.usedarts.activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.seener.usedarts.databinding.ActivityLoginBinding;
import com.seener.usedarts.ui.login.LoginViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

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

        // 设置注册按钮点击事件
        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailEditText.getText().toString();
                String password = binding.passwordEditText.getText().toString();
                viewModel.register(email, password);
            }
        });

        // 监听登录结果
        viewModel.getLoginResult().observe(this, success -> {
            if (success) {
                // 登录成功
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    // 获取用户信息，执行相应的操作
                }
            } else {
                // 登录失败
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });

        // 监听注册结果
        viewModel.getRegisterResult().observe(this, success -> {
            if (success) {
                // 注册成功
                Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            } else {
                // 注册失败
                Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
