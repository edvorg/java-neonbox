package com.edvorg.neonbox;

import java.lang.Math;

public class LevelObjectRectangle extends LevelObject
{
	public LevelObjectRectangle()
	{
		m_Dimensions = new vec2f();		
		m_Dimensions.set(1.0f);
	}
	
	public void setDimensions(vec2f _dimensions)
	{
		if (Math.abs(_dimensions.x) < 1.0f || Math.abs(_dimensions.y) < 1.0f) return;
		
		m_Dimensions.set(_dimensions);
	}
	
	public vec2f getDimensions()
	{
		return m_Dimensions;
	}
	
	public float getLeftBounds()
	{
		return getPos().x - m_Dimensions.x;
	}
	
	public float getUpBounds()
	{
		return getPos().y + m_Dimensions.y;
	}
	
	public float getRightBounds()
	{
		return getPos().x + m_Dimensions.x;
	}
	
	public float getDownBounds()
	{
		return getPos().y - m_Dimensions.y;
	}
	
	protected vec2f m_Dimensions;
}
