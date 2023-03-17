package com.example.onlineshopping.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshopping.R;
import com.example.onlineshopping.model.productData;
import com.example.onlineshopping.ui.ItemClickListener;
import com.example.onlineshopping.viewHolder.setViewHolder;

import java.util.Collections;
import java.util.List;

public class ProductAdapter  extends RecyclerView.Adapter<setViewHolder> {
    List<productData> data= Collections.emptyList();
    public ItemClickListener item;
    private Activity activity;
    @Override
    public setViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item,parent,false);

        return new setViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  setViewHolder holder, int position) {
        setViewHolder.name.setText(data.get(position).getName());
        setViewHolder.color.setText(data.get(position).getColor());
        setViewHolder.price.setText(data.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public void setItemClicklisnter(ItemClickListener listner){
        this.item=listner;
    }
}
