package com.example.pharmaeye.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pharmaeye.R;
import com.example.pharmaeye.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySignUpBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_sign_up);

        this.binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.binding.btnCreateAccount.setOnClickListener(this);
        this.mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        if(view != null){
            switch (view.getId()){
                case R.id.btn_create_account:{
                    Log.d(TAG,"onClick: Create Account button Clicked");
                    this.validateData();
                    break;
                }
            }
        }
    }

    private void validateData(){
        Boolean validData = true;
        String email = "";
        String password = "";

        if(this.binding.editEmail.getText().toString().isEmpty()){
            this.binding.editEmail.setError("Email Cannot be Empty");
            validData = false;
        }else{
            email = this.binding.editEmail.getText().toString();
        }
        if(this.binding.editPassword.getText().toString().isEmpty()){
            this.binding.editPassword.setError("Password cannot be Empty");
            validData = false;
        }else {
           if(this.binding.editConfirmPassword.getText().toString().isEmpty()){
               this.binding.editConfirmPassword.setError("Confirm Password cannot be Empty");
               validData = false;
           }else {
               if(!this.binding.editPassword.getText().toString().equals(this.binding.editConfirmPassword.getText().toString())){
                   this.binding.editConfirmPassword.setError("Both Passwords must be Same");
                   validData = false;
               }else {
                   password = this.binding.editPassword.getText().toString();
               }
           }
        }
        if(validData){
            this.createAccount(email,password);
        }else{
            Toast.makeText(this,"Please provide correct inputs",Toast.LENGTH_SHORT).show();
        }
    }
    private void createAccount(String email, String password){
        //allow user to create account
        this.mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if( task.isSuccessful()){
                    //account is created successfully
                    Log.e(TAG,"onComplete:Account created successfully");
                    saveToPrefs(email,password);
                   goToHome();

                }else {
                    Log.e(TAG,"onComplete: Failed to create user account" + task.getException().getLocalizedMessage());
                    Toast.makeText(SignUpActivity.this,"Cannot create account",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void saveToPrefs(String email, String password) {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(getPackageName(), MODE_PRIVATE);
        prefs.edit().putString("USER_EMAIL", email).apply();
        prefs.edit().putString("USER_PASSWORD", password).apply();
    }

    private void goToHome(){
        this.finishAffinity();
        Intent intent = new Intent(this, AddPatientActivity.class);
        startActivity(intent);
    }
}