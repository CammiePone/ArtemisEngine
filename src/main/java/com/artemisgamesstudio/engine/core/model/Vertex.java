package com.artemisgamesstudio.engine.core.model;

import com.artemisgamesstudio.engine.core.math.Vec2F;
import com.artemisgamesstudio.engine.core.math.Vec3F;

public class Vertex
{
	public static final int BYTES = 14 * Float.BYTES;
	public static final int FLOATS = 14;
	
	private Vec3F pos;
	private Vec3F normal;
	private Vec2F textureCoord;
	private Vec3F tangent;
	private Vec3F bitangent;
	
	public Vertex()
	{

	}
	
	public Vertex(Vec3F pos)
	{
		this.setPos(pos);
		this.setTextureCoord(new Vec2F(0,0));
		this.setNormal(new Vec3F(0,0,0));
	}
	
	public Vertex(Vec3F pos, Vec2F texture)
	{
		this.setPos(pos);
		this.setTextureCoord(texture);
		this.setNormal(new Vec3F(0,0,0));
	}

	public Vec3F getPos()
	{
		return pos;
	}

	public void setPos(Vec3F pos)
	{
		this.pos = pos;
	}

	public Vec2F getTextureCoord()
	{
		return textureCoord;
	}

	public void setTextureCoord(Vec2F texture)
	{
		this.textureCoord = texture;
	}


	public Vec3F getNormal()
	{
		return normal;
	}

	public void setNormal(Vec3F normal)
	{
		this.normal = normal;
	}

	public Vec3F getTangent()
	{
		return tangent;
	}

	public void setTangent(Vec3F tangent)
	{
		this.tangent = tangent;
	}

	public Vec3F getBitangent()
	{
		return bitangent;
	}

	public void setBitangent(Vec3F bitangent)
	{
		this.bitangent = bitangent;
	}
}
