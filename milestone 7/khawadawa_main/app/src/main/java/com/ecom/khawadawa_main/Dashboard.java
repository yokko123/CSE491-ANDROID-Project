package com.ecom.khawadawa_main;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Dashboard extends AppCompatActivity implements OnNavigationItemSelectedListener,BottomNavigationView.OnNavigationItemSelectedListener {
    TextView locationText;
    private RelativeLayout shopsRl, ordersRl;
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
    FirebaseAuth firebaseAuthentication;

    private ArrayList<ModelShop> shopList;
    private AdapterShop adapterShop;

    private RecyclerView shopsRv;

    private TextView shopTv, orderTv;

    FusedLocationProviderClient fusedLocationProviderClient;

    @SuppressLint("MissingPermission")
    private void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE
        );
        //check condition
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //When location service is enabled
            //get last location

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    //Initialize location
                    Location location = task.getResult();

                    if(location!=null){
                        //When location is not null
                        Geocoder geocoder = new Geocoder(Dashboard.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            locationText.setText(addresses.get(0).getAddressLine(0));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(1000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback(){

                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();

                            }
                        };

                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());
                    }

                }
            });

        }
        else{
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        //Hooks
        ImageSlider imageSlider = findViewById(R.id.slider);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        menu = findViewById(R.id.menu);
        contentView = findViewById(R.id.content);
        logout = findViewById(R.id.logout);
        profile = findViewById(R.id.profile);
        cart = findViewById(R.id.cart);
        orders = findViewById(R.id.orders);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setBackground(null);
        shopTv = findViewById(R.id.shopsTv);
        orderTv = findViewById(R.id.orderTv);
        shopsRl = findViewById(R.id.shopsRl);
        ordersRl = findViewById(R.id.ordersRl);
        shopsRv = findViewById(R.id.shopsRecyclerView);

        firebaseAuthentication = FirebaseAuth.getInstance();








        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.slider5,"image1"));
        slideModels.add(new SlideModel(R.drawable.slider4,"image2"));
        slideModels.add(new SlideModel(R.drawable.slider3,"image3"));
        slideModels.add(new SlideModel(R.drawable.slider1,"image4"));
        slideModels.add(new SlideModel(R.drawable.slider2,"image5"));
        imageSlider.setImageList(slideModels,true);

        //geolocation
        locationText = findViewById(R.id.locationText);

                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Dashboard.this);
                //check permission
                if (ActivityCompat.checkSelfPermission(Dashboard.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Dashboard.this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    //when permission granted

                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(Dashboard.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                }




        navigationDrawer();
        bottomNavigationDrawer();


        shopTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // loadAllShops();
                showShopsUi();
            }
        });

        orderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrdersUi();
            }
        });

       loadAllShops();
        //loadAllProducts();
        showShopsUi();


    }

    private void loadAllShops() {

        shopList = new ArrayList<>();

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("vendor");

        reference1
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        shopList.clear();
                        for(DataSnapshot ds: snapshot.getChildren()){
                            ModelShop modelShop = ds.getValue(ModelShop.class);
                            shopList.add(modelShop);
                        }

                         adapterShop = new AdapterShop(Dashboard.this,shopList);

                        shopsRv.setAdapter(adapterShop);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

//    private void loadAllProducts() {
//        productList = new ArrayList<>();
//
//        //get All products
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("vendor");
//
//        reference.child(firebaseAuthentication.getUid())
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        productList.clear();
//                        for(DataSnapshot dst: snapshot.getChildren()){
//                            ModelProduct modelProduct = dst.getValue(ModelProduct.class);
//                            productList.add(modelProduct);
//
//                        }
//                        //setup adapter
//                        adapterProductSeller = new AdapterProductSeller(Dashboard.this, productList);
//                        //setAdapter
//                        shopsRv.setAdapter(adapterProductSeller);
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//    }

    private void showShopsUi() {
        shopsRl.setVisibility(View.VISIBLE);
        ordersRl.setVisibility(View.GONE);
        shopTv.setTextColor(getResources().getColor(R.color.purple_200));
        shopTv.setBackgroundResource(R.drawable.shape_rect01);


        orderTv.setTextColor(getResources().getColor(R.color.purple_200));
        orderTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }


    private void showOrdersUi(){
        shopsRl.setVisibility(View.GONE);
        ordersRl.setVisibility(View.VISIBLE);


        shopTv.setTextColor(getResources().getColor(R.color.purple_200));
        shopTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));


        orderTv.setTextColor(getResources().getColor(R.color.purple_200));
        orderTv.setBackgroundResource(R.drawable.shape_rect01);
    }




    private void bottomNavigationDrawer() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


    }




    private void navigationDrawer() {
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);

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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("LogOut");
            builder.setMessage("Are you sure you want to exit?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finishAffinity();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
            //super.onBackPressed();
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
                        startActivity(new Intent(Dashboard.this, SignIn.class));
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
                Intent intent = new Intent(Dashboard.this, Profile.class);
                startActivity(intent);
        }
        switch(item.getItemId()){
            case R.id.orders:
                Intent intent1 = new Intent(Dashboard.this, Orders.class);
                startActivity(intent1);
        }
        switch(item.getItemId()){
            case R.id.cart:
                Intent intent2 = new Intent(Dashboard.this, Cart.class);
                startActivity(intent2);
        }
        switch(item.getItemId()){
            case R.id.home:
                Intent intent2 = new Intent(Dashboard.this, Dashboard.class);
                startActivity(intent2);
        }
        switch (item.getItemId()){

            case R.id.bottomnavHome:
                startActivity(new Intent(Dashboard.this,Dashboard.class));
                finish();
        }
        switch (item.getItemId()){

            case R.id.bottomnavOrders:
                startActivity(new Intent(Dashboard.this,Orders.class));
        }
        switch (item.getItemId()){

            case R.id.bottomnavCart:
                startActivity(new Intent(Dashboard.this,Cart.class));
        }
        switch (item.getItemId()){

            case R.id.bottomnavProfile:
                startActivity(new Intent(Dashboard.this,Profile.class));
        }
        switch (item.getItemId()){

            case R.id.fab:
                startActivity(new Intent(Dashboard.this,Dashboard.class));
                finish();
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==100 && grantResults.length>0 && (grantResults[0] + grantResults[1]
        == PackageManager.PERMISSION_GRANTED)){
            getLocation();
        }else{
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
}