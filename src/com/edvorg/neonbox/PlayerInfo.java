package com.edvorg.neonbox;

public final class PlayerInfo {
	PlayerInfo()
	{		
	}
	
	public void addLive()
	{
		m_Lives++;
	}
	
	public void subLive()
	{
		if (m_Lives > 0) m_Lives--;
	}
	
	public int getLives()
	{
		return m_Lives;
	}
	
	public void setBallVelocity(float _newvel)
	{
		if (_newvel < 1.0f) return;
		
		m_BallVelocity = _newvel;
	}
	
	public float getBallVelocity()
	{
		return m_BallVelocity;
	}
	
	private int m_Lives = 3;
	private float m_BallVelocity = 150.0f;
}
