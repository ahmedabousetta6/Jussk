package com.example.onlineshopping.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.onlineshopping.R;
import com.example.onlineshopping.model.productData;
import com.example.onlineshopping.user.ShowProduct;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ProductGridAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<productData> productlist;
    private String mail , description;

    public ProductGridAdapter(Context context, int layout, ArrayList<productData> productlist, String mail) {
        this.context = context;
        this.layout = layout;
        this.productlist = productlist;
        this.mail=mail;
    }
    @Override
    public int getCount() {
        return productlist.size();
    }

    @Override
    public Object getItem(int position) {
        return productlist.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder{
        ImageView imageView;
        TextView name,color,price;
        Button b;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        ViewHolder holder=new ViewHolder();

        if(row == null)
        {
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layout,null);
            holder.name=(TextView)row.findViewById(R.id.productCardName);
            holder.color=(TextView)row.findViewById(R.id.productCardColor);
            holder.price=(TextView)row.findViewById(R.id.productCardPrice);
            holder.imageView=(ImageView)row.findViewById(R.id.productCardImageView);
            holder.b=(Button)row.findViewById(R.id.btnShowProduct);
            row.setTag(holder);
        }
        else {
            holder=(ViewHolder)row.getTag();
        }
        productData pro=productlist.get(position);
        holder.name.setText(pro.getName());
        holder.color.setText(pro.getColor());
        holder.price.setText(pro.getPrice());
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();

        final byte[] proimage=pro.getImage();
        Bitmap bitmap= BitmapFactory.decodeByteArray(proimage,0,proimage.length);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        holder.imageView.setImageBitmap(bitmap);

        final String name1=holder.name.getText().toString();
        final String color=holder.color.getText().toString();
        final String pric =holder.price.getText().toString();
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, ShowProduct.class);
                intent.putExtra("name",name1);
              // intent.putExtra("image",proimage.equals(bStream.toByteArray()));
                intent.putExtra("color",color);
                intent.putExtra("price",pric);
                intent.putExtra("email",mail);
                intent.putExtra("des",mail);
                context.startActivity(intent);
            }
        });

        return row;
    }
}
