package com.example.particollider;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import java.util.ArrayList;

import static android.os.SystemClock.elapsedRealtime;

public class MainActivity extends AppCompatActivity {
    private ImageView particle1;
    private ImageView particle2;
    private ImageView particle3;
    private ImageView particle4;
    private ImageView particle5;


    Particle part1 = new Particle(1,1,1,1,1,1,1,1,true);
    Particle part2 = new Particle(2,2,2, 2, -2, 2, 2,2, true);
    Particle part3 = new Particle(3,1,3, 2, -2, 2, 3,4, true);
    ArrayList<Particle> particles = new ArrayList<>();

    Calculator calc = new Calculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        particle1 = findViewById(R.id.particle);
        particle2 = findViewById(R.id.particle2);
        particle3 = findViewById(R.id.particle3);
        particle4 = findViewById(R.id.particle4);
        particle5 = findViewById(R.id.particle5);

        // Finds the width and height of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        particles.add(part1);
        particles.add(part2);
        particles.add(part3);
        updateVisible();

    }


    public void move(View view) {
        helper();
    }

    private void helper(){
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator anim = null;
        ObjectAnimator anim1 = null;
        ObjectAnimator anim2 = null;
        ObjectAnimator anim3 = null;
        ObjectAnimator anim4 = null;

        for (Particle p : particles) {

            Path path1 = new Path();
            System.out.println("IN LOOPS");
            path1.setLastPoint(p.getxPos(), p.getyPos());

            path1.lineTo(p.getxPos() + p.getxDir(), p.getyPos() + p.getyDir());

            if (p.getPart() == 1) {
                anim = ObjectAnimator.ofFloat(particle1,
                        "translationX", "translationY", path1);
                long temp = (long) (1000 / p.getVelocity());
                anim.setDuration(temp);
                //anim.start();
                System.out.println("MOVE ONE");
            } else if (p.getPart() == 2) {
                anim1 = ObjectAnimator.ofFloat(particle2,
                        "translationX", "translationY", path1);
                long temp = (long) (1000 / p.getVelocity());
                anim1.setDuration(temp);
                //anim1.start();
            } else if (p.getPart() == 3) {
                anim2 = ObjectAnimator.ofFloat(particle3,
                        "translationX", "translationY", path1);
                long temp = (long) (1000 / p.getVelocity());
                anim2.setDuration(temp);
                //anim2.start();
            } else if (p.getPart() == 4) {
                anim3 = ObjectAnimator.ofFloat(particle4,
                        "translationX", "translationY", path1);
                long temp = (long) (1000 / p.getVelocity());
                anim3.setDuration(temp);
                //anim3.start();
            } else if (p.getPart() == 5) {
                anim4 = ObjectAnimator.ofFloat(particle4,
                        "translationX", "translationY", path1);
                long temp = (long) (1000 / p.getVelocity());
                anim4.setDuration(temp);
                //anim.start();
            }

            p.setxPos(p.getxPos() + p.getxDir());
            p.setyPos(p.getyPos() + p.getyDir());


        }
        set.play(anim).with(anim1).with(anim2).after(0);
        set.start();
    }

    private void updateVisible(){
        int numParticles = particles.size();
        for(int i = 1; i <= numParticles; i++){
            if(i ==1){
                particle1.setVisibility(View.VISIBLE);
            }
            if(i ==2){
                particle2.setVisibility(View.VISIBLE);
            }
            if(i ==3){
                particle3.setVisibility(View.VISIBLE);
            }
            if(i ==4){
                particle4.setVisibility(View.VISIBLE);
            }
            if(i ==5){
                particle5.setVisibility(View.VISIBLE);
            }
        }
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
                        calc.finalVel(particles.get(i), particles.get(j));
                    }
                }
            }
            // Check if Particle has collided with the Left/Right Wall
            if (touchedLRWall(particles.get(i), width)) {
                calc.collideLR(particles.get(i));
            }
            // Check if Particle has collided with the Top/Bottom Wall
            if (touchedTBWall(particles.get(i), height)) {
                calc.collideTopBot(particles.get(i));
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
