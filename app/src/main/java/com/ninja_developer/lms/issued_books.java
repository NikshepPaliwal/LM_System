package com.ninja_developer.lms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class issued_books extends AppCompatActivity {

    static ArrayList<model> item=new ArrayList<>();
    static Context context;
    static RecyclerView recyclerView;
    TextView student_name;
//static myadapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued_books);
        student_name = findViewById(R.id.student_name);
        recyclerView=findViewById(R.id.recyclerView);
        context=getApplicationContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        GetStudentName();
        AllBooks();
        item.clear();
    }

    public void GetStudentName(){
        DatabaseReference node;
        String className, roll;
        Bundle a = getIntent().getExtras();
        className = a.getString("class_name");
        roll = a.getString("roll_number");
        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseuser.getUid();
        node = FirebaseDatabase.getInstance().getReference("Libraries").child(userId).child("Students Card").child(className).child(roll).child("Personal Data").child(roll).getRef();

        node.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {

                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("student_name").getValue());
                        student_name.setText("Welcome "+name);
                    } else {
                        Toast.makeText(issued_books.this, "Data not found.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void AllBooks() {
        String className, roll;
        Bundle a = getIntent().getExtras();
        className = a.getString("class_name");
        roll = a.getString("roll_number");
        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseuser.getUid();
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("Libraries").child(userId).child("Students Card").child(className).child(roll).child("Issued Books");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    model model = dsp.getValue(model.class);
                    item.add(model);
                }
                if(item.size() == 0){
                    Toast.makeText(issued_books.this, "No Book found.", Toast.LENGTH_SHORT).show();
                }else {
                    issued_book_adapter adapter = new issued_book_adapter(issued_books.this, item);
                    recyclerView.setAdapter(adapter);
                }
                //here clear the list
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(issued_books.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        });

    }
}