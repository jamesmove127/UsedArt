package com.seener.usedarts.ui.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputLayout;
import com.seener.usedarts.R;

import android.app.Dialog;
import android.os.Bundle;


import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

public class SignUpDialog extends AppCompatDialogFragment {

    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputLayout confirmPasswordLayout;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private TextInputEditText confirmPasswordEditText;
    private Button registerButton;

    private RegisterViewModel viewModel;

    private SignUpDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_register, null);

        builder.setView(view)
                .setTitle("Sign Up")
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Cancel button clicked
                });

        emailLayout = view.findViewById(R.id.dialog_emailEditLayout);
        passwordLayout = view.findViewById(R.id.dialog_passwordLayout);
        confirmPasswordLayout = view.findViewById(R.id.dialog_confirmPasswordLayout);
        emailEditText = view.findViewById(R.id.dialog_emailEditText);
        passwordEditText = view.findViewById(R.id.dialog_passwordEditText);
        confirmPasswordEditText = view.findViewById(R.id.dialog_confirmPasswordEditText);
        registerButton = view.findViewById(R.id.dialog_registerButton);

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        registerButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (validateInputs(email, password, confirmPassword)) {
                viewModel.register(email, password);
            }
        });

        viewModel.getRegisterResult().observe(this, success -> {
            if (success) {
                Toast.makeText(requireContext(), "Sign Up successful", Toast.LENGTH_SHORT).show();
                if (listener != null) {
                    listener.onSignUpSuccess();
                }
                dismiss();
            } else {
                Toast.makeText(requireContext(), "Sign Up failed", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }

    private boolean validateInputs(String email, String password, String confirmPassword) {
        boolean isValid = true;

        if (TextUtils.isEmpty(email)) {
            emailLayout.setError("Email is required");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.setError("Invalid email format");
            isValid = false;
        } else {
            emailLayout.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("Password is required");
            isValid = false;
        } else if (password.length() < 8 || !containsLetterAndDigit(password)) {
            passwordLayout.setError("Password must be at least 8 characters long and contain both letters and digits");
            isValid = false;
        } else {
            passwordLayout.setError(null);
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordLayout.setError("Confirm Password is required");
            isValid = false;
        } else {
            confirmPasswordLayout.setError(null);
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordLayout.setError("Passwords do not match");
            isValid = false;
        } else {
            confirmPasswordLayout.setError(null);
        }

        return isValid;
    }

    private boolean containsLetterAndDigit(String str) {
        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : str.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }

        return hasLetter && hasDigit;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (SignUpDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SignUpDialogListener");
        }
    }

    public interface SignUpDialogListener {
        void onSignUpSuccess();
    }
}

