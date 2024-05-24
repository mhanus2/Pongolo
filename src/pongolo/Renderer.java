package pongolo;

import pongolo.global.AbstractRenderer;

import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glViewport;

public class Renderer extends AbstractRenderer {

    private Ball ball;
    private Paddle paddle;

    public Renderer() {
        super();
        ball = new Ball(-0.1f, -0.25f, 0.01f, 0.01f, 0.2f);
        paddle = new Paddle(0.0f);
    }

    @Override
    public void init() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void display() {
        glViewport(0, 0, width, height);
    }


}