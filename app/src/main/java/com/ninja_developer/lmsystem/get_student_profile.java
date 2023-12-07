package com.ninja_developer.lmsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class get_student_profile extends AppCompatActivity {
    String className;
    EditText roll_number;
    TextView studentName, branch, Roll;
    AppCompatButton search_btn, issued_books_btn, issue_books, return_book,delete_account;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_student_profile);
        Spinner spinner=findViewById(R.id.class_name);
        roll_number=findViewById(R.id.roll_number);
        search_btn=findViewById(R.id.search_btn);
        issue_books=findViewById(R.id.issue_books);
        issued_books_btn=findViewById(R.id.issued_books_btn);
        studentName=findViewById(R.id.NAME);
        Roll= findViewById(R.id.Roll);
        branch= findViewById(R.id.branch);
        imageView=findViewById(R.id.imageView);
        return_book = findViewById(R.id.return_book);
        delete_account = findViewById(R.id.delete_account);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                className= adapterView.getItemAtPosition(i).toString().toUpperCase();
                Toast.makeText(get_student_profile.this, ""+className, Toast.LENGTH_SHORT).show();
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

        issue_books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(get_student_profile.this, issue_books_to_students.class));


            }
        });

        issued_books_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(get_student_profile.this, issued_books.class);
                Bundle b= new Bundle();
                b.putString("roll_number", roll_number.getText().toString());
                b.putString("class_name", className.toString());
                i.putExtras(b);
                startActivity(i);
            }
        });
        return_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(get_student_profile.this, return_book.class);
                Bundle b= new Bundle();
                b.putString("roll_number", roll_number.getText().toString());
                b.putString("class_name", className.toString());
                i.putExtras(b);
                startActivity(i);

            }
        });
        delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String Roll_Number= roll_number.getText().toString();
                FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
                String userId = firebaseuser.getUid();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference imgUploader= storage.getReference("uploads").child(userId).child("Image"+Roll_Number);
                DatabaseReference databaseReference;
                databaseReference = FirebaseDatabase.getInstance().getReference("Libraries").child(userId).child("Students Card").child(className).child(Roll_Number);
                imgUploader.delete();
                databaseReference.removeValue();
                issued_books_btn.setVisibility(View.INVISIBLE);
                issue_books.setVisibility(View.INVISIBLE);
                return_book.setVisibility(View.INVISIBLE);
                delete_account.setVisibility(View.INVISIBLE);
                studentName.setText("");
                Roll.setText("");
                branch.setText("");
                imageView.setImageResource(R.drawable.ic_launcher_background);

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
                        Toast.makeText(get_student_profile.this, "Succesfully read", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("student_name").getValue());
                        String rollNumber = String.valueOf(dataSnapshot.child("roll").getValue());
                        String Branch = String.valueOf(dataSnapshot.child("branch").getValue());
                        String imgUrl= String.valueOf(dataSnapshot.child("imageUri").getValue());
                        studentName.setVisibility(View.VISIBLE);
                        Roll.setVisibility(View.VISIBLE);
                        branch.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        studentName.setText(name);
                        Roll.setText(rollNumber);
                        branch.setText(Branch);
                        issued_books_btn.setVisibility(View.VISIBLE);
                        issue_books.setVisibility(View.VISIBLE);
                        return_book.setVisibility(View.VISIBLE);
                        delete_account.setVisibility(View.VISIBLE);
                        Picasso.get().load(imgUrl).into(imageView);


                        // addItem(name,Price,no_products);
                    } else {
                        studentName.setVisibility(View.INVISIBLE);
                        Roll.setVisibility(View.INVISIBLE);
                        branch.setVisibility(View.INVISIBLE);
                        imageView.setVisibility(View.INVISIBLE);
                        issued_books_btn.setVisibility(View.INVISIBLE);
                        issue_books.setVisibility(View.INVISIBLE);
                        return_book.setVisibility(View.INVISIBLE);
                        delete_account.setVisibility(View.INVISIBLE);

                        Toast.makeText(get_student_profile.this, "Data not found.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}