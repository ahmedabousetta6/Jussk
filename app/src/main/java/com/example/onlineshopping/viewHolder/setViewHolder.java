package com.example.onlineshopping.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshopping.R;
import com.example.onlineshopping.ui.ItemClickListener;

public class setViewHolder extends RecyclerView.ViewHolder  {
    public static TextView name;
    public static TextView color;
    public static TextView price;
    public ImageView image;
    public ItemClickListener item;
    public setViewHolder(@NonNull View itemView) {
        super(itemView);
        name=(TextView)itemView.findViewById(R.id.productCardName);
        color=(TextView)itemView.findViewById(R.id.productCardColor);
        price=(TextView)itemView.findViewById(R.id.productCardPrice);
        image=(ImageView)itemView.findViewById(R.id.productCardImageView);
    }
}
