package pongolo;

public class Paddle {
    private float angle;
    private float rotationSpeed;
    private final float width = (float) Math.PI / 8;
    private float maxPaddleRotationSpeed = 0.02f;
    private final float speedIncrement = 0.0025f;

    public Paddle(float initialAngle) {
        this.angle = initialAngle;
        this.rotationSpeed = 0.0f;
    }

    public void updateAngle() {
        angle += rotationSpeed;
        if (angle < 0) angle += (float) (2 * Math.PI);
        if (angle >= 2 * Math.PI) angle -= (float) (2 * Math.PI);
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void moveLeft() {
        this.rotationSpeed = -maxPaddleRotationSpeed;
    }

    public void moveRight() {
        this.rotationSpeed = maxPaddleRotationSpeed;
    }

    public void stop() {
        this.rotationSpeed = 0.0f;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getWidth() {
        return width;
    }

    public void increaseSpeed() {
        maxPaddleRotationSpeed += speedIncrement;
    }
}
