package com.ninja_developer.librarymanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ninja_developer.librarymanagementsystem.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class student_card_form extends AppCompatActivity {
    String className;
    AppCompatButton select_img, saveData;
    int SELECT_PICTURE = 200;
    ImageView imgView;
    Uri filePath;
    EditText student_name,roll_Number,branch, father_name,mobile_number,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_card_form);
        Spinner spinner=findViewById(R.id.class_name);
        student_name= findViewById(R.id.student_name);
        roll_Number= findViewById(R.id.roll_number);
        branch= findViewById(R.id.branch);
        father_name= findViewById(R.id.father_name);
        mobile_number= findViewById(R.id.mobile_number);
        address= findViewById(R.id.address);
        saveData= findViewById(R.id.saveData);
        imgView= findViewById(R.id.imageView);
        select_img= findViewById(R.id.select_img);



        select_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });


     spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              className= adapterView.getItemAtPosition(i).toString().toUpperCase();
             Toast.makeText(student_card_form.this, ""+className, Toast.LENGTH_SHORT).show();
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


    saveData.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            uploadtofirebase();
        }
    });




    }

    private void uploadtofirebase() {
            ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setTitle("Please wait....");
            progressDialog.show();
            String name=student_name.getText().toString().toUpperCase();
            String roll_number= roll_Number.getText().toString();
            String Class= className;
            String Branch= branch.getText().toString().toUpperCase();
            String fatherName= father_name.getText().toString().toUpperCase();
            String mob= mobile_number.getText().toString();
            String Add= address.getText().toString();

        FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseuser.getUid();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imgUploader= storage.getReference("uploads").child(userId).child("Image"+roll_number);

        imgUploader.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imgUploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        progressDialog.dismiss();
                        if (name.equals("") || roll_number.equals("") || Class.equals("SELECT CLASS") || Branch.equals("") || fatherName.equals("") || mob.equals("") || Add.equals("")) {
                            Toast.makeText(student_card_form.this, "Fill all the details.", Toast.LENGTH_SHORT).show();
                        } else {

                            FirebaseDatabase db = FirebaseDatabase.getInstance();
                            DatabaseReference node = db.getReference("Libraries");
                            FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = firebaseuser.getUid();
                            student_form_helper studentFormHelper = new student_form_helper(name, roll_number, Class, Branch, fatherName, mob, Add, uri.toString());
                            node.child(userId).child("Students Card").child(Class).child(roll_number).child("Personal Data").child(roll_number).setValue(studentFormHelper);
                            student_name.setText("");
                            roll_Number.setText("");
                            branch.setText("");
                            father_name.setText("");
                            mobile_number.setText("");
                            address.setText("");
                            imgView.setImageResource(R.drawable.ic_launcher_background);
                            Toast.makeText(student_card_form.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float per= (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded "+ (int)per+" %");

                    }
                })
                .addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Toast.makeText(student_card_form.this, "uploading cancel", Toast.LENGTH_SHORT).show();
                    }
                });

//            if(name.equals("") || roll.equals("") || Class.equals("SELECT CLASS") || Branch.equals("") || fatherName.equals("") ||mob.equals("") || Add.equals("")){
//                Toast.makeText(student_card_form.this, "Fill all the details.", Toast.LENGTH_SHORT).show();
//            }else {
//
//                FirebaseDatabase db = FirebaseDatabase.getInstance();
//                DatabaseReference node = db.getReference("Libraries");
//                FirebaseUser firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
//                String userId = firebaseuser.getUid();
//                student_form_helper studentFormHelper = new student_form_helper(name, roll, Class, Branch, fatherName, mob, Add);
//                node.child(userId).child("Students Card").child(Class).child(roll).child("Personal Data").child(roll).setValue(studentFormHelper);
//                student_name.setText("");
//                roll_number.setText("");
//                branch.setText("");
//                father_name.setText("");
//                mobile_number.setText("");
//                address.setText("");
//                Toast.makeText(student_card_form.this, "Registration Successful", Toast.LENGTH_SHORT).show();
//            }
    }


    void imageChooser() {

            // create an instance of the
            // intent of the type image
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);

            // pass the constant to compare it
            // with the returned requestCode
            startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
        }

        // this function is triggered when user
        // selects the image from the imageChooser
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {

                // compare the resultCode with the
                // SELECT_PICTURE constant
                if (requestCode == SELECT_PICTURE) {
                    // Get the url of the image from data
                    filePath = data.getData();
                    if (null != filePath) {
                        // update the preview image in the layout
                        imgView.setImageURI(filePath);
                    }
                }
            }
        }

}