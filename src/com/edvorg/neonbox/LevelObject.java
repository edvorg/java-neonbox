package com.edvorg.neonbox;

public class LevelObject
{
	public LevelObject()
	{
		m_Type = LevelObjectType.Unknown;
		m_Activity = LevelObjectActivity.Static;

		m_Pos = new vec2f(0);
		m_LastPos = new vec2f(0);
		m_Vel = new vec2f(0);
	}
	
	public LevelObjectType getType()
	{
		return m_Type;
	}
	
	public void setPos(vec2f _pos)
	{
		m_LastPos.set(m_Pos);
		m_Pos.set(_pos);
	}
	
	public vec2f getPos()
	{
		return m_Pos;
	}
	
	public vec2f getLastPos()
	{
		return m_LastPos;
	}
	
	public void setVel(vec2f _pos)
	{
		if (m_Activity == LevelObjectActivity.Static) return;
		
		m_Vel.set(_pos);
	}
	
	public vec2f getVel()
	{
		return m_Vel;
	}
	
	public LevelObjectActivity getActivity()
	{
		return m_Activity;
	}
	
	public void setActivity(LevelObjectActivity _activity)
	{
		m_Activity = _activity;
		
		if (_activity == LevelObjectActivity.Static) m_Vel.set(0);
	}
	
	protected LevelObjectType m_Type;
	protected LevelObjectActivity m_Activity;
	private vec2f m_LastPos;
	private vec2f m_Pos;
	private vec2f m_Vel;
}