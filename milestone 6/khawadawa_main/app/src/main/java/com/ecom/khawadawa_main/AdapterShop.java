package com.ecom.khawadawa_main;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NonNls;

import java.util.ArrayList;

public class AdapterShop extends RecyclerView.Adapter<AdapterShop.HolderShop> {

    private Context context1;
    public ArrayList<ModelShop> shopList;
    //Holds view of recyclerview


    public AdapterShop(Context context, ArrayList<ModelShop> shopList) {
        this.context1 = context;
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public HolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate Layout

        View view = LayoutInflater.from(context1).inflate(R.layout.row_shop,parent,false);
        return new HolderShop(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HolderShop holder, int position) {
        //get data
        ModelShop modelShop = shopList.get(position);
        final String id = modelShop.getUid1();
        String shop = modelShop.getShopName();
        String email = modelShop.getVendorMail();
        String phone = modelShop.getVendorPhone();

        //set Data
        holder.shopName.setText(shop);
        holder.vendorEmail.setText(email);
        holder.vendorPhone.setText(phone);

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context1,ShowProductsUser.class);
               intent.putExtra("shopid",id);
               context1.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    class HolderShop extends RecyclerView.ViewHolder{
        //Holds view of recyclerview

        private TextView shopName,vendorEmail,vendorPhone;


        public HolderShop(@NonNull View itemView){
            super(itemView);

           shopName = itemView.findViewById(R.id.shopNameTv);
           vendorEmail = itemView.findViewById(R.id.emailTv);
           vendorPhone = itemView.findViewById(R.id.phoneTv);


        }
    }
}
