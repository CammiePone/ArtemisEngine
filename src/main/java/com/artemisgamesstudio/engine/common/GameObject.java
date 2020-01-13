package com.artemisgamesstudio.engine.common;

import com.artemisgamesstudio.engine.core.render.Mesh;
import com.artemisgamesstudio.engine.core.render.OBJLoader;
import com.artemisgamesstudio.engine.core.render.Texture;
import org.joml.Vector3f;

public class GameObject
{
    private Mesh mesh;
    private final Vector3f position;
    private float scale;
    private final Vector3f rotation;

    public GameObject(String string)
    {
        this.mesh = null;

        try
        {
            this.mesh = OBJLoader.loadMesh(string + ".obj");
            Texture texture = new Texture(string + ".png");
            mesh.setTexture(texture);
        }
        catch(Exception e)
        {

        }

        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
    }

    public Vector3f getPosition()
    {
        return position;
    }

    public void setPosition(float x, float y, float z)
    {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public float getScale()
    {
        return scale;
    }

    public void setScale(float scale)
    {
        this.scale = scale;
    }

    public Vector3f getRotation()
    {
        return rotation;
    }

    public void setRotation(float x, float y, float z)
    {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public Mesh getMesh()
    {
        return mesh;
    }
}
