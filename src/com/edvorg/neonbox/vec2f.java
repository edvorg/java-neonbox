package com.edvorg.neonbox;

import java.lang.Math;

public class vec2f
{
	public float x;
	public float y;
	
	public vec2f()
	{
		set(0);
	}
	
	public vec2f(float _xy)
	{
		set(_xy);
	}
	
	public vec2f(float _x, float _y)
	{
		set(_x, _y);
	}
	
	public vec2f(vec2f _src)
	{
		set(_src);
	}
	
	public vec2f set(float _xy)
	{
		x = y = _xy;
		return this;
	}
	
	public vec2f set(float _x, float _y)
	{
		x = _x;
		y = _y;
		return this;
	}
	
	public vec2f set(vec2f _src)
	{
		x = _src.x;
		y = _src.y;
		return this;
	}
	
	public vec2f add(float _xy)
	{
		x += _xy;
		y += _xy;
		return this;
	}
	
	public vec2f add(float _x, float _y)
	{
		x += _x;
		y += _y;
		return this;
	}
	
	public vec2f add(vec2f _src)
	{
		x += _src.x;
		y += _src.y;
		return this;
	}
	
	public vec2f sub(float _xy)
	{
		x -= _xy;
		y -= _xy;
		return this;
	}
	
	public vec2f sub(float _x, float _y)
	{
		x -= _x;
		y -= _y;
		return this;
	}
	
	public vec2f sub(vec2f _src)
	{
		x -= _src.x;
		y -= _src.y;
		return this;
	}
	
	public vec2f mul(float _xy)
	{
		x *= _xy;
		y *= _xy;
		return this;
	}
	
	public vec2f mul(float _x, float _y)
	{
		x *= _x;
		y *= _y;
		return this;
	}
	
	public vec2f mul(vec2f _src)
	{
		x *= _src.x;
		y *= _src.y;
		return this;
	}
	
	public float length2()
	{
		return x*x + y*y;
	}
	
	public float length()
	{
		return (float)Math.sqrt(x*x + y*y);
	}
	
	public vec2f normalize()
	{
		float len = length();
		x /= len;
		y /= len;
		return this;
	}
}