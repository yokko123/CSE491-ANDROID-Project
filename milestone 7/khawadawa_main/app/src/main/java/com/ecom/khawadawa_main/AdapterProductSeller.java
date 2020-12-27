package com.ecom.khawadawa_main;


import android.annotation.SuppressLint;
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

import org.jetbrains.annotations.NonNls;

import java.util.ArrayList;

public class AdapterProductSeller extends RecyclerView.Adapter<AdapterProductSeller.HolderProductSeller> {

    private Context context;
    public ArrayList<ModelProduct> productList;
    //Holds view of recyclerview


    public AdapterProductSeller(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public HolderProductSeller onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate Layout

        View view = LayoutInflater.from(context).inflate(R.layout.row_product_seller,parent,false);
        return new HolderProductSeller(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HolderProductSeller holder, int position) {
        //get data
        ModelProduct modelProduct = productList.get(position);
        String id = modelProduct.getProductId();
        String uId = modelProduct.getUid();
        String discountAvailable = modelProduct.getDiscountAvailable();
        String discountNote = modelProduct.getDiscountNote();
        String discountPrice = modelProduct.getDiscountedPrice();
        String productCategory = modelProduct.getProductCategory();
        String productDescription = modelProduct.getProductDescription();
        String icon = modelProduct.getProductIcon();
        String quantity = modelProduct.getProductQuantity();
        String name = modelProduct.getProductName();
        String originalPrice = modelProduct.getOriginalPrice();

        String timeStamp = modelProduct.getTimestamp();

        //set Data
        holder.titleTv.setText(name);
        holder.quantityTv.setText(quantity);
        holder.discountedPriceTv.setText("BDT"+discountPrice);
        holder.disocuntNotetv.setText(discountNote);
        holder.originalPriceTv.setText("BDT "+originalPrice);

        if(discountAvailable.equals("true")){
            //product is on discount
            holder.discountedPriceTv.setVisibility(View.VISIBLE);
            holder.disocuntNotetv.setVisibility(View.VISIBLE);
            holder.originalPriceTv.setPaintFlags(holder.originalPriceTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            //not discount
            holder.disocuntNotetv.setVisibility(View.GONE);
            holder.originalPriceTv.setVisibility(View.GONE);
        }
        try{
            Picasso.get().load(icon).placeholder(R.drawable.addproductcicon).into(holder.productImageIv);
        }
        catch(Exception e){
            holder.productImageIv.setImageResource(R.drawable.addproductcicon);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //manage item clicks
            }
        });







    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HolderProductSeller extends RecyclerView.ViewHolder{
        //Holds view of recyclerview

        private ImageView productImageIv;
        private TextView disocuntNotetv,titleTv,quantityTv,discountedPriceTv,originalPriceTv;


        public HolderProductSeller(@NonNull View itemView){
            super(itemView);

            productImageIv = itemView.findViewById(R.id.productIconIv);
            disocuntNotetv = itemView.findViewById(R.id.discountNoteTv);
            titleTv = itemView.findViewById(R.id.titleTv);
            quantityTv = itemView.findViewById(R.id.quantityTv);
            discountedPriceTv = itemView.findViewById(R.id.discountedPriceTv);
            originalPriceTv = itemView.findViewById(R.id.originalPriceTv);


        }
    }
}
