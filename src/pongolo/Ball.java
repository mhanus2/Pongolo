package pongolo;

import java.util.Random;

public class Ball {
    private float x, y;
    private float dirX, dirY;
    private float speed;

    /**
     * Constructor for Ball.
     * Initializes the ball at a random position and direction with a default speed.
     */
    public Ball() {
        Random random = new Random();
        this.x = 0.1f * (random.nextBoolean() ? 1 : -1);
        this.y = -0.15f * (random.nextBoolean() ? 1 : -1);
        this.dirX = random.nextBoolean() ? -0.01f : 0.01f;
        this.dirY = random.nextBoolean() ? -0.01f : 0.01f;
        this.speed = 0.2f;
    }

    /**
     * Updates the position of the ball based on its direction and speed.
     */
    public void update() {
        x += dirX * speed;
        y += dirY * speed;
    }

    /**
     * Reflects the ball's direction based on its angle to the center.
     */
    public void reflect() {
        float angleToBall = getAngleToBall();
        float normalX = (float) Math.cos(angleToBall);
        float normalY = (float) Math.sin(angleToBall);
        float dotProduct = dirX * normalX + dirY * normalY;
        dirX -= 2 * dotProduct * normalX;
        dirY -= 2 * dotProduct * normalY;
    }

    /**
     * Gets the current x-coordinate of the ball.
     *
     * @return The x-coordinate.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the current y-coordinate of the ball.
     *
     * @return The y-coordinate.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the radius of the ball.
     *
     * @return The radius.
     */
    public float getRadius() {
        return 0.03f;
    }

    /**
     * Increases the speed of the ball by a fixed increment.
     */
    public void increaseSpeed() {
        speed += 0.075f;
    }

    /**
     * Gets the distance of the ball from the center.
     *
     * @return The distance from the center.
     */
    public float getDistanceFromCenter() {
        return (float) Math.sqrt((x * x) + (y * y));
    }

    /**
     * Gets the angle from the center to the ball.
     *
     * @return The angle to the ball in radians.
     */
    public float getAngleToBall() {
        float angleToBall = (float) Math.atan2(y, x);
        if (angleToBall < 0) angleToBall += (float) (2 * Math.PI);
        return angleToBall;
    }
}
