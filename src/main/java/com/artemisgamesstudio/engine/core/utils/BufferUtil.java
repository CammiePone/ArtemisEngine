package com.artemisgamesstudio.engine.core.utils;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import com.artemisgamesstudio.engine.core.math.Matrix4F;
import com.artemisgamesstudio.engine.core.math.Quaternion;
import com.artemisgamesstudio.engine.core.math.Vec2F;
import com.artemisgamesstudio.engine.core.math.Vec3F;
import com.artemisgamesstudio.engine.core.model.Vertex;

public class BufferUtil
{
	public static FloatBuffer createFloatBuffer(int size)
	{
		return BufferUtils.createFloatBuffer(size);
	}

	public static IntBuffer createIntBuffer(int size)
	{
		return BufferUtils.createIntBuffer(size);
	}
	
	public static DoubleBuffer createDoubleBuffer(int size)
	{
		return BufferUtils.createDoubleBuffer(size);
	}
	
	public static IntBuffer createFlippedBuffer(int... values)
	{
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(float... values)
	{
		FloatBuffer buffer = createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		
		return buffer;
	}
	
	public static DoubleBuffer createFlippedBuffer(double... values)
	{
		DoubleBuffer buffer = createDoubleBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		
		return buffer;
	}
	
	public static FloatBuffer createFlippedBufferAOS(Vertex[] vertices)
	{
		FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.FLOATS);
		
		for(int i = 0; i < vertices.length; i++)
		{
			buffer.put(vertices[i].getPos().getX());
			buffer.put(vertices[i].getPos().getY());
			buffer.put(vertices[i].getPos().getZ());
			buffer.put(vertices[i].getNormal().getX());
			buffer.put(vertices[i].getNormal().getY());
			buffer.put(vertices[i].getNormal().getZ());
			buffer.put(vertices[i].getTextureCoord().getX());
			buffer.put(vertices[i].getTextureCoord().getY());
			
			if(vertices[i].getTangent() != null && vertices[i].getBitangent() != null)
			{
				buffer.put(vertices[i].getTangent().getX());
				buffer.put(vertices[i].getTangent().getY());
				buffer.put(vertices[i].getTangent().getZ());
				buffer.put(vertices[i].getBitangent().getX());
				buffer.put(vertices[i].getBitangent().getY());
				buffer.put(vertices[i].getBitangent().getZ());
			}
		}
		
		buffer.flip();
		
		return buffer;
	}
	
	public static FloatBuffer createFlippedBufferSOA(Vertex[] vertices)
	{
		FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.FLOATS);
		
		for(int i = 0; i < vertices.length; i++)
		{
			buffer.put(vertices[i].getPos().getX());
			buffer.put(vertices[i].getPos().getY());
			buffer.put(vertices[i].getPos().getZ());
		}
		
		for(int i = 0; i < vertices.length; i++)
		{
			buffer.put(vertices[i].getNormal().getX());
			buffer.put(vertices[i].getNormal().getY());
			buffer.put(vertices[i].getNormal().getZ());
		}
			
		for(int i = 0; i < vertices.length; i++)
		{
			buffer.put(vertices[i].getTextureCoord().getX());
			buffer.put(vertices[i].getTextureCoord().getY());
		}	
		
		buffer.flip();
		
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Vec3F[] vector)
	{
		FloatBuffer buffer = createFloatBuffer(vector.length * Float.BYTES * 3);
		
		for(int i = 0; i < vector.length; i++)
		{
			buffer.put(vector[i].getX());
			buffer.put(vector[i].getY());
			buffer.put(vector[i].getZ());
		}
		
		buffer.flip();
		
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Quaternion[] vector)
	{
		FloatBuffer buffer = createFloatBuffer(vector.length * Float.BYTES * 4);
		
		for(int i = 0; i < vector.length; i++)
		{
			buffer.put(vector[i].getX());
			buffer.put(vector[i].getY());
			buffer.put(vector[i].getZ());
			buffer.put(vector[i].getW());
		}
		
		buffer.flip();
		
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Vec3F vector)
	{
		FloatBuffer buffer = createFloatBuffer(Float.BYTES * 3);
		
		buffer.put(vector.getX());
		buffer.put(vector.getY());
		buffer.put(vector.getZ());
		
		buffer.flip();
		
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Quaternion vector)
	{
		FloatBuffer buffer = createFloatBuffer(Float.BYTES * 4);
		
		buffer.put(vector.getX());
		buffer.put(vector.getY());
		buffer.put(vector.getZ());
		buffer.put(vector.getW());
		
		buffer.flip();
		
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Vec2F[] vector)
	{
		FloatBuffer buffer = createFloatBuffer(vector.length * Float.BYTES * 2);
		
		for(int i = 0; i < vector.length; i++)
		{
			buffer.put(vector[i].getX());
			buffer.put(vector[i].getY());	
		}
		
		buffer.flip();
		
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix4F matrix)
	{
		FloatBuffer buffer = createFloatBuffer(4 * 4);
		
		for(int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				buffer.put(matrix.get(i, j));
		
		buffer.flip();
		
		return buffer;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix4F[] matrices)
	{
		FloatBuffer buffer = createFloatBuffer(4 * 4 * matrices.length);
		
		for(Matrix4F matrix : matrices){
			for(int i = 0; i < 4; i++)
				for(int j = 0; j < 4; j++)
					buffer.put(matrix.get(i, j));
		}
		
		buffer.flip();
		
		return buffer;
	}
}
