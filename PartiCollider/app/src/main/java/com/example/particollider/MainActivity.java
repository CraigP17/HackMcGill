package com.example.particollider;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
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

        // Finds the width and height of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

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

    int size = particles.size();
    /**
     * Checks whether there is a collision between two particles or a particle and a screen wall
     *
     * @param width of the Phone screen canvas
     * @param height of the Phone screen canvas
     */
    void checkCollision(int width, int height) {
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                if (i != j) {
                    if (hasOverlap(particles.get(i), particles.get(j))) {
                        // Collision Occurred
                    }
                }
            }
            // Check if Particle has collided with the Left/Right Wall
            if (touchedLRWall(particles.get(i), width)) {
                // Flip
            }
            // Check if Particle has collided with the Top/Bottom Wall
            if (touchedTBWall(particles.get(i), height)) {
                // Flip
            }
        }
    }

    /**
     * Returns boolean of whether two particles have collided
     *
     * @param p1 A Particle on the screen
     * @param p2 Another Particle on the screen
     * @return whether the Particles p1 and p2 overlap/collide with each other
     */
    boolean hasOverlap(Particle p1, Particle p2) {
        float p1Mass = p1.getMass();
        float p1Left = p1.getxDir() - p1Mass;
        float p1Right = p1.getxDir() + p1Mass;
        float p1Top = p1.getyDir() - p1Mass;
        float p1Bottom = p1.getyDir() + p1Mass;
        float p2Mass = p2.getMass();
        float p2Left = p2.getxDir() - p2Mass;
        float p2Right = p2.getxDir() + p2Mass;
        float p2Top = p2.getyDir() - p2Mass;
        float p2Bottom = p2.getyDir() + p2Mass;

        if (p1Left < p2Left && p2Left < p1Right) {
            return true;
        } else if (p1Left < p2Right && p2Right < p1Right) {
            return true;
        } else if (p1Top < p2Bottom && p2Bottom < p1Bottom) {
            return true;
        } else return p1Top < p2Top && p2Top < p1Bottom;
    }

    /**
     * @param p1 A Particle
     * @param width width of the canvas on the phone
     * @return whether the particle collided with the left or right side wall
     */
    boolean touchedLRWall(Particle p1, int width) {
        float p1Mass = p1.getMass();
        float p1Left = p1.getxDir() - p1Mass;
        float p1Right = p1.getxDir() + p1Mass;
        if (p1Left < 0) {
            return true;
        } else return width < p1Right;
    }

    /**
     * @param p1 a Particle
     * @param height the height of the canvas on the phone
     * @return boolean of whether the particle collided with top or bottom wall
     */
    boolean touchedTBWall(Particle p1, int height) {
        float p1Mass = p1.getMass();
        float p1Top = p1.getyDir() - p1Mass;
        float p1Bottom = p1.getyDir() + p1Mass;
        if (p1Top < 0) {
            return true;
        } else return height < p1Bottom;
    }
}
