package com.ninja_developer.lms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class download_library_card extends AppCompatActivity {
    String className;
    EditText roll_number;
    AppCompatButton search_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_library_card);
        Spinner spinner=findViewById(R.id.class_name);
        search_btn=findViewById(R.id.search_btn);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                className= adapterView.getItemAtPosition(i).toString().toUpperCase();
                Toast.makeText(download_library_card.this, ""+className, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

// array list for data on spinner
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("select class");
        arrayList.add("MCA");
        arrayList.add("BTECH");
        arrayList.add("MSC");
        arrayList.add("BCA");
        arrayList.add("BSC");
        arrayList.add("BCOM");
        arrayList.add("MTECH");
        arrayList.add("Other");

        // creating adapter for spiner

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Roll_Number= roll_number.getText().toString();
                String Class_Name= className;

                findStudent(Roll_Number,Class_Name);
            }
        });

    }
    private void findStudent(String rollNumber, String className) {
        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseuser.getUid();
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("Libraries").child(userId).child("Students Card").child(className).child(rollNumber).child("Personal Data").child(rollNumber).getRef();
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        Toast.makeText(download_library_card.this, "Succesfully read", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("student_name").getValue());
                        String rollNumber = String.valueOf(dataSnapshot.child("roll").getValue());
                        String Branch = String.valueOf(dataSnapshot.child("branch").getValue());
                        String imgUrl= String.valueOf(dataSnapshot.child("imageUri").getValue());



                        // addItem(name,Price,no_products);
                    } else {


                        Toast.makeText(download_library_card.this, "Data not found.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}