package com.ninja_developer.librarymanagementsystem;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.ninja_developer.librarymanagementsystem.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class add_books extends AppCompatActivity {


    SurfaceView surfaceView;
    TextView txtBarcodeValue;
    ImageView tourch;
    private BarcodeDetector barcodeDetector;
    //    static CameraManager cameraManager;
//    Static
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    AppCompatButton add_to_shopbtn;
    String intentData = "";
    boolean isEmail = false;
    //    private CameraManager cameraManager;
//    private String getCameraID;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);

        initViews();





    }


    private void initViews() {
        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
        surfaceView = findViewById(R.id.surfaceView);
        add_to_shopbtn = findViewById(R.id.add_to_library_btn);


        add_to_shopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intentData.length() > 0) {

                    startActivity(new Intent(add_books.this, filling_book_details.class).putExtra("scanned value", intentData));
                    finish();
                }
            }
        });

//        sellbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (intentData.length() > 0) {
//
//                    startActivity(new Intent(scanned_barcode.this, sell_product.class).putExtra("scanned value", intentData));
//
//                }
//            }
//        });
    }

    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(add_books.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(add_books.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
//            cToast("To prevent memory leaks barcode scanner has been stopped");
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    txtBarcodeValue.post(new Runnable() {
                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).email != null) {
                                txtBarcodeValue.removeCallbacks(null);
                                intentData = barcodes.valueAt(0).email.address;
                                txtBarcodeValue.setText(intentData);
                                isEmail = true;
                                add_to_shopbtn.setText("ADD CONTENT TO THE MAIL");
                            } else {
                                isEmail = false;
                                add_to_shopbtn.setText("Add to Shop");
                                intentData = barcodes.valueAt(0).displayValue;
                                txtBarcodeValue.setText(intentData);
                            }
                        }
                    });
                }


            }
        });
    }






    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
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