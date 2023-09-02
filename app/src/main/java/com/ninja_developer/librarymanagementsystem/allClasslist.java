package com.ninja_developer.librarymanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class allClasslist extends AppCompatActivity {
    RecyclerView recyclerView;
    static ArrayList<class_model> item=new ArrayList<>();
    static Context context;
    String className;

    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_classlist);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this,2) ;
        recyclerView.setLayoutManager(layoutManager);
        Spinner spinner=findViewById(R.id.class_name);
        AppCompatButton search_btn = findViewById(R.id.search_btn);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                className= adapterView.getItemAtPosition(i).toString().toUpperCase();
                Toast.makeText(allClasslist.this, ""+className, Toast.LENGTH_SHORT).show();
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
                all_classes();
                item.clear();
            }
        });


    }

    private void all_classes() {

        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseuser.getUid();
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("Libraries").child(userId).child("Students Card").child(className).getRef();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren()) {
                    String roll = dsp.getKey();
                    class_model model = dsp.getValue(class_model.class);
                    item.add(model);

                }
                allclasslist_adapter adapter = new allclasslist_adapter(allClasslist.this, item);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(allClasslist.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        });
    }
}