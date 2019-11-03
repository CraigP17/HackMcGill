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
import android.widget.Toast;

import java.util.ArrayList;

import static android.os.SystemClock.elapsedRealtime;

public class MainActivity extends AppCompatActivity {
    private ImageView particle1;
    private ImageView particle2;
    private ImageView particle3;


    public int width;
    public int height;

    public boolean started = true;

    Particle part1 = new Particle(1,1,1,1,10,10,1,1,true);
    Particle part2 = new Particle(2,2,2, 2, -2, 2, 20,20, true);
    Particle part3 = new Particle(3,1,3, 2, -2, 2, 30,40, true);
    ArrayList<Particle> particles = new ArrayList<>();

    Calculator calc = new Calculator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        particle1 = findViewById(R.id.particle);
        particle1.setY(part1.getyPos());
        particle1.setX(part1.getxPos());

        particle2 = findViewById(R.id.particle2);
        particle2.setY(part2.getyPos());
        particle2.setX(part2.getxPos());

        particle3 = findViewById(R.id.particle3);
        particle3.setY(part3.getyPos());
        particle3.setX(part3.getxPos());

        // Finds the width and height of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        particles.add(part1);
        particles.add(part2);
        particles.add(part3);
        updateVisible();

    }


    public void update(View view) {
        System.out.println(part1.getxPos());
        System.out.println(width);
        System.out.println(height);
        //System.out.println(part1.getyPos());

        checkCollision();
        move();
        checkCollision();
    }


    public void move() {
        helper();
    }

    private void helper(){
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator anim = null;
        ObjectAnimator anim1 = null;
        ObjectAnimator anim2 = null;

        for (Particle p : particles) {

            Path path1 = new Path();
            path1.setLastPoint(p.getxPos(), p.getyPos());

            path1.lineTo(p.getxPos() + p.getxDir(), p.getyPos() + p.getyDir());

            if (p.getPart() == 1) {
                anim = ObjectAnimator.ofFloat(particle1,
                        "translationX", "translationY", path1);
                long temp = (long) (1000 / p.getVelocity());
                anim.setDuration(temp);
                //anim.start();
            } else if (p.getPart() == 2) {
                anim1 = ObjectAnimator.ofFloat(particle2,
                        "translationX", "translationY", path1);
                long temp = (long) (1000 / p.getVelocity());
                anim1.setDuration(temp);
                System.out.println(p.getxPos());
                //anim1.start();
            } else if (p.getPart() == 3) {
                anim2 = ObjectAnimator.ofFloat(particle3,
                        "translationX", "translationY", path1);
                long temp = (long) (1000 / p.getVelocity());
                anim2.setDuration(temp);
                //anim2.start();
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
        }
    }



    /**
     * Checks whether there is a collision between two particles or a particle and a screen wall
     *
     */
    void checkCollision() {
        int size = particles.size();
        //System.out.println("HI");
        //System.out.println(size);
        for (int i=0; i<size; i++) {
            //System.out.println(i);
            for (int j=0; j<size; j++) {
                //System.out.println(j);
                if (i != j) {
                    if (hasOverlap(particles.get(i), particles.get(j))) {
                        calc.finalVel(particles.get(i), particles.get(j));
                    }
                }
            }
            Particle partisan = particles.get(i);
            // Check if Particle has collided with the Left/Right Wall
            System.out.println(particles.get(i).getxPos());
            System.out.println(width);
            if (touchedLRWall(partisan, width)) {
                System.out.println("LR");
                calc.collideLR(partisan);
            }
            // Check if Particle has collided with the Top/Bottom Wall
            if (touchedTBWall(particles.get(i), height)) {
                System.out.println("TB");
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
        //System.out.println(String.valueOf(width)+String.valueOf(p1Right));
        if (p1Left < 0) {
            System.out.print("TOUCHED EDGE");
            return true;
        } else if (p1Right > (float) width) {
            System.out.println("Right");
            return true;
        } else {
            return width < p1Right;
        }
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

    public void pause(View view) {
        started = !started;
    }

    public void showVelocities(View view){
        Toast toast=Toast.makeText(getApplicationContext(),"Vp1: "+
                        String.valueOf(part1.getVelocity()) + " xPos" +String.valueOf(part1.getxPos())+
                " yPos:" + String.valueOf(part1.getyPos()) + "\n Vp2: " + String.valueOf(part2.getVelocity()) + " xPos" +String.valueOf(part1.getxPos())+
                " yPos:" + String.valueOf(part1.getyPos())+
                        "\n Vp3 " + String.valueOf(part3.getVelocity()) + " xPos" +String.valueOf(part1.getxPos())+
                " yPos:" + String.valueOf(part1.getyPos()), Toast.LENGTH_LONG);
        toast.show();
    }
}
