package com.ninja_developer.lmsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class login extends AppCompatActivity {

    private EditText editTextemail, editTextPassword;
    AppCompatButton buttonLogin;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView registerbtn, forget_password;
        registerbtn = findViewById(R.id.registerbtn);
        editTextemail = findViewById(R.id.editTextemail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        auth = FirebaseAuth.getInstance();
        forget_password = findViewById(R.id.forget_password);

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forget_password = new Intent(login.this, forgetpass.class);
                startActivity(forget_password);
            }
        });


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerbtn = new Intent(login.this, registration.class);
                startActivity(registerbtn);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = editTextemail.getText().toString();
                String pass = editTextPassword.getText().toString();

                if ((user.length() == 0 || user == null) || (pass.length() == 0 || pass == null)) {
                    Toast.makeText(login.this, "please fill the all details.", Toast.LENGTH_SHORT).show();
//                    cToast("please fill the all details.");
                } else {
                    loginUser(user, pass);
                }

            }
        });


    }

    private void loginUser(String user, String pass) {


        auth.signInWithEmailAndPassword(user, pass).
                addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(login.this, "Login Successful.", Toast.LENGTH_SHORT).show();
//                            cToast("Login Successful.");
                            startActivity(new Intent(login.this, MainActivity.class));
                            finish();
                        } else {
                            editTextemail.setText("");
                            editTextPassword.setText("");
                            Toast.makeText(login.this, "Invalid email/password", Toast.LENGTH_SHORT).show();
//                            cToast("Invalid email/password");
                        }

                    }

                });
    }
//    public  void cToast(String t)
//    {
//        LayoutInflater inflater=getLayoutInflater();
//        View layout=inflater.inflate(R.layout.toastlayout, (ViewGroup)findViewById(R.id.viewHOLDER));
//        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tv=layout.findViewById(R.id.toastText);
//        tv.setText(t);
//        Toast toast=new Toast(getApplicationContext());
//        toast.setView(layout);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.BOTTOM,0,0);
//        toast.show();
//    }
}

