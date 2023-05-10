package com.sortscript.digitalkasaan.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sortscript.digitalkasaan.databinding.ActivityLoginBinding;
import com.sortscript.digitalkasaan.databinding.ActivityRegisterBinding;
import com.sortscript.digitalkasaan.datamodel.model.fan.UserModel;
import com.sortscript.digitalkasaan.other.utils.NetworkAvailability;
import com.sortscript.digitalkasaan.other.utils.Validation;
import com.sortscript.digitalkasaan.ui.base.BaseActivity;

public class RegisterActivity extends BaseActivity {

    private ActivityRegisterBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        binding.tvHaveAnAcc.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        binding.btnRegister.setOnClickListener(v -> {
            validation();
        });
    }

    public void validation() {
        String userName = binding.etUserName.getText().toString();
        String userEmail = binding.etEmail.getText().toString();
        String userPassword = binding.etPassword.getText().toString();
        if (!NetworkAvailability.checkInternetConnection(RegisterActivity.this)) {
            Toast.makeText(RegisterActivity.this, "No Internet Connection Available", Toast.LENGTH_SHORT).show();
        } else if (userName.equals("")) {
            binding.tfUserName.setError("Enter User Name");
        } else if (!Validation.isEmailValid(userEmail)) {
            binding.tfEmail.setError("Enter Valid Email");
            binding.tfUserName.setError("");
        } else if (userPassword.equals("") || userPassword.length() < 6) {
            binding.tfPassword.setError("Password must b 6 character");
            binding.tfEmail.setError("");
        } else {
            registerNow(userName, userEmail, userPassword);
        }
    }

    private void registerNow(String userName, String userEmail, String userPassword) {
        showLoading();
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String UID = mAuth.getUid();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Digital Kasaan").child("Users");
                            UserModel userModel = new UserModel(userName, userEmail);
                            databaseReference.child(UID).setValue(userModel)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                            showToast("Signup Successfully");
                                            hideLoading();
                                        }
                                    });
                        } else if (task.isCanceled()) {
                            showToast("Task Cancel");
                            hideLoading();
                        } else {
                            showToast("Authentication failed");
                            hideLoading();
                        }
                    }
                });
    }

}