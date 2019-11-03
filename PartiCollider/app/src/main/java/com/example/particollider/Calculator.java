package com.example.particollider;



public class Calculator {

    // This class contains the methods for calculating the collisions
    Calculator(){

    }

    public void finalVel(Particle p1, Particle p2){ //final velocity calculator
        //variables
        float p1m=p1.getMass();
        float p2m=p2.getMass();
        float p1vx=p1.getxDir();
        float p1vy=p1.getyDir();
        float p2vx=p2.getxDir();
        float p2vy=p2.getyDir();
        float p1px=p1.getxPos();
        float p1py=p1.getyPos();
        float p2px=p2.getxPos();
        float p2py=p2.getyPos();
        float p1r=p1.getRadius();
        float p2r=p2.getRadius();
        float p1vxf, p1vyf, p2vxf, p2vyf;

        if (p1.isElastic() || p2.isElastic()){ //checking if a particle is inelastic
            p1vxf=(p1m*p1vx+p2m*p2vx)/(p1m+p2m); //final x velocity of the combined particles
            p1vyf=(p1m*p1vy+p2m*p2vy)/(p1m+p2m); //final y velocity of the combined particles
            //setting the final velocities of the two particles to the same value
            p2vxf=p1vxf;
            p2vyf=p1vyf;
        }else{ //if collision is elastic
            //finding value for (x1-x2) squared
            double e1 = Math.pow((p1px-p2px),2);
            double f1 = Math.pow((p2px-p1px),2);
            float e = (float)e1;
            float f = (float)f1;

            p1vxf=p1vx-((2*p2m)/(p1m+p2m))*(((p1vx-p2vx)*(p1px-p2px))/(e))*(p1px-p2px); //particle 1 final x velocity
            p1vyf=p1vy-((2*p2m)/(p1m+p2m))*(((p1vy-p2vy)*(p1py-p2py))/(e))*(p1py-p2py); //particle 1 final y velocity
            p2vxf=p2vx-((2*p1m)/(p1m+p2m))*(((p2vx-p1vx)*(p2px-p1px))/(f))*(p2px-p1px); //particle 2 final x velocity
            p2vyf=p2vy-((2*p1m)/(p1m+p2m))*(((p2vy-p1vy)*(p2py-p1py))/(f))*(p2py-p1py); //particle 2 final y velocity
        }
    }

    public void sideWall(Particle p1){ //particle colliding with left or right wall
        //switch the direction of the x velocity
        float vx=p1.getxDir();
        p1.setxDir(-vx);
    }

    public void topBotWall(Particle p1){ //particle colliding with top or bottom
        //switch the direction of the y velocity
        float vy=p1.getyDir();
        p1.setyDir(-vy);
    }
}
