package com.example.Lab_AndroidAPI.adapter;

import static com.example.Lab_AndroidAPI.services.ApiServices.IP;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Lab_AndroidAPI.R;
import com.example.Lab_AndroidAPI.databinding.ItemFruitBinding;
import com.example.Lab_AndroidAPI.model.Distributor;
import com.example.Lab_AndroidAPI.model.Fruit;
import com.example.Lab_AndroidAPI.view.HomeActivity;
import com.example.Lab_AndroidAPI.view.LocationActivity;

import java.util.ArrayList;

public class FruitAdapter  extends RecyclerView.Adapter<FruitAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Fruit> list;
    private FruitClick fruitClick;

    public FruitAdapter(Context context, ArrayList<Fruit> list, FruitClick fruitClick) {
        this.context = context;
        this.list = list;
        this.fruitClick = fruitClick;
    }

    public interface FruitClick {
        void delete(Fruit fruit);
        void edit(Fruit fruit);

        void addCart(Fruit fruit);
    }

    @NonNull
    @Override
    public FruitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFruitBinding binding = ItemFruitBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FruitAdapter.ViewHolder holder, int position) {
        Fruit fruit = list.get(position);
        holder.binding.tvName.setText(fruit.getName());
        holder.binding.tvPriceQuantity.setText("price :" +fruit.getPrice()+" - quantity:" +fruit.getQuantity());
        holder.binding.tvDes.setText(fruit.getDesciption());
        if(!fruit.getImage().isEmpty()){
            String url  = fruit.getImage().get(0);
            String newUrl = url.replace("localhost", IP);
            Glide.with(context)
                    .load(newUrl)
                    .thumbnail(Glide.with(context).load(R.drawable.baseline_broken_image_24))
                    .into(holder.binding.img);
        }

        holder.binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fruitClick.edit(fruit);
            }
        });
        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fruitClick.delete(fruit);
            }
        });
        holder.binding.btnCart.setOnClickListener(v->{
            fruitClick.addCart(fruit);

        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemFruitBinding binding;
        public ViewHolder(ItemFruitBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
