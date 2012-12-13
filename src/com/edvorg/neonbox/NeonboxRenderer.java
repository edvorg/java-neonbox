package com.edvorg.neonbox;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;

public class NeonboxRenderer implements GLSurfaceView.Renderer
{
	public NeonboxRenderer(vec2f _size)
	{
		m_ScreenSize = new vec2f(_size);
		m_FieldSizeHalf = new vec2f();
		
		onResize(_size);
	}
	
	private void onResize(vec2f _size)
	{
		m_ScreenSize.set(_size);
		m_Aspect = m_ScreenSize.x / m_ScreenSize.y;
		
		if (m_Aspect > 1.0f)
		{
			m_FieldSizeHalf.y = 100.0f;			
			m_FieldSizeHalf.x = m_FieldSizeHalf.y * m_Aspect;
		}
		else
		{
			m_FieldSizeHalf.x = 100.0f;
			m_FieldSizeHalf.y = m_FieldSizeHalf.x / m_Aspect;
		}
	}
	
	public void onSurfaceCreated(GL10 _gl, EGLConfig _config)
	{
		_gl.glClearColor(0.0f,0.0f,0.2f,1.0f);
        _gl.glEnable(GL10.GL_CULL_FACE);
        _gl.glShadeModel(GL10.GL_SMOOTH);
        _gl.glEnable(GL10.GL_DEPTH_TEST);
        _gl.glDisable(GL10.GL_DITHER);
	}
	
	public void onSurfaceChanged(GL10 _gl, int _w, int _h)
	{
		onResize(new vec2f(_w,_h));
		_gl.glViewport(0, 0, _w, _h);
	}
	
	public void onDrawFrame(GL10 _gl)
	{		
		_gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		if (m_LevelSet)
		{
			m_Level.update();
			
			_gl.glMatrixMode(GL10.GL_PROJECTION);
			_gl.glLoadIdentity();
			_gl.glOrthof(-m_FieldSizeHalf.x, m_FieldSizeHalf.x, -m_FieldSizeHalf.y, m_FieldSizeHalf.y, 1.0f, -1.0f);

			m_Level.draw(_gl);
			
			if (m_Aspect < 1.0f)
			{
				m_Level.m_PlayerInfo.getLives();
			}
		}
	}
	
	public void setLevel(Level _level)
	{
		m_Level = _level;
		m_LevelSet = true;
	}
	
	public void setLevel()
	{
		m_LevelSet = false;
	}

	private vec2f m_FieldSizeHalf;
	private float m_Aspect = 1.0f;
	private vec2f m_ScreenSize;
	private Level m_Level;
	private boolean m_LevelSet = false;
}
