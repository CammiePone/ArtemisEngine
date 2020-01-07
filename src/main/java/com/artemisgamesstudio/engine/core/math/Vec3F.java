package com.artemisgamesstudio.engine.core.math;


public class Vec3F
{
	private float X;
	private float Y;
	private float Z;
	
	public Vec3F()
	{
		this.setX(0);
		this.setY(0);
		this.setZ(0);
	}
	
	public Vec3F(float x, float y, float z)
	{
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}
	
	public Vec3F(Vec3F v)
	{
		this.X = v.getX();
		this.Y = v.getY();
		this.Z = v.getZ();
	}
	
	public float length()
	{
		return (float) Math.sqrt(X*X + Y*Y + Z*Z);
	}
	
	public float dot(Vec3F r)
	{
		return X * r.getX() + Y * r.getY() + Z * r.getZ();
	}
	
	public Vec3F cross(Vec3F r)
	{
		float x = Y * r.getZ() - Z * r.getY();
		float y = Z * r.getX() - X * r.getZ();
		float z = X * r.getY() - Y * r.getX();
		
		return new Vec3F(x,y,z);
	}
	
	public Vec3F normalize()
	{
		float length = this.length();
		
		X /= length;
		Y /= length;
		Z /= length;
		
		return this;
	}
	
	public Vec3F rotate(float angle, Vec3F axis)
	{
		float sinHalfAngle = (float)Math.sin(Math.toRadians(angle / 2));
		float cosHalfAngle = (float)Math.cos(Math.toRadians(angle / 2));
		
		float rX = axis.getX() * sinHalfAngle;
		float rY = axis.getY() * sinHalfAngle;
		float rZ = axis.getZ() * sinHalfAngle;
		float rW = cosHalfAngle;
		
		Quaternion rotation = new Quaternion(rX, rY, rZ, rW);
		Quaternion conjugate = rotation.conjugate();
		
		Quaternion w = rotation.mul(this).mul(conjugate);
		
		X = w.getX();
		Y = w.getY();
		Z = w.getZ();
		
		return this;
	}
	
	public Vec3F add(Vec3F r)
	{
		return new Vec3F(this.X + r.getX(), this.Y + r.getY(), this.Z + r.getZ());
	}
	
	public Vec3F add(float r)
	{
		return new Vec3F(this.X + r, this.Y + r, this.Z + r);
	}
	
	public Vec3F sub(Vec3F r)
	{
		return new Vec3F(this.X - r.getX(), this.Y - r.getY(), this.Z - r.getZ());
	}
	
	public Vec3F sub(float r)
	{
		return new Vec3F(this.X - r, this.Y - r, this.Z - r);
	}
	
	public Vec3F mul(Vec3F r)
	{
		return new Vec3F(this.X * r.getX(), this.Y * r.getY(), this.Z * r.getZ());
	}
	
	public Vec3F mul(float x, float y, float z)
	{
		return new Vec3F(this.X * x, this.Y * y, this.Z * z);
	}
	
	public Vec3F mul(float r)
	{
		return new Vec3F(this.X * r, this.Y * r, this.Z * r);
	}
	
	public Vec3F div(Vec3F r)
	{
		return new Vec3F(this.X / r.getX(), this.Y / r.getY(), this.getZ() / r.getZ());
	}
	
	public Vec3F div(float r)
	{
		return new Vec3F(this.X / r, this.Y / r, this.Z / r);
	}
	
	public Vec3F abs()
	{
		return new Vec3F(Math.abs(X), Math.abs(Y), Math.abs(Z));
	}
	
	public boolean equals(Vec3F v)
	{
		if(X == v.getX() && Y == v.getY() && Z == v.getZ())
			return true;

		else return false;
	}
	
	public String toString()
	{
		return "[" + this.X + "," + this.Y + "," + this.Z + "]";
	}

	public float getX()
	{
		return X;
	}

	public void setX(float x)
	{
		X = x;
	}

	public float getY()
	{
		return Y;
	}

	public void setY(float y)
	{
		Y = y;
	}

	public float getZ()
	{
		return Z;
	}

	public void setZ(float z)
	{
		Z = z;
	}
}
