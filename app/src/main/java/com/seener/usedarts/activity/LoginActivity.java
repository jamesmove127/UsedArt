package com.seener.usedarts.activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

        // 监听登录结果
        viewModel.getLoginResult().observe(this, success -> {
            if (success) {
                // 登录成功
                FirebaseUser currentUser = mAuth.getCurrentUser();
//                FirebaseContants.DISPLAY_NAME = currentUser.getDisplayName();
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

    private void checkButtonEnable() {
        String email = binding.emailEditText.getText().toString();
        String password = binding.passwordEditText.getText().toString();

        boolean isEnable = !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password);

        binding.loginButton.setEnabled(isEnable);
    }

    private void showRegisterDialog() {
        SignUpDialog registerDialog = new SignUpDialog();
        registerDialog.show(getSupportFragmentManager(), "Sign Up");
    }

    @Override
    public void onSignUpSuccess() {
        //TODO
    }
}
