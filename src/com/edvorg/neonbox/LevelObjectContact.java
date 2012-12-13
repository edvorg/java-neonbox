package com.edvorg.neonbox;

public class LevelObjectContact {
	public LevelObjectContact(LevelObjectBall _ball, LevelObject _obj, vec2f _normal)
	{
		m_Ball = _ball;
		m_Obj = _obj;
		m_Normal = new vec2f(_normal);
	}
	
	public boolean m_Ends = false;
	public boolean m_Old = false;
	public LevelObjectBall m_Ball;
	public LevelObject m_Obj;
	public vec2f m_Normal;
}
