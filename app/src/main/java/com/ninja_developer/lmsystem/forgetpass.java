package com.ninja_developer.lmsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetpass extends AppCompatActivity {

    private EditText editTextemail;
    private AppCompatButton btnforget;
    private FirebaseAuth auth;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);
        editTextemail=findViewById(R.id.editTextemail);
        btnforget=findViewById(R.id.btnforget);
        auth=FirebaseAuth.getInstance();


        btnforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        email = editTextemail.getText().toString();
        if(email.isEmpty()){
            editTextemail.setError("Required");
        }else{
            forgetPass();
        }

    }

    private void forgetPass() {

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgetpass.this, "Check your Email", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(forgetpass.this,login.class));
                    finish();
                }else{
                    Toast.makeText(forgetpass.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}