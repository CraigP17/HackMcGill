package com.example.particollider;

public class Particle {

    /** Mass of the Particle, Unit: kg */
    private float mass;

    /** Velocity of the Particle, Unit: m/s */
    private float velocity;

    /** Colour of the Particle */
    private String color;

    /** Radius of the Particle, Unit: m */
    private float radius;

    /** Direction x of the Particle */
    private float xDir;

    /** Direction y of the Particle */
    private float yDir;

    /** Position x of the Particle */
    private float xPos;

    /** Position y of the Particle */
    private float yPos;

    /** Whether the Particle is Elastic */
    private boolean elastic;

    /**
     * @param mass Mass of the Particle (kg)
     * @param velocity Velocity of the Particle (m/s)
     * @param color Color of the Particle
     * @param radius Radius of the Particle (m)
     * @param xDir Direction x
     * @param yDir Direction y
     * @param xPos Position x
     * @param yPos Position y
     * @param elastic if Particle is elastic
     */
    Particle(float mass, float velocity, String color, float radius, float xDir, float yDir,
             float xPos, float yPos, boolean elastic) {
        this.mass = mass;
        this.velocity = velocity;
        this.color = color;
        this.radius = radius;
        this.xDir = xDir;
        this.yDir = yDir;
        this.xPos = xPos;
        this.yPos = yPos;
        this.elastic = elastic;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getxDir() {
        return xDir;
    }

    public void setxDir(float xDir) {
        this.xDir = xDir;
    }

    public float getyDir() {
        return yDir;
    }

    public void setyDir(float yDir) {
        this.yDir = yDir;
    }

    public float getxPos() {
        return xPos;
    }

    public void setxPos(float xPos) {
        this.xPos = xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public void setyPos(float yPos) {
        this.yPos = yPos;
    }

    public boolean isElastic() {
        return elastic;
    }

    public void setElastic(boolean elastic) {
        this.elastic = elastic;
    }
}
