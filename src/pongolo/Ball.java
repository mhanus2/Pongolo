package pongolo;

public class Ball {
    private float x, y;
    private float dirX, dirY;
    private float speed;
    private final float radius = 0.03f;

    public Ball(float x, float y, float dirX, float dirY, float speed) {
        this.x = x;
        this.y = y;
        this.dirX = dirX;
        this.dirY = dirY;
        this.speed = speed;
    }

    public void updatePosition() {
        x += dirX * speed;
        y += dirY * speed;
    }

    public void reflect(float normalX, float normalY) {
        float dotProduct = dirX * normalX + dirY * normalY;
        dirX -= 2 * dotProduct * normalX;
        dirY -= 2 * dotProduct * normalY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public void increaseSpeed(float increment) {
        speed += increment;
    }

    public float getSpeed() {
        return speed;
    }
}
