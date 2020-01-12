package com.artemisgamesstudio.engine;

import com.artemisgamesstudio.engine.common.GameObject;
import com.artemisgamesstudio.engine.render.Mesh;
import com.artemisgamesstudio.engine.render.Texture;
import com.artemisgamesstudio.engine.render.Window;
import com.artemisgamesstudio.engine.util.interfaces.IGameLogic;
import org.joml.Vector3f;

public class DummyGame implements IGameLogic
{
    private int displxInc = 0;
    private int displyInc = 0;
    private int displzInc = 0;
    private int scaleInc = 0;
    private final Renderer renderer;
    private GameObject[] gameObjects;

    public DummyGame()
    {
        renderer = new Renderer();
    }

    @Override
    public void init(Window window) throws Exception
    {
        renderer.init(window);

        float[] positions = new float[]
        {
                // VO
                -0.5f,  0.5f,  0.5f,
                // V1
                -0.5f, -0.5f,  0.5f,
                // V2
                0.5f, -0.5f,  0.5f,
                // V3
                0.5f,  0.5f,  0.5f,
                // V4
                -0.5f,  0.5f, -0.5f,
                // V5
                0.5f,  0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
                // V7
                0.5f, -0.5f, -0.5f,
        };

        float[] textCoords = new float[]
        {
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,

                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,

                // For text coords in top face
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,

                // For text coords in right face
                0.0f, 0.0f,
                0.0f, 0.5f,

                // For text coords in left face
                0.5f, 0.0f,
                0.5f, 0.5f,

                // For text coords in bottom face
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
        };

        int[] indices = new int[]
        {
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                4, 0, 3, 5, 4, 3,
                // Right face
                3, 2, 7, 5, 3, 7,
                // Left face
                6, 1, 0, 6, 0, 4,
                // Bottom face
                2, 1, 6, 2, 6, 7,
                // Back face
                7, 6, 4, 7, 4, 5,
        };

        Texture texture = new Texture("grassblock.png");
        Mesh mesh = new Mesh(positions, textCoords, indices, texture);
        GameObject gameObject = new GameObject(mesh);
        gameObject.setPosition(0, 0, -2);

        gameObjects = new GameObject[]
        {
                gameObject
        };
    }

    @Override
    public void input(Window window)
    {

    }

    @Override
    public void update(float interval)
    {
        for(GameObject gameObject : gameObjects)
        {
            // Update position
            Vector3f itemPos = gameObject.getPosition();
            float posx = itemPos.x + displxInc * 0.01f;
            float posy = itemPos.y + displyInc * 0.01f;
            float posz = itemPos.z + displzInc * 0.01f;
            gameObject.setPosition(posx, posy, posz);

            // Update scale
            float scale = gameObject.getScale();
            scale += scaleInc * 0.05f;

            if(scale < 0)
            {
                scale = 0;
            }

            gameObject.setScale(scale);

            // Update rotation angle
            float rotation = gameObject.getRotation().x + 1.5f;

            if(rotation > 360)
            {
                rotation = 0;
            }

            gameObject.setRotation(rotation, rotation, rotation);
        }
    }

    @Override
    public void render(Window window)
    {
        renderer.render(window, gameObjects);
    }

    @Override
    public void cleanup()
    {
        renderer.cleanup();
    }
}