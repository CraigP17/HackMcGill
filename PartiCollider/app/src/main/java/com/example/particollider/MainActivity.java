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
    private int x = 2;
    private int y = 2;
    Particle part1 = new Particle(1,1,"red",1,1,1,1,1,true);
    Particle part2 = new Particle(2,2,"green", 2, 2, 2, 2,2, true);
    ArrayList<Particle> particles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        particle = findViewById(R.id.particle);
        particle.setImageResource(R.drawable.particle);
        particles.add(part1);
        particles.add(part2);

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
