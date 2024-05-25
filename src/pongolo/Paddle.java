package pongolo;

import java.util.Random;

public class Paddle {
    private float angle;
    private float rotationSpeed;
    private float maxPaddleRotationSpeed = 0.02f;

    /**
     * Constructor for Paddle.
     * Initializes the paddle with a random angle and zero rotation speed.
     */
    public Paddle() {
        Random random = new Random();
        this.angle = random.nextFloat() * (float) (2 * Math.PI);
        this.rotationSpeed = 0.0f;
    }

    /**
     * Updates the paddle's angle based on its rotation speed.
     * Ensures the angle stays within the range [0, 2*PI].
     */
    public void update() {
        angle += rotationSpeed;
        if (angle < 0) angle += (float) (2 * Math.PI);
        if (angle >= 2 * Math.PI) angle -= (float) (2 * Math.PI);
    }

    /**
     * Sets the paddle's rotation speed to move left.
     */
    public void moveLeft() {
        this.rotationSpeed = -maxPaddleRotationSpeed;
    }

    /**
     * Sets the paddle's rotation speed to move right.
     */
    public void moveRight() {
        this.rotationSpeed = maxPaddleRotationSpeed;
    }

    /**
     * Stops the paddle's rotation by setting the rotation speed to zero.
     */
    public void stop() {
        this.rotationSpeed = 0.0f;
    }

    /**
     * Gets the current angle of the paddle.
     *
     * @return The current angle of the paddle.
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Sets the angle of the paddle.
     *
     * @param angle The new angle to set.
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }

    /**
     * Gets the width of the paddle.
     *
     * @return The width of the paddle.
     */
    public float getWidth() {
        return (float) Math.PI / 8;
    }

    /**
     * Increases the maximum paddle rotation speed by a fixed increment.
     */
    public void increaseSpeed() {
        float speedIncrement = 0.0025f;
        maxPaddleRotationSpeed += speedIncrement;
    }
}
