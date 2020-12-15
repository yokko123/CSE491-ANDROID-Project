package com.ecom.khawadawa_main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ImageSlider imageSlider = findViewById(R.id.slider);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.slider5,"image1"));
        slideModels.add(new SlideModel(R.drawable.slider4,"image2"));
        slideModels.add(new SlideModel(R.drawable.slider3,"image3"));
        slideModels.add(new SlideModel(R.drawable.slider1,"image4"));
        slideModels.add(new SlideModel(R.drawable.slider2,"image5"));
        imageSlider.setImageList(slideModels,true);





    }
}