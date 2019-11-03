package com.example.particollider;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageView particle;
    private ImageView particle2;
    private int x = 2;
    private int y = 2;
    private int x1 = 200;
    private int y1 = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        particle = findViewById(R.id.particle);
        particle2 = findViewById(R.id.particle2);
        particle.setImageResource(R.drawable.particle);
        particle2.setImageResource(R.drawable.particle);

    }

    public void moveRight(View view){
        Path p = new Path();
        Path p1 = new Path();

        int i = 0;
        while(i<250){
            p.setLastPoint(x-2,y-2);
            p.lineTo(x,y);
            x+=2;
            y+=2;
            ObjectAnimator anim = ObjectAnimator.ofFloat(particle,
                    "translationX", "translationY", p);
            anim.setDuration(500);
            anim.start();

            p1.setLastPoint(x1-2,y1-2);
            p1.lineTo(x1,y1);
            x1-=2;
            y1+=2;
            ObjectAnimator anim2 = ObjectAnimator.ofFloat(particle2,
                    "translationX", "translationY", p1);
            anim2.setDuration(500);
            anim2.start();
            i++;

        }
    }

}
