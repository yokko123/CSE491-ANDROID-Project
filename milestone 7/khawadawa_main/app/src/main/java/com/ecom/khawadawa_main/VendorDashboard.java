package com.ecom.khawadawa_main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VendorDashboard extends AppCompatActivity {

    Button toAddProduct;
    Button toShowProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_dashboard);

        toAddProduct = findViewById(R.id.toAddProduct);
        toShowProduct = findViewById(R.id.toShowProduct);

        toAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VendorDashboard.this,AddProductVendor.class);
                startActivity(intent);
            }
        });

        toShowProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VendorDashboard.this,ShowProductVendor.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}