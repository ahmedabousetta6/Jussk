package com.example.onlineshopping.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.onlineshopping.Capture;
import com.example.onlineshopping.R;
import com.example.onlineshopping.adapters.ProductGridAdapter;
import com.example.onlineshopping.admin.Admin;
import com.example.onlineshopping.model.productData;
import com.example.onlineshopping.ui.MainActivity;
import com.example.onlineshopping.ui.SignUp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Locale;

import static android.view.WindowInsets.Side.all;

public class Products extends AppCompatActivity {
    GridView gridView;
    ArrayList<productData> productList;
    ProductGridAdapter adapter=null;
    EditText proName;
    ImageButton voice,cam,search,productShow;
    DBTables database;
boolean Gate=false;




    private static final int voicecode=1;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logout:
                Intent ii=new Intent(Products.this, MainActivity.class);
                startActivity(ii);

                return true;
            case R.id.fashion:
                catt("fashion");
                return true;
            case R.id.laptop:
                catt("laptop");
                return  true;
            case R.id.mobile:
                catt("mobile");
                return true;
            case R.id.electronic:
                catt("electronic");
                return  true;

            case R.id.mycard:
                Intent intent=new Intent(Products.this,Cart.class);
                String x=getIntent().getExtras().getString("email");
                intent.putExtra("email",x);
                startActivity(intent);
        }
        return  false;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        gridView=(GridView)findViewById(R.id.gridView);

        database=new DBTables(this);
        String em=getIntent().getExtras().getString("email");
         String rr;
        productList=new ArrayList<>();
        adapter=new ProductGridAdapter(this,R.layout.product_list_item,productList,em);
        gridView.setAdapter(adapter);

        search=findViewById(R.id.btnSearch);
        cam=findViewById(R.id.cameraSearch);
        voice=findViewById(R.id.voiceSearch);
        proName=findViewById(R.id.searchEditText);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Products.this, Admin.class);
                startActivity(intent);
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        cam.setOnClickListener(new View.OnClickListener() {
          @Override
    public void onClick(View view) {
              IntentIntegrator intentIntegrator=new IntentIntegrator(Products.this);
              intentIntegrator.setPrompt("hello everyone0");
              intentIntegrator.setBeepEnabled(true);
              intentIntegrator.setOrientationLocked(true);
              intentIntegrator.setCaptureActivity(Capture.class);
              intentIntegrator.initiateScan();
                   }
                    });

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = database.searchbytext(proName.getText().toString());
                    productList.clear();
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(1);
                        String color = cursor.getString(3);
                        String price = cursor.getString(6);
                        String barcode= cursor.getString(7);
                        byte[] image = cursor.getBlob(4);

                        productList.add(new productData(name, color, price,barcode ,image));

                    }
                    adapter.notifyDataSetChanged();

                }
            });

        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                talk();
            }
        });
        Cursor cursor=database.getproduct("Select * from product");
        productList.clear();
        while (cursor.moveToNext())
         {

            String name=cursor.getString(1);
            String color=cursor.getString(3);
            String price=cursor.getString(6);
             String barcode= cursor.getString(7);
            byte [] image=cursor.getBlob(4);
            productList.add(new productData(name,color,price,barcode,image));
        }
        adapter.notifyDataSetChanged();
    }
    private void talk(){
        Gate=true;
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL , RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent , 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
            if(Gate==false)
            {

                if (result.getContents() != null) {

                  //  proName.setText(result.getContents());
                    Cursor cursor = database.searchbycat(result.getContents().toString());
                    productList.clear();
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(1);
                        String color = cursor.getString(3);
                        String price = cursor.getString(6);
                        String barcode= cursor.getString(7);
                        byte[] image = cursor.getBlob(4);
                        productList.add(new productData(name, color, price,barcode ,image));

                    }
                    adapter.notifyDataSetChanged();

                }
            }
       else if(requestCode==200 && resultCode == RESULT_OK)
        {
            ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String voic = arrayList.get(0);
            Cursor cursor = database.searchbytext(voic.toString());
            productList.clear();
            while (cursor.moveToNext()) {
                String name = cursor.getString(1);
                String color = cursor.getString(3);
                String price = cursor.getString(6);
                String barcode= cursor.getString(7);
                byte[] image = cursor.getBlob(4);
                productList.add(new productData(name, color, price,barcode ,image));

            }
            adapter.notifyDataSetChanged();

            Toast.makeText(this , voic, Toast.LENGTH_LONG).show();
            Gate=false;
        }
        else
        {
            Toast.makeText(this , "something is wrong ", Toast.LENGTH_LONG).show();
        }

    }
    public void cat(String cat)
    {
        Cursor cursor=database.searchbycat(cat);
        productList.clear();
        while(cursor.moveToNext())
        {
            String namee=cursor.getString(1);
            String color=cursor.getString(3);
            String price=cursor.getString(6);
            String barcode= cursor.getString(7);
            byte [] image=cursor.getBlob(4);
            productList.add(new productData(namee,color,price,barcode,image));

        }
        adapter.notifyDataSetChanged();
    }
    public void catt(String category)
    {
        Cursor cursor=database.searchbycategory(category);
        productList.clear();
        while(cursor.moveToNext())
        {
            String namee=cursor.getString(1);
            String color=cursor.getString(3);
            String price=cursor.getString(6);
            String barcode= cursor.getString(7);
            byte [] image=cursor.getBlob(4);
            productList.add(new productData(namee,color,price,barcode,image));

        }
        adapter.notifyDataSetChanged();
    }
}