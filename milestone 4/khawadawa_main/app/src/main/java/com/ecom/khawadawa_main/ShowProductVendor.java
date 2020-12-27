package com.ecom.khawadawa_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowProductVendor extends AppCompatActivity {

    TextView productsTv,ordersTv;
    private RelativeLayout productsRl, ordersRl;
    private RecyclerView productsRv;

    private ArrayList<ModelProduct> productList;
    private AdapterProductSeller adapterProductSeller;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product_vendor);


        productsTv = findViewById(R.id.productsTv);
        ordersTv = findViewById(R.id.ordersTv);
        productsRl = findViewById(R.id.productsRl);
        ordersRl = findViewById(R.id.ordersRl);
        productsRv = findViewById(R.id.productsRv);
        firebaseAuth = FirebaseAuth.getInstance();
        loadAllProducts();

        showOrdersUi();


        productsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProductsUi();
            }
        });

        ordersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrdersUi();
            }
        });
    }

    private void loadAllProducts() {
        productList = new ArrayList<>();

        //get All products

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("vendor");

        reference.child(firebaseAuth.getUid()).child("Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        productList.clear();
                        for(DataSnapshot dst: snapshot.getChildren()){
                            ModelProduct modelProduct = dst.getValue(ModelProduct.class);
                            productList.add(modelProduct);

                        }
                        //setup adapter
                        adapterProductSeller = new AdapterProductSeller(ShowProductVendor.this,productList);
                        //setAdapter
                        productsRv.setAdapter(adapterProductSeller);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void showProductsUi() {
        //show products ui and hide orders ui
        productsRl.setVisibility(View.VISIBLE);
        ordersRl.setVisibility(View.GONE);

        productsTv.setTextColor(getResources().getColor(R.color.purple_200));
        productsTv.setBackgroundResource(R.drawable.shape_rect01);


        ordersTv.setTextColor(getResources().getColor(R.color.purple_200));
        ordersTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void showOrdersUi() {
        productsRl.setVisibility(View.GONE);
        ordersRl.setVisibility(View.VISIBLE);


        productsTv.setTextColor(getResources().getColor(R.color.purple_200));
        productsTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));


        ordersTv.setTextColor(getResources().getColor(R.color.purple_200));
        ordersTv.setBackgroundResource(R.drawable.shape_rect01);
    }
}