package com.ninja_developer.librarymanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.ninja_developer.librarymanagementsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    CardView generate_student_card, add_books, return_book,issue_books,all_student_list;
    FirebaseUser user;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generate_student_card = findViewById(R.id.generate_student_card);
        add_books = findViewById(R.id.add_books);
        return_book = findViewById(R.id.return_book);
        logout = findViewById(R.id.logout);
        issue_books=findViewById(R.id.issue_books);
        all_student_list = findViewById(R.id.all_students_list);
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
            finish();
        } else {
            generate_student_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, student_card_form.class);
                    startActivity(intent);
                }
            });


            add_books.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, add_books.class);
                    startActivity(intent);
                }
            });


            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseAuth.getInstance().signOut();
                    user = null;
                    if (user == null) {
                        Toast.makeText(MainActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    Intent intent = new Intent(MainActivity.this, login.class);
                    startActivity(intent);


                }
            });


            return_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(MainActivity.this, get_student_profile.class);
                    startActivity(intent);
                }
            });
            all_student_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, allClasslist.class));
                }
            });

        }
    }
}