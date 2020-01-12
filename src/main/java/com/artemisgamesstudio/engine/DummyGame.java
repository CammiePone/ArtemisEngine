package com.artemisgamesstudio.engine;

import com.artemisgamesstudio.engine.common.GameObject;
import com.artemisgamesstudio.engine.core.render.Mesh;
import com.artemisgamesstudio.engine.core.render.OBJLoader;
import com.artemisgamesstudio.engine.core.render.Renderer;
import com.artemisgamesstudio.engine.core.render.Texture;
import com.artemisgamesstudio.engine.core.io.Window;
import com.artemisgamesstudio.engine.core.io.Camera;
import com.artemisgamesstudio.engine.core.io.MouseInput;
import com.artemisgamesstudio.engine.core.util.interfaces.IGameLogic;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class DummyGame implements IGameLogic
{
    private static final float MOUSE_SENSITIVITY = 0.3f;
    private final Vector3f cameraInc;
    private final Renderer renderer;
    private GameObject[] gameObjects;
    private Camera camera;
    private static final float CAMERA_POS_STEP = 0.05f;

    public DummyGame()
    {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f();
    }

    @Override
    public void init(Window window) throws Exception
    {
        renderer.init(window);

        Mesh mesh = OBJLoader.loadMesh("cube.obj");
        Texture texture = new Texture("grassblock.png");
        mesh.setTexture(texture);

        GameObject gameObject1 = new GameObject(mesh);
        gameObject1.setScale(0.5f);
        gameObject1.setRotation(0, 270, 0);
        gameObject1.setPosition(0, 0, -2);

        GameObject gameObject2 = new GameObject(mesh);
        gameObject2.setScale(0.5f);
        gameObject2.setRotation(0, 270, 0);
        gameObject2.setPosition(2, 0, -2);

        GameObject gameObject3 = new GameObject(mesh);
        gameObject3.setScale(0.5f);
        gameObject3.setRotation(0, 270, 0);
        gameObject3.setPosition(-2, 0, -2);

        GameObject gameObject4 = new GameObject(mesh);
        gameObject4.setScale(0.5f);
        gameObject4.setRotation(0, 270, 0);
        gameObject4.setPosition(0, 0, -4);

        GameObject gameObject5 = new GameObject(mesh);
        gameObject5.setScale(0.5f);
        gameObject5.setRotation(0, 270, 0);
        gameObject5.setPosition(2, 0, -4);

        GameObject gameObject6 = new GameObject(mesh);
        gameObject6.setScale(0.5f);
        gameObject6.setRotation(0, 270, 0);
        gameObject6.setPosition(-2, 0, -4);

        gameObjects = new GameObject[]
        {
                gameObject1, gameObject2, gameObject3, gameObject4, gameObject5, gameObject6
        };
    }

    @Override
    public void input(Window window, MouseInput mouseInput)
    {
        cameraInc.set(0, 0, 0);

        if(window.isKeyPressed(GLFW_KEY_W))
        {
            cameraInc.z = -1;
        }
        else if(window.isKeyPressed(GLFW_KEY_S))
        {
            cameraInc.z = 1;
        }

        if(window.isKeyPressed(GLFW_KEY_A))
        {
            cameraInc.x = -1;
        }
        else if(window.isKeyPressed(GLFW_KEY_D))
        {
            cameraInc.x = 1;
        }

        if(window.isKeyPressed(GLFW_KEY_SPACE))
        {
            cameraInc.y = 1;
        }
        else if(window.isKeyPressed(GLFW_KEY_LEFT_SHIFT))
        {
            cameraInc.y = -1;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput)
    {
        // Update camera position
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

        // Update camera based on mouse
        if(mouseInput.isRightButtonPressed())
        {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }
    }

    @Override
    public void render(Window window)
    {
        renderer.render(camera, window, gameObjects);
    }

    @Override
    public void cleanup()
    {
        renderer.cleanup();

        for(GameObject gameObject : gameObjects)
        {
            gameObject.getMesh().cleanUp();
        }
    }
}
