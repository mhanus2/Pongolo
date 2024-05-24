package pongolo;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import pongolo.global.AbstractRenderer;

import static org.lwjgl.opengl.GL11.*;

public class Renderer extends AbstractRenderer {

    private Ball ball;
    private Paddle paddle;

    public Renderer() {
        super();
        ball = new Ball(-0.1f, -0.25f, 0.01f, 0.01f, 0.2f);
        paddle = new Paddle(0.0f);
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
                //do nothing
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
                //do nothing
            }
        };
    }

    @Override
    public void init() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void display() {
        glViewport(0, 0, width, height);

        // Draw the circular boundary
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        drawCircle();
    }

    private void drawCircle() {
        glBegin(GL_LINE_LOOP);
        glColor3f(1f, 1f, 1f);
        for (int i = 0; i < 100; i++) {
            double theta = 2.0f * Math.PI * i / 100;
            float x = (float) Math.cos(theta) * 0.7f;
            float y = (float) Math.sin(theta) * 0.7f;
            glVertex2f(x, y);
        }
        glEnd();
    }
}