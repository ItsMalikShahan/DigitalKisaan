package com.sortscript.digitalkasaan.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sortscript.digitalkasaan.databinding.ActivityLoginBinding;
import com.sortscript.digitalkasaan.databinding.ActivitySplashBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.SessionManager;
import com.sortscript.digitalkasaan.datamodel.model.fan.UserModel;
import com.sortscript.digitalkasaan.other.utils.Validation;
import com.sortscript.digitalkasaan.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    FirebaseAuth mAuth;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(this);
        binding.tvForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        });

        binding.tvDontHaveAcc.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        binding.btnLogin.setOnClickListener(v -> {
            if (binding.etEmail.getText().toString().equals("Admin@gmail.com")) {
                if (binding.etPassword.getText().toString().equals("123456")) {
                    startActivity(new Intent(this , AdminActivity.class));
                    finish();
                }
            } else {
                validation();
            }
        });
    }

    private void validation() {
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();
        if (!Validation.isEmailValid(email)) {
            binding.tfEmailLogin.setError("Please Enter Email");
        } else if (password.length() < 6) {
            binding.tfEmailLogin.setError("");
            binding.tfPassword.setError("Password must be 6 Character");
        } else {
            binding.tfPassword.setError("");
            loginNow(email, password);
        }
    }

    private void loginNow(String userEmail, String userPassword) {
        showLoading();
        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Digital Kasaan").child("Users").child(firebaseUser.getUid());
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                UserModel user = dataSnapshot.getValue(UserModel.class);
                                sessionManager.setLogin(true);
                                sessionManager.setObject(user);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                hideLoading();
                                showToast("Login successful");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                hideLoading();
                            }
                        });
                    } else {
                        hideLoading();
                        showToast("Login failed");
                    }
                });

    }
}