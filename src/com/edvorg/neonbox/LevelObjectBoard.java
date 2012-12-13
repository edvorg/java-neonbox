package com.edvorg.neonbox;

import java.util.ArrayList;
import java.util.Iterator;

public class LevelObjectBoard extends LevelObjectRectangle
{
	public LevelObjectBoard()
	{
		m_Type = LevelObjectType.Board;
		m_Activity = LevelObjectActivity.Animated;
		m_HoldingBalls = new ArrayList<LevelObjectBall>();
		
		setDimensions(new vec2f(15.0f, 2.5f));		
	}
	
	public void computeBallVelocity(LevelObjectBall _ball, float _velmod)
	{	
		vec2f newvel = new vec2f(_ball.getPos().x - getPos().x, _ball.getPos().y - getPos().y);
		newvel.x /= 8.0f;
		newvel.normalize();
		newvel.mul(_velmod);
		_ball.setVel(newvel);
	}
	
	public void holdBall(LevelObjectBall _ball)
	{
		if (!m_HoldingBalls.contains(_ball))
		{
			m_HoldingBalls.add(_ball);
			_ball.setHold(true);
			_ball.setVel(new vec2f(0));
		}
	}
	
	public void unholdBall(LevelObjectBall _ball, float _velmod)
	{
		if (m_HoldingBalls.contains(_ball))
		{
			m_HoldingBalls.remove(_ball);
			_ball.setHold(false);
			computeBallVelocity(_ball, _velmod);
		}
	}
	
	public void setBallPos(LevelObjectBall _ball)
	{
		_ball.setPos(new vec2f(getPos()).add(_ball.getRad(), _ball.getRad() + getDimensions().y));		
	}
	
	public void setPos(vec2f _pos)
	{
		super.setPos(_pos);
		
		vec2f displace = new vec2f(_pos);
		displace.sub(getLastPos());
		
		for (Iterator<LevelObjectBall> i = m_HoldingBalls.iterator(); i.hasNext();)
		{
			LevelObjectBall ball = i.next();

			vec2f newpos = new vec2f(ball.getPos());
			newpos.add(displace);
			ball.setPos(newpos);
		}
	}
	
	private ArrayList<LevelObjectBall> m_HoldingBalls;
}
