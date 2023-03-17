package com.example.onlineshopping.user;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineshopping.R;

public class Cart extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    DBTables product;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        getMenuInflater().inflate(R.menu.cardmenu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String selectedproduct=((TextView)info.targetView).getText().toString();
        int id=item.getItemId();
        if(id==R.id.delete) {
            arrayAdapter.remove(selectedproduct);
            product.deletebuy(selectedproduct);
            return true;

        }
        if(id==R.id.update)
        {
            Intent i=new Intent(Cart.this,UpdateCart.class);
            i.putExtra("productname",selectedproduct);
            i.putExtra("amount",product.getamount(selectedproduct));
            i.putExtra("cost",product.getcost(selectedproduct));
            startActivity(i);

        }
        return super.onContextItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        listView=(ListView)findViewById(R.id.cartListView);
        TextView cost=(TextView)findViewById(R.id.costTextView);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);
        registerForContextMenu(listView);

        Button confirm=findViewById(R.id.btnConfirmCart);
        product=new DBTables(this);
        final String email=getIntent().getExtras().getString("email");
        int total=0;
        Cursor cursor=product.getbuy(email);
        while (cursor.moveToNext())
        {
            arrayAdapter.add(cursor.getString(2));
            String t=cursor.getString(4);
            int x=Integer.parseInt(t);
            total+=x;
        }
        cost.setText(Integer.toString(total));


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(   Cart.this, Get_location.class);
                startActivity(intent);
            }
        });
    }
}