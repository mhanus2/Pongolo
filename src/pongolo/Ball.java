package pongolo;

public class Ball {
    private float x, y;
    private float dirX, dirY;
    private float speed;
    private final float radius = 0.03f;
    private final float speedIncrement = 0.075f;

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

    public void reflect() {
        float angleToBall = getAngleToBall();
        float normalX = (float) Math.cos(angleToBall);
        float normalY = (float) Math.sin(angleToBall);
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

    public void increaseSpeed() {
        speed += speedIncrement;
    }

    public float getSpeed() {
        return speed;
    }

    public float getDistanceFromCenter() {
        return (float) Math.sqrt((x * x) + (y * y));
    }

    public float getAngleToBall() {
        float angleToBall = (float) Math.atan2(y, x);
        if (angleToBall < 0) angleToBall += (float) (2 * Math.PI);
        return angleToBall;
    }
}
