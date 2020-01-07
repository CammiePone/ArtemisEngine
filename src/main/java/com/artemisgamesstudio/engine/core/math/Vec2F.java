package com.artemisgamesstudio.engine.core.math;

public class Vec2F
{
	private float X;
	private float Y;
	
	public Vec2F()
	{
		this.setX(0);
		this.setY(0);
	}
	
	public Vec2F(float x, float y)
	{
		this.setX(x);
		this.setY(y);
	}
	
	public Vec2F(Vec2F v)
	{
		this.X = v.getX();
		this.Y = v.getY();
	}
	
	public float length()
	{
		return (float) Math.sqrt(X*X + Y*Y);
	}
	
	public float dot(Vec2F r)
	{
		return X * r.getX() + Y * r.getY();
	}
	
	public Vec2F normalize()
	{
		float length = length();
		
		X /= length;
		Y /= length;
		
		return this;
	}
	
	public Vec2F add(Vec2F r)
	{
		return new Vec2F(this.X + r.getX(), this.Y + r.getY());
	}
	
	public Vec2F add(float r)
	{
		return new Vec2F(this.X + r, this.Y + r);
	}
	
	public Vec2F sub(Vec2F r)
	{
		return new Vec2F(this.X - r.getX(), this.Y - r.getY());
	}
	
	public Vec2F sub(float r)
	{
		return new Vec2F(this.X - r, this.Y - r);
	}
	
	public Vec2F mul(Vec2F r)
	{
		return new Vec2F(this.X * r.getX(), this.Y * r.getY());
	}
	
	public Vec2F mul(float r)
	{
		return new Vec2F(this.X * r, this.Y * r);
	}
	
	public Vec2F div(Vec2F r)
	{
		return new Vec2F(this.X / r.getX(), this.Y / r.getY());
	}
	
	public Vec2F div(float r)
	{
		return new Vec2F(this.X / r, this.Y / r);
	}
	
	public String toString()
	{
		return "[" + this.X + "," + this.Y + "]";
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
}
