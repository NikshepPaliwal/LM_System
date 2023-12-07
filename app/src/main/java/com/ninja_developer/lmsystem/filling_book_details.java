package com.ninja_developer.lmsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class filling_book_details extends AppCompatActivity {
    EditText scannedValue, book_title,publish_place,total_pages,author_name,book_condition, fine;
    AppCompatButton add_productbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filling_book_details);
        initViews();
        book_title=findViewById(R.id.book_title);
        publish_place=findViewById(R.id.publish_place);
        total_pages=findViewById(R.id.total_pages);
        author_name=findViewById(R.id.author_name);
        book_condition=findViewById(R.id.book_condition);
        fine=findViewById(R.id.fine);
    }
    private void initViews() {
        add_productbtn = findViewById(R.id.add_productbtn);
        scannedValue = findViewById(R.id.scannedValue);
        if (getIntent().getStringExtra("scanned value") != null) {
            scannedValue.setText(getIntent().getStringExtra("scanned value"));
        }
        add_productbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productId=scannedValue.getText().toString().trim();
                String b_name=book_title.getText().toString().trim();
                String p_place=publish_place.getText().toString().trim();
                String no_of_pages=total_pages.getText().toString().trim();
                String author=author_name.getText().toString().trim();
                String b_condition=book_condition.getText().toString().trim();
                String charge=fine.getText().toString().trim();
                book_data_constructor obj=new book_data_constructor(b_name,p_place,no_of_pages, author,b_condition,charge);
                FirebaseDatabase db=FirebaseDatabase.getInstance();
                DatabaseReference node= db.getReference("Libraries");
                FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
                String userId = firebaseuser.getUid();
                node.child(userId).child("Book Details").child(productId).setValue(obj);
                scannedValue.setText("");
                book_title.setText("");
                publish_place.setText("");
                total_pages.setText("");
                author_name.setText("");
                book_condition.setText("");
                fine.setText("");
                Toast.makeText(filling_book_details.this, "Data Added", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(filling_book_details.this, add_books.class);
                startActivity(intent);
                finish();
            }
        });
    }
}