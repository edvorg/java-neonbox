package com.edvorg.neonbox;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class NeonboxSurfaceView extends GLSurfaceView
{
	public NeonboxSurfaceView(Context _context)
	{
		super(_context);
		m_Renderer = new NeonboxRenderer(new vec2f(getWidth(), getHeight()));
		this.setRenderer(m_Renderer);
	}
	
	public boolean onTouchEvent(final MotionEvent _event)
	{
		if (m_LevelSet)
		{
			if (_event.getY() > 0.5f * getHeight())
			{
				m_Level.setBoardPos(_event.getX() / (float)getWidth() * 200.0f - 100.0f);
			}
			else
			{
				m_Level.unholdBalls();
			}
		}
		return true;
	}
	
	public void setLevel(Level _level)
	{
		m_Level = _level;
		m_Renderer.setLevel(m_Level);
		m_LevelSet = true;
	}
	
	public void setLevel()
	{
		m_Renderer.setLevel();
		m_LevelSet = false;
	}
	
	public NeonboxRenderer m_Renderer;	
	private Level m_Level;
	private boolean m_LevelSet = false;
}
