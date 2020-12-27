package com.ecom.khawadawa_main;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ProgressBar pb;
    int counter = 0;
    Animation topAnimation,middleAnimation;
    View line1,line2,line3,line4,line5,line6;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        middleAnimation = AnimationUtils.loadAnimation(this,R.anim.middle_animation);

        //hooks
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        line4 = findViewById(R.id.line4);
        line5 = findViewById(R.id.line5);
        line6 = findViewById(R.id.line6);

        tv = findViewById(R.id.logo);

        line1.setAnimation(topAnimation);
        line2.setAnimation(topAnimation);
        line3.setAnimation(topAnimation);
        line4.setAnimation(topAnimation);
        line5.setAnimation(topAnimation);
        line6.setAnimation(topAnimation);

        tv.setAnimation(middleAnimation);

        prog();
    }

    public void prog(){
        pb = findViewById(R.id.pb);
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter+=5;
                pb.setProgress(counter);

                if(counter==100){
                    Intent intent = new Intent(MainActivity.this,SignIn.class);
                    startActivity(intent);
                    finish();
                    t.cancel();
                }
            }
        };

        t.schedule(tt,0,100);
    }
}