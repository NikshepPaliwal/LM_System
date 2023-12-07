package com.ninja_developer.lmsystem;

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
import java.util.Date;

public class book_issue_form extends AppCompatActivity {
    EditText bookBarcode, book_title,roll_number, author_name,book_condition;
    String className;
    AppCompatButton issue;
    String barcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_issue_form);
        issue= findViewById(R.id.issue);
        bookBarcode = findViewById(R.id.bookBarcode);
        book_title = findViewById(R.id.book_title);
        roll_number = findViewById(R.id.roll_number);
        author_name = findViewById(R.id.author_name);
        book_condition = findViewById(R.id.book_condition);
        Spinner spinner = findViewById(R.id.class_name);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                className= adapterView.getItemAtPosition(i).toString().toUpperCase();
                Toast.makeText(book_issue_form.this, ""+className, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        bookBarcode = findViewById(R.id.bookBarcode);
        if (getIntent().getStringExtra("scanned value") != null) {
            bookBarcode.setText(getIntent().getStringExtra("scanned value"));
        }
        barcode = bookBarcode.getText().toString();
        findBook(barcode);
    }

    private void findBook(String Barcode) {
        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseuser.getUid();
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("Libraries").child(userId).child("Book Details").child(Barcode).getRef();
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        Toast.makeText(book_issue_form.this, "Succesfully read", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        String Author_name = String.valueOf(dataSnapshot.child("author_name").getValue());
                        String Book_Title = String.valueOf(dataSnapshot.child("book_title").getValue());
                        String Condition = String.valueOf(dataSnapshot.child("book_condition").getValue());
                        author_name.setText(Author_name);
                        book_title.setText(Book_Title);
                        book_condition.setText(Condition);
                        // addItem(name,Price,no_products);
                    } else {
                        Toast.makeText(book_issue_form.this, "Data not found.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void saveData(){
        String bName=book_title.getText().toString();
        String AName= author_name.getText().toString();
        String B_Condition = book_condition.getText().toString();
        String C_Name= className;
        String roll = roll_number.getText().toString();

        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseuser.getUid();
        DatabaseReference databaseReference;
        Date d1 = new Date();
        databaseReference = FirebaseDatabase.getInstance().getReference("Libraries").child(userId).child("Students Card").child(className).child(roll).child("Issued Books").child(barcode);
        book_issue_constructor obj = new book_issue_constructor(barcode,bName, roll, AName, B_Condition,C_Name,d1.toString());
        databaseReference.setValue(obj);
        bookBarcode.setText("");
        book_title.setText("");
        roll_number.setText("");
        author_name.setText("");
        book_condition.setText("");
        Toast.makeText(this, "Issued", Toast.LENGTH_SHORT).show();
        finish();

    }
}