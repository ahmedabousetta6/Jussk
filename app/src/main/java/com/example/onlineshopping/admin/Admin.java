package com.example.onlineshopping.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.onlineshopping.Capture;
import com.example.onlineshopping.R;
import com.example.onlineshopping.user.DBTables;
import com.example.onlineshopping.user.Products;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Admin extends AppCompatActivity {

    TextInputEditText prodName , prodDesc, prodColor , prodPrice, prodCategory;
    ImageView prodImage;
    EditText probarcode;
    Button addProduct,addbarcode;
    boolean xx = false;
    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        prodName = findViewById(R.id.productName);
        prodDesc = findViewById(R.id.productDescription);
        prodColor = findViewById(R.id.colorProduct);
        prodPrice = findViewById(R.id.priceProduct);
        prodCategory = findViewById(R.id.categoryProduct);
        probarcode=findViewById(R.id.editTextbarcode);
        prodImage = (ImageView)findViewById(R.id.imageViewProduct);
        addProduct = (Button)findViewById(R.id.btnAddProduct);
        addbarcode=(Button)findViewById(R.id.barcode);

            final DBTables admin=new DBTables(this);
            prodImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(
                            Admin.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_GALLERY
                    );
                }
            });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean x=admin.addproduct(prodName.getText().toString(),
                        prodDesc.getText().toString(),
                        prodColor.getText().toString(),
                        imageViewToByte(prodImage),
                        prodPrice.getText().toString(),
                        prodCategory.getText().toString(),probarcode.getText().toString());
                if(x==true) {
                    Toast.makeText(Admin.this, "item was added", Toast.LENGTH_LONG).show();
                }
            }
        });

        addbarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xx=true;
                IntentIntegrator intentIntegrator=new IntentIntegrator(Admin.this);
                intentIntegrator.setPrompt("hello everyone0");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (xx == true) {
            xx=false;
            IntentResult result = IntentIntegrator.parseActivityResult(
                    requestCode, resultCode, data
            );
            probarcode.setText(result.getContents());
        } else if (xx == false) {
            if (requestCode == REQUEST_CODE_GALLERY && data != null) {
                Uri uri = data.getData();

                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    prodImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}