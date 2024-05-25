package pongolo;

import lwjglutils.OGLTextRenderer;
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
    private final float radius = 0.7f; // Radius of a circle
    private final float gap = 0.05f; // Small gap between the paddle and the circle
    private int score = 0;
    private boolean gameOver = false;
    private boolean showHelp = false;

    /**
     * Constructor for Renderer.
     * Initializes the game components and sets up callbacks.
     */
    public Renderer() {
        super();
        initCallBacks();
        ball = new Ball();
        paddle = new Paddle();
    }

    /**
     * Initializes the GLFW callbacks for window size, cursor position, scroll, and key inputs.
     */
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
                double dy = centerY - y;
                paddle.setAngle((float) Math.atan2(dy, dx));
            }
        };

        glfwScrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double dx, double dy) {
                // Do nothing
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
                        case GLFW_KEY_H:
                            showHelp = !showHelp;
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

    /**
     * Initializes the OpenGL settings.
     * Sets the clear color and enables depth testing.
     */
    @Override
    public void init() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glEnable(GL_DEPTH_TEST);
        textRenderer = new OGLTextRenderer(width, height);
        textRenderer.setScale(2);
    }

    /**
     * The main display method for rendering the game.
     * Clears the screen, updates the game state, and draws the game elements.
     */
    @Override
    public void display() {
        if (showHelp) {
            drawHelpMessage();
            return;
        }

        glViewport(0, 0, width, height);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if (gameOver) {
            drawGameOverMessage();
            return;
        }

        paddle.update();
        ball.update();

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

        textRenderer.addStr2D(3, 30, "Score: " + score);
        textRenderer.addStr2D(800, 790, "Press H for help");
    }

    /**
     * Draws the help message on the screen.
     */
    private void drawHelpMessage() {
        glViewport(0, 0, width, height);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        textRenderer.addStr2D(350, 300, "Kruhový Pong");
        textRenderer.addStr2D(350, 350, "Author: Martin Hanuš");
        textRenderer.addStr2D(350, 400, "KPGR2, 25.05.2024");
        textRenderer.addStr2D(350, 450, "Controls: A, D | Left, Right | Mouse");
        textRenderer.addStr2D(350, 500, "Press H to hide this help message");
    }

    /**
     * Draws the game over message on the screen.
     */
    private void drawGameOverMessage() {
        textRenderer.addStr2D((int) (width / 2.175), height / 2, "Score: " + score);
        textRenderer.addStr2D((int) (width / 2.45), (int) (height / 1.75), "Press R to restart");
    }

    /**
     * Resets the game to its initial state.
     * Resets the ball, paddle, score, and game over flag.
     */
    private void reset() {
        ball = new Ball();
        paddle = new Paddle();
        score = 0;
        gameOver = false;
    }

    /**
     * Reflects the ball off the paddle, increases the speed of both the ball and the paddle, and increments the score.
     */
    private void reflectBall() {
        ball.reflect();
        ball.increaseSpeed();
        paddle.increaseSpeed();
        score++;
    }

    /**
     * Draws the circular boundary of the game.
     */
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

    /**
     * Draws the ball on the screen.
     */
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

    /**
     * Draws the paddle on the screen.
     */
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
