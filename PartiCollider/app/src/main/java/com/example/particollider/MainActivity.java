package com.example.particollider;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView particle;
    private int x = 2;
    private int y = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        particle = findViewById(R.id.particle);
        particle.setImageResource(R.drawable.particle);

    }

    public void moveRight(View view){
        Path p = new Path();
        p.setLastPoint(x-2,y-2);
        p.lineTo(x,y);
        x+=2;
        y+=2;
        ObjectAnimator anim = ObjectAnimator.ofFloat(particle,
                "translationX", "translationY", p);
        anim.setDuration(200);
        anim.start();
    }

    public void moveBack(View view){
        Path p = new Path();
        p.lineTo(x + 100,y + 100);
        x += 100;
        y += 100;
        ObjectAnimator anim = ObjectAnimator.ofFloat(particle,
                "translationX", "translationY", p);
        anim.setDuration(3000);
        anim.start();
    }
}
