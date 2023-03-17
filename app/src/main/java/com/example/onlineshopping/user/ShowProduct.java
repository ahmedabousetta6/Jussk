package com.example.onlineshopping.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopping.R;
import com.example.onlineshopping.ui.StartActivity;

import at.markushi.ui.CircleButton;

public class ShowProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        final String name=getIntent().getExtras().getString("name");
        final String color=getIntent().getExtras().getString("color");
        final String price=getIntent().getExtras().getString("price");
        final String email=getIntent().getExtras().getString("email");
        final DBTables database=new DBTables(this);
        TextView productName=findViewById(R.id.product_Name);
        TextView productColor=(TextView)findViewById(R.id.product_color);
        TextView productPrice=(TextView)findViewById(R.id.product_price);
        TextView productDes=(TextView)findViewById(R.id.product_Description);
        final TextView amount=(TextView)findViewById(R.id.numberOfQuantity);
        CircleButton minus=findViewById(R.id.minus);
        CircleButton plus=findViewById(R.id.plus);
        Button add=(Button)findViewById(R.id.btnAddToCart);

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number=Integer.parseInt(amount.getText().toString());
                number=number-1;
                String nw = Integer.toString(number);
                amount.setText(nw);

            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number=Integer.parseInt(amount.getText().toString());
                number=number+1;
                String nw = Integer.toString(number);
                amount.setText(nw);
            }
        });
       add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p=Integer.parseInt(price);
                int a=Integer.parseInt(amount.getText().toString());
                int mul=p*a;
                String totalprice=Integer.toString(mul);

                boolean y=database.addbuy(email,name,amount.getText().toString(),totalprice);
                if(y==true)
                {
                    Toast.makeText(getApplicationContext(),"Product Added To Your Cart",Toast.LENGTH_LONG).show();
                     Intent intent=new Intent(ShowProduct.this, Products.class);
                     startActivity(intent);
                }

            }
        });
        ImageView imageView=findViewById(R.id.productImageView);

        productName.setText(name);
        productColor.setText(color);
        productPrice.setText(price);

    }
}