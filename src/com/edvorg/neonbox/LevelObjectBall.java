package com.edvorg.neonbox;

public class LevelObjectBall extends LevelObject
{
	public LevelObjectBall()
	{
		m_Type = LevelObjectType.Ball;
		m_Activity = LevelObjectActivity.Animated;
		
		m_Rad = 1.0f;
	}
	
	public void setRad(float _rad)
	{
		if (_rad < 1.0f) return;
		
		m_Rad = _rad;
	}
	
	public float getRad()
	{
		return m_Rad;
	}
	
	public void setHold(boolean _hold)
	{
		if (_hold)
		{
			m_Hold = true;
		}
		else
		{
			m_Hold = false;
		}
	}
	
	public boolean getHold()
	{
		return m_Hold;
	}
	
	public float getLeftBounds()
	{
		return getPos().x - m_Rad;
	}
	
	public float getUpBounds()
	{
		return getPos().y + m_Rad;
	}
	
	public float getRightBounds()
	{
		return getPos().x + m_Rad;
	}
	
	public float getDownBounds()
	{
		return getPos().y - m_Rad;
	}

	private boolean m_Hold = false;
	private float m_Rad = 1.0f;
}