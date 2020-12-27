package com.ecom.khawadawa_main;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProductUser extends RecyclerView.Adapter<AdapterProductUser.HolderProductUser> {

    private Context context2;
    public ArrayList<ModelProduct> productsList;

    public AdapterProductUser(Context context, ArrayList<ModelProduct> productsList) {
        this.context2 = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public HolderProductUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context2).inflate(R.layout.row_shop,parent,false);

        return new HolderProductUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProductUser holder, int position) {

        ModelProduct modelProduct1 = productsList.get(position);
        String id = modelProduct1.getProductId();
        String uId = modelProduct1.getUid();
        String discountAvailable = modelProduct1.getDiscountAvailable();
        String discountNote = modelProduct1.getDiscountNote();
        String discountPrice = modelProduct1.getDiscountedPrice();
        String productCategory = modelProduct1.getProductCategory();
        String productDescription = modelProduct1.getProductDescription();
        String icon = modelProduct1.getProductIcon();
        String quantity = modelProduct1.getProductQuantity();
        String productTitle = modelProduct1.getProductName();
        String originalPrice = modelProduct1.getOriginalPrice();
        String productImageIv = modelProduct1.getProductIcon();

        String timeStamp = modelProduct1.getTimestamp();

        //set Data
        holder.titleTview.setText(productTitle);
        holder.discountNoteTv.setText(discountNote);
        holder.descriptionTv.setText(productDescription);
        holder.originalPriceTv.setText(originalPrice);
        holder.discountedPriceTv.setText(discountPrice);

        if(discountAvailable.equals("true")){
            //product is on discount
            holder.discountedPriceTv.setVisibility(View.VISIBLE);
            holder.discountNote.setVisibility(View.VISIBLE);
            holder.originalPriceTv.setPaintFlags(holder.originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            //not discount
            holder.discountNote.setVisibility(View.GONE);
            holder.originalPriceTv.setVisibility(View.VISIBLE);
        }
        try{
            Picasso.get().load(icon).placeholder(R.drawable.addproductcicon).into(holder.productIconIv);
        }
        catch(Exception e){
            holder.productIconIv.setImageResource(R.drawable.addproductcicon);
        }

        holder.addToCartTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show product details
            }
        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class HolderProductUser extends RecyclerView.ViewHolder{

        //uid views
        private ImageView productIconIv;
        private TextView discountNoteTv;
        private TextView titleTview;
        private TextView descriptionTv;
        private TextView addToCartTv;
        private TextView discountedPriceTv;
        private TextView originalPriceTv;
        private TextView discountNote;


        public HolderProductUser(@NonNull View itemView) {
            super(itemView);

            productIconIv = itemView.findViewById(R.id.productIconIv);
            discountNoteTv = itemView.findViewById(R.id.discountNoteTv);
            titleTview = itemView.findViewById(R.id.titleTview);
            descriptionTv = itemView.findViewById(R.id.descriptionTv);
            addToCartTv = itemView.findViewById(R.id.addToCartTv);
            discountedPriceTv = itemView.findViewById(R.id.discountedPriceTv);
            originalPriceTv = itemView.findViewById(R.id.originalPriceTv);
            discountNote = itemView.findViewById(R.id.discountNoteTv);
        }
    }
}
