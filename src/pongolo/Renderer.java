package pongolo;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import pongolo.global.AbstractRenderer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Renderer extends AbstractRenderer {

    private Ball ball;
    private Paddle paddle;
    private final float radius = 0.7f;
    private final float gap = 0.05f; // Small gap between the paddle and the circle
    private int score = 0;
    private boolean gameOver = false;

    public Renderer() {
        super();
        ball = new Ball();
        paddle = new Paddle();
        initCallBacks();
    }

    private void initCallBacks() {
        glfwWindowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                if (w > 0 && h > 0) {
                    width = w;
                    height = h;
                }
            }
        };

        glfwMouseButtonCallback = null;

        glfwCursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double x, double y) {
                double centerX = width / 2.0;
                double centerY = height / 2.0;
                double dx = x - centerX;
                double dy = centerY - y; // Inverted Y coordinate for correct direction
                paddle.setAngle((float) Math.atan2(dy, dx));
            }
        };


        glfwScrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double dx, double dy) {
                //do nothing
            }
        };

        glfwKeyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (action == GLFW_PRESS) {
                    switch (key) {
                        case GLFW_KEY_LEFT, GLFW_KEY_A:
                            paddle.moveLeft();
                            break;
                        case GLFW_KEY_RIGHT, GLFW_KEY_D:
                            paddle.moveRight();
                            break;
                        case GLFW_KEY_R:
                            reset();
                            break;
                        default:
                            break;
                    }
                } else if (action == GLFW_RELEASE) {
                    paddle.stop();
                }
            }
        };
    }

    @Override
    public void init() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glEnable(GL_DEPTH_TEST);
    }

    @Override
    public void display() {
        glViewport(0, 0, width, height);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if (gameOver) return;

        paddle.updateAngle();
        ball.updatePosition();

        float angleToBall = ball.getAngleToBall();

        float paddleAngleStart = paddle.getAngle();
        float paddleAngleEnd = (paddleAngleStart + paddle.getWidth()) % (2 * (float) Math.PI);

        boolean withinPaddle;
        if (paddleAngleStart < paddleAngleEnd) {
            withinPaddle = angleToBall > paddleAngleStart && angleToBall < paddleAngleEnd;
        } else {
            withinPaddle = angleToBall > paddleAngleStart || angleToBall < paddleAngleEnd;
        }
        
        float distanceFromCenter = ball.getDistanceFromCenter();

        if (withinPaddle && distanceFromCenter >= radius - gap - 0.05f && distanceFromCenter <= radius - gap) {
            reflectBall();
        }

        if (distanceFromCenter >= radius - gap / 2) {
            gameOver = true;
        }

        // Draw the circular boundary
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        drawCircle();

        // Draw ball
        glLoadIdentity();
        drawBall();

        // Draw paddle
        glLoadIdentity();
        drawPaddle();
    }

    private void reset() {
        ball = new Ball();
        paddle = new Paddle();
        score = 0;
        gameOver = false;
    }

    private void reflectBall() {
        ball.reflect();
        ball.increaseSpeed();
        paddle.increaseSpeed();
        score++;
    }

    private void drawCircle() {
        glBegin(GL_LINE_LOOP);
        glColor3f(1f, 1f, 1f);
        for (int i = 0; i < 100; i++) {
            double theta = 2.0f * Math.PI * i / 100;
            float x = (float) Math.cos(theta) * radius;
            float y = (float) Math.sin(theta) * radius;
            glVertex2f(x, y);
        }
        glEnd();
    }

    private void drawBall() {
        glBegin(GL_TRIANGLE_FAN);
        glColor3f(1f, 1f, 1f);
        glVertex2f(ball.getX(), ball.getY()); // Center of circle
        for (int i = 0; i <= 20; i++) {
            double angle = 2.0f * Math.PI * i / 20;
            float dx = (float) Math.cos(angle) * ball.getRadius();
            float dy = (float) Math.sin(angle) * ball.getRadius();
            glVertex2f(ball.getX() + dx, ball.getY() + dy);
        }
        glEnd();
    }

    private void drawPaddle() {
        float startAngle = paddle.getAngle();
        float endAngle = paddle.getAngle() + paddle.getWidth();
        float innerRadius = radius - gap - 0.035f;
        float outerRadius = radius - gap;

        glBegin(GL_QUAD_STRIP);
        glColor3f(1f, 1f, 1f);
        for (int i = 0; i <= 5; i++) {
            float angle = startAngle + (endAngle - startAngle) * i / 5;
            float xInner = (float) Math.cos(angle) * innerRadius;
            float yInner = (float) Math.sin(angle) * innerRadius;
            float xOuter = (float) Math.cos(angle) * outerRadius;
            float yOuter = (float) Math.sin(angle) * outerRadius;
            glVertex2f(xInner, yInner);
            glVertex2f(xOuter, yOuter);
        }
        glEnd();
    }
}