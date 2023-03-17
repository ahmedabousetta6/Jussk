package com.example.onlineshopping.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.onlineshopping.R;

import at.markushi.ui.CircleButton;

public class UpdateCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cart);
        CircleButton plus=findViewById(R.id.plusUpdate);
        CircleButton minus=findViewById(R.id.minusUpdate);
        Button confirm=(Button)findViewById(R.id.btnUpdateQuantity);
        final DBTables database=new DBTables(this);

        final TextView proname=(TextView)findViewById(R.id.updateName);
        final TextView proprice=(TextView)findViewById(R.id.updatePrice);
        final TextView proamount=(TextView)findViewById(R.id.updateQuantity);

        String name=getIntent().getExtras().getString("productname");
        String price=getIntent().getExtras().getString("cost");
        String amount=getIntent().getExtras().getString("amount");

        proamount.setText(amount);
        proname.setText(name);
        proprice.setText(price);

        int old1=Integer.parseInt(price);
        int old2=Integer.parseInt(amount);

        final int oldprice=old1/old2;

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number=Integer.parseInt(proamount.getText().toString());
                number=number-1;
                String nw = Integer.toString(number);
                proamount.setText(nw);

            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number=Integer.parseInt(proamount.getText().toString());
                number=number+1;
                String nw = Integer.toString(number);
                proamount.setText(nw);
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int n=Integer.parseInt(proamount.getText().toString());
                int newcost=n*oldprice;
                String cost=Integer.toString(newcost);
                database.updatebuy(proname.getText().toString(),proamount.getText().toString(),cost);

                /*Intent intent=new Intent(UpdateCart.this, test.class);
                startActivity(intent);*/
            }

        });
    }
}