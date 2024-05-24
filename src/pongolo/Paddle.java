package pongolo;

public class Paddle {
    private float angle;
    private float rotationSpeed;
    private static final float WIDTH = (float) Math.PI / 8;
    private static final float MAX_ROTATION_SPEED = 0.02f;

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

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public static float getWidth() {
        return WIDTH;
    }

    public static float getMaxRotationSpeed() {
        return MAX_ROTATION_SPEED;
    }
}
