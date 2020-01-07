package com.artemisgamesstudio.engine.core.kernel;

import com.artemisgamesstudio.engine.core.math.Matrix4F;
import com.artemisgamesstudio.engine.core.math.Quaternion;
import com.artemisgamesstudio.engine.core.math.Vec3F;
import com.artemisgamesstudio.engine.core.utils.Constants;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

public class Camera
{
	private static Camera instance = null;

	private final Vec3F yAxis = new Vec3F(0,1,0);
	
	private Vec3F position;
	private Vec3F previousPosition;
	private Vec3F forward;
	private Vec3F previousForward;
	private Vec3F up;
	private float movAmt = 0.1f;
	private float rotAmt = 0.8f;
	private Matrix4F viewMatrix;
	private Matrix4F projectionMatrix;
	private Matrix4F viewProjectionMatrix;
	private Matrix4F previousViewMatrix;
	private Matrix4F previousViewProjectionMatrix;
	private boolean cameraMoved;
	private boolean cameraRotated;
	
	private float width;
	private float height;
	private float fovY;

	private float rotYstride;
	private float rotYamt;
	private float rotYcounter;
	private boolean rotYInitiated = false;
	private float rotXstride;
	private float rotXamt;
	private float rotXcounter;
	private boolean rotXInitiated = false;
	private float mouseSensitivity = 0.8f;
	
	private Quaternion[] frustumPlanes = new Quaternion[6];
	private Vec3F[] frustumCorners = new Vec3F[8];
	  
	public static Camera getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new Camera();
	    }

	    return instance;
	}
	
	protected Camera()
	{
		this(new Vec3F(-200,20,-20), new Vec3F(1,0,-1).normalize(), new Vec3F(0,1,0));
		setProjection(70, Window.getInstance().getWidth(), Window.getInstance().getHeight());
		setViewMatrix(new Matrix4F().View(this.getForward(), this.getUp()).mul(
				new Matrix4F().Translation(this.getPosition().mul(-1))));
		previousViewMatrix = new Matrix4F().Zero();
		viewProjectionMatrix = new Matrix4F().Zero();
		previousViewProjectionMatrix = new Matrix4F().Zero();
	}
	
	private Camera(Vec3F position, Vec3F forward, Vec3F up)
	{
		setPosition(position);
		setForward(forward);
		setUp(up);
		up.normalize();
		forward.normalize();
	}
	
	public void update()
	{
		setPreviousPosition(new Vec3F(position));
		setPreviousForward(new Vec3F(forward));
		cameraMoved = false;
		cameraRotated = false;
		
		movAmt += (0.04f * Input.getInstance().getScrollOffset());
		movAmt = Math.max(0.02f, movAmt);
		
		if(Input.getInstance().isKeyHold(GLFW_KEY_W))
			move(getForward(), movAmt);

		if(Input.getInstance().isKeyHold(GLFW_KEY_S))
			move(getForward(), -movAmt);

		if(Input.getInstance().isKeyHold(GLFW_KEY_A))
			move(getLeft(), movAmt);

		if(Input.getInstance().isKeyHold(GLFW_KEY_D))
			move(getRight(), movAmt);
				
		if(Input.getInstance().isKeyHold(GLFW_KEY_UP))
			rotateX(-rotAmt / 8f);

		if(Input.getInstance().isKeyHold(GLFW_KEY_DOWN))
			rotateX(rotAmt / 8f);

		if(Input.getInstance().isKeyHold(GLFW_KEY_LEFT))
			rotateY(-rotAmt / 8f);

		if(Input.getInstance().isKeyHold(GLFW_KEY_RIGHT))
			rotateY(rotAmt / 8f);

		
		// free mouse rotation
		if(Input.getInstance().isButtonHolding(2))
		{
			float dy = Input.getInstance().getLockedCursorPosition().getY() - Input.getInstance().getCursorPosition().getY();
			float dx = Input.getInstance().getLockedCursorPosition().getX() - Input.getInstance().getCursorPosition().getX();
			
			// y-axxis rotation
			
			if(dy != 0)
			{
				rotYstride = Math.abs(dy * 0.01f);
				rotYamt = -dy;
				rotYcounter = 0;
				rotYInitiated = true;
			}
			
			if(rotYInitiated )
			{
				// up-rotation
				if(rotYamt < 0)
				{
					if(rotYcounter > rotYamt)
					{
						rotateX(-rotYstride * mouseSensitivity);
						rotYcounter -= rotYstride;
						rotYstride *= 0.98;
					}
					else
						rotYInitiated = false;
				}

				// down-rotation
				else if(rotYamt > 0)
				{
					if(rotYcounter < rotYamt)
					{
						rotateX(rotYstride * mouseSensitivity);
						rotYcounter += rotYstride;
						rotYstride *= 0.98;
					}
					else
						rotYInitiated = false;
				}
			}
			
			// x-axxis rotation
			if(dx != 0)
			{
				rotXstride = Math.abs(dx * 0.01f);
				rotXamt = dx;
				rotXcounter = 0;
				rotXInitiated = true;
			}
			
			if(rotXInitiated)
			{
				
				// up-rotation
				if(rotXamt < 0)
				{
					if(rotXcounter > rotXamt)
					{
						rotateY(rotXstride * mouseSensitivity);
						rotXcounter -= rotXstride;
						rotXstride *= 0.96;
					}
					else
						rotXInitiated = false;
				}

				// down-rotation
				else if(rotXamt > 0)
				{
					if(rotXcounter < rotXamt)
					{
						rotateY(-rotXstride * mouseSensitivity);
						rotXcounter += rotXstride;
						rotXstride *= 0.96;
					}
					else
						rotXInitiated = false;
				}
			}
			
			glfwSetCursorPos(Window.getInstance().getWindow(),
					 Input.getInstance().getLockedCursorPosition().getX(),
					 Input.getInstance().getLockedCursorPosition().getY());
		}
		
		if(!position.equals(previousPosition))
		{
			cameraMoved = true;	
		}
		
		if(!forward.equals(previousForward))
		{
			cameraRotated = true;
		}
		
		setPreviousViewMatrix(viewMatrix);
		setPreviousViewProjectionMatrix(viewProjectionMatrix);
		setViewMatrix(new Matrix4F().View(this.getForward(), this.getUp()).mul(
				new Matrix4F().Translation(this.getPosition().mul(-1))));
		setViewProjectionMatrix(projectionMatrix.mul(viewMatrix));
	}
	
	public void move(Vec3F dir, float amount)
	{
		Vec3F newPos = position.add(dir.mul(amount));
		setPosition(newPos);
	}
	
	public void rotateY(float angle)
	{
		Vec3F hAxis = yAxis.cross(forward).normalize();
		
		forward.rotate(angle, yAxis).normalize();
		
		up = forward.cross(hAxis).normalize();
	}
	
	public void rotateX(float angle)
	{
		Vec3F hAxis = yAxis.cross(forward).normalize();

		forward.rotate(angle, hAxis).normalize();
		
		up = forward.cross(hAxis).normalize();
	}
	
	public Vec3F getLeft()
	{
		Vec3F left = forward.cross(up);
		left.normalize();
		return left;
	}
	
	public Vec3F getRight()
	{
		Vec3F right = up.cross(forward);
		right.normalize();
		return right;
	}

	public Matrix4F getProjectionMatrix()
	{
		return projectionMatrix;
	}

	public void setProjectionMatrix(Matrix4F projectionMatrix)
	{
		this.projectionMatrix = projectionMatrix;
	}
	
	public  void setProjection(float fovY, float width, float height)
	{
		this.fovY = fovY;
		this.width = width;
		this.height = height;
		
		this.projectionMatrix = new Matrix4F().PerspectiveProjection(fovY, width, height, Constants.ZNEAR, Constants.ZFAR);
	}

	public Matrix4F getViewMatrix()
	{
		return viewMatrix;
	}

	public void setViewMatrix(Matrix4F viewMatrix)
	{
		this.viewMatrix = viewMatrix;
	}

	public Vec3F getPosition()
	{
		return position;
	}

	public void setPosition(Vec3F position)
	{
		this.position = position;
	}

	public Vec3F getForward()
	{
		return forward;
	}

	public void setForward(Vec3F forward)
	{
		this.forward = forward;
	}

	public Vec3F getUp()
	{
		return up;
	}

	public void setUp(Vec3F up)
	{
		this.up = up;
	}

	public Quaternion[] getFrustumPlanes()
	{
		return frustumPlanes;
	}
	
	public float getFovY()
	{
		return this.fovY;
	}
	
	public float getWidth()
	{
		return this.width;
	}

	public float getHeight()
	{
		return this.height;
	}
	
	public void setViewProjectionMatrix(Matrix4F viewProjectionMatrix)
	{
		this.viewProjectionMatrix = viewProjectionMatrix;
	}
	
	public Matrix4F getViewProjectionMatrix()
	{
		return viewProjectionMatrix;
	}

	public Matrix4F getPreviousViewProjectionMatrix()
	{
		return previousViewProjectionMatrix;
	}

	public void setPreviousViewProjectionMatrix(Matrix4F previousViewProjectionMatrix)
	{
		this.previousViewProjectionMatrix = previousViewProjectionMatrix;
	}

	public Matrix4F getPreviousViewMatrix()
	{
		return previousViewMatrix;
	}

	public void setPreviousViewMatrix(Matrix4F previousViewMatrix)
	{
		this.previousViewMatrix = previousViewMatrix;
	}

	public Vec3F[] getFrustumCorners()
	{
		return frustumCorners;
	}

	public boolean isCameraMoved()
	{
		return cameraMoved;
	}

	public boolean isCameraRotated()
	{
		return cameraRotated;
	}
	
	public Vec3F getPreviousPosition()
	{
		return previousPosition;
	}

	public void setPreviousPosition(Vec3F previousPosition)
	{
		this.previousPosition = previousPosition;
	}
	
	public Vec3F getPreviousForward()
	{
		return previousForward;
	}

	private void setPreviousForward(Vec3F previousForward)
	{
		this.previousForward = previousForward;
	}
}