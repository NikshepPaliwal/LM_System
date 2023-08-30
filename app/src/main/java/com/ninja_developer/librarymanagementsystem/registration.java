package com.ninja_developer.librarymanagementsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.ninja_developer.librarymanagementsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registration extends AppCompatActivity {
    TextView loginpage;
    private EditText editTextemail, editTextPassword,confirmPassword;

    AppCompatButton buttonRegister;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        editTextemail=(EditText) findViewById(R.id.editTextemail);
        editTextPassword=(EditText) findViewById(R.id.editTextPassword);
        confirmPassword=findViewById(R.id.confirmPassword);
//       radioButtonMale=(RadioButton)findViewById(R.id.radioButtonMale);
//       radioButtonFemale=(RadioButton)findViewById(R.id.radioButtonFemale);
        buttonRegister=(AppCompatButton)findViewById(R.id.buttonreg);
        loginpage=findViewById(R.id.loginpage);
        auth=FirebaseAuth.getInstance();



        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user= editTextemail.getText().toString();
                String pass= editTextPassword.getText().toString();
                String cPass=confirmPassword.getText().toString();
                if(user.isEmpty() || pass.isEmpty()){
                    Toast.makeText(registration.this, "Fill all fields",Toast.LENGTH_SHORT).show();
//                    cToast("Fill all fields");
                } else if (editTextPassword.length()<6) {
                    Toast.makeText(registration.this, "Password should be atleast 6 digits",Toast.LENGTH_SHORT).show();
//                    cToast("Password should be atleast 6 digits");
                }
                else if(!pass.equals(cPass)){
                    Toast.makeText(registration.this, "Password not matched", Toast.LENGTH_SHORT).show();
                }
                else{
                    registerUser(user,pass);
                }
            }
        });

        loginpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerbtn= new Intent(registration.this,login.class);
                startActivity(registerbtn);
                finish();
            }
        });

    }

    private void registerUser(String editTextemail, String editTextPassword) {
        auth.createUserWithEmailAndPassword(editTextemail,editTextPassword).addOnCompleteListener(registration.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(registration.this, "Registration Successful",Toast.LENGTH_SHORT).show();
//                    cToast("Registration Successful");
                    startActivity(new Intent(registration.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(registration.this, "Registration failed",Toast.LENGTH_SHORT).show();
//                    cToast("Registration failed");
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