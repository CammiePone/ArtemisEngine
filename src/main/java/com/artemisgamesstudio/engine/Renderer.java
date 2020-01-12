package com.artemisgamesstudio.engine;

import com.artemisgamesstudio.engine.common.GameObject;
import com.artemisgamesstudio.engine.render.ShaderProgram;
import com.artemisgamesstudio.engine.render.Window;
import com.artemisgamesstudio.engine.util.Transformation;
import com.artemisgamesstudio.engine.util.Utils;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL15.*;

public class Renderer
{
    public ShaderProgram shaderProgram;
    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;
    private Matrix4f projectionMatrix;
    private Transformation transformation;

    public Renderer()
    {
        transformation = new Transformation();
    }

    public void init(Window window) throws Exception
    {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/shaders/vertex.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/shaders/fragment.fs"));
        shaderProgram.link();

        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("worldMatrix");
        shaderProgram.createUniform("texture_sampler");

        float aspectRatio = (float) window.getWidth() / window.getHeight();
        projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }

    public void render(Window window, GameObject[] gameItems)
    {
        clear();

        if(window.isResized())
        {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderProgram.bind();

        // Update projection Matrix
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);

        // Update texture shiz
        shaderProgram.setUniform("texture_sampler", 0);

        // Render each gameItem
        for(GameObject gameObject : gameItems)
        {
            // Set world matrix for this item
            Matrix4f worldMatrix = transformation.getWorldMatrix(
                            gameObject.getPosition(),
                            gameObject.getRotation(),
                            gameObject.getScale());

            shaderProgram.setUniform("worldMatrix", worldMatrix);

            // Render the mes for this game item
            gameObject.getMesh().render();
        }

        shaderProgram.unbind();
    }

    public void cleanup()
    {
        if(shaderProgram != null)
        {
            shaderProgram.cleanup();
        }
    }

    public void clear()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}
