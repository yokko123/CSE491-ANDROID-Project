package com.ecom.khawadawa_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowProductsUser extends AppCompatActivity {

    private RecyclerView productRv;

    private ArrayList<ModelProduct> productList;
    private AdapterProductSeller adapterProductSeller;

    private String shopId;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products_user);

        shopId = getIntent().getStringExtra("id");
        productRv = findViewById(R.id.productsRv);
        firebaseAuth = FirebaseAuth.getInstance();
       // Log.d("Tag",shopId);



        loadShopProducts();
    }

    private void loadShopProducts(){

        productList = new ArrayList<>();

        DatabaseReference refer = FirebaseDatabase.getInstance().getReference("vendor");
        refer.child("C79lt1sTphbD4qmat7jzJvxgevw2").child("Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        productList.clear();
                        for (DataSnapshot ds: snapshot.getChildren()){
                            ModelProduct modelProduct = ds.getValue(ModelProduct.class);
                            productList.add(modelProduct);

                        }
                        adapterProductSeller = new AdapterProductSeller(ShowProductsUser.this,productList);
                        productRv.setAdapter(adapterProductSeller);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}