package com.edvorg.neonbox;

public class LevelObjectBrick extends LevelObjectRectangle {
	public LevelObjectBrick()
	{
		m_Type = LevelObjectType.Brick;
		m_Activity = LevelObjectActivity.Static;
	}
	
	public void setHealth(int _health)
	{
		if (_health < 0) return;
		
		m_Health = _health;
	}
	
	public int getHealth()
	{
		return m_Health;
	}
	
	private int m_Health = 1;
}
