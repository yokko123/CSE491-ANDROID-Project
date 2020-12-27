package com.ecom.khawadawa_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Cart extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,BottomNavigationView.OnNavigationItemSelectedListener {
    TextView locationText;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    static final float END_SCALE = 0.7f;
    ImageView menu;
    LinearLayout contentView;
    Button logout;
    Button profile;
    Button orders;
    Button cart;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //hooks
        drawerLayout = findViewById(R.id.drawerCart);
        navigationView = findViewById(R.id.navigationViewCart);
        menu = findViewById(R.id.menu);
        contentView = findViewById(R.id.contentCart);
        logout = findViewById(R.id.logout);
        profile = findViewById(R.id.profile);
        cart = findViewById(R.id.cart);
        orders = findViewById(R.id.orders);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);



        navigationDrawer();
        bottomNavigationDrawer();

    }
    private void bottomNavigationDrawer() {

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);
    }




    private void navigationDrawer() {
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.cart);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("LogOut");
                builder.setMessage("Are you sure you want to log out?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(Cart.this, SignIn.class));
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
        }
        switch(item.getItemId()){
            case R.id.profile:
                Intent intent = new Intent(Cart.this, Profile.class);
                startActivity(intent);
        }
        switch(item.getItemId()){
            case R.id.orders:
                Intent intent1 = new Intent(Cart.this, Orders.class);
                startActivity(intent1);
        }
        switch(item.getItemId()){
            case R.id.cart:
                Intent intent2 = new Intent(Cart.this, Cart.class);
                startActivity(intent2);
        }
        switch(item.getItemId()){
            case R.id.home:
                Intent intent2 = new Intent(Cart.this, Dashboard.class);
                startActivity(intent2);
        }
        switch (item.getItemId()){

            case R.id.bottomnavHome:
                startActivity(new Intent(Cart.this,Dashboard.class));
                finish();
        }
        switch (item.getItemId()){

            case R.id.bottomnavOrders:
                startActivity(new Intent(Cart.this,Orders.class));
        }
        switch (item.getItemId()){

            case R.id.bottomnavCart:
                startActivity(new Intent(Cart.this,Cart.class));
        }
        switch (item.getItemId()){

            case R.id.bottomnavProfile:
                startActivity(new Intent(Cart.this,Profile.class));
        }
        switch (item.getItemId()){

            case R.id.fab:
                startActivity(new Intent(Cart.this,Dashboard.class));
                finish();
        }

        return true;
    }
}