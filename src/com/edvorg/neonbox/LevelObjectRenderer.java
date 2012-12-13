package com.edvorg.neonbox;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.lang.Math;

import javax.microedition.khronos.opengles.GL10;

public class LevelObjectRenderer
{
	public LevelObjectRenderer()
	{
		final float vrect[] = {-1.0f, -1.0f,
								-1.0f, 1.0f,
								1.0f, 1.0f,
								1.0f, -1.0f};
		
		final short irect[] = {0,1,3,
								3,1,2};
		
		final double step = Math.PI / 4.0f;
		final float vcircle[] = new float[16];
		final short icircle[] = {5, 3, 7,
								7, 3, 1,
								1, 3, 2,
								7, 1, 0,
								5, 4, 3,
								5, 7, 6};
		
		double curangle = 0;		
		for (int i=0; i<16; i+=2)
		{
			vcircle[i] = (float)Math.cos(curangle);
			vcircle[i+1] = (float)Math.sin(curangle);
			curangle += step;
		}
		
		ByteBuffer vrbb = ByteBuffer.allocateDirect(4*2*4);
		vrbb.order(ByteOrder.nativeOrder());
		m_RectangleVB = vrbb.asFloatBuffer();
		m_RectangleVB.put(vrect, 0, 8);
		m_RectangleVB.position(0);
		
		ByteBuffer irbb = ByteBuffer.allocateDirect(2*6);
		irbb.order(ByteOrder.nativeOrder());
		m_RectangleIB = irbb.asShortBuffer();
		m_RectangleIB.put(irect, 0, 6);
		m_RectangleIB.position(0);
		
		ByteBuffer vcbb = ByteBuffer.allocateDirect(4*2*8);
		vcbb.order(ByteOrder.nativeOrder());
		m_CircleVB = vcbb.asFloatBuffer();
		m_CircleVB.put(vcircle, 0, 16);
		m_CircleVB.position(0);
		
		ByteBuffer icbb = ByteBuffer.allocateDirect(2*18);
		icbb.order(ByteOrder.nativeOrder());
		m_CircleIB = icbb.asShortBuffer();
		m_CircleIB.put(icircle, 0, 18);
		m_CircleIB.position(0);
	}
	
	public void Draw(GL10 _gl, ArrayList<LevelObject> _objects)
	{
		if (m_GLSet)
		{
			for (int i=0; i<_objects.size(); i++)
			{
				Draw(_objects.get(i));
			}
		}
	}
	
	private void DrawRectangle(LevelObjectRectangle _obj)
	{
		m_GL.glScalef(((LevelObjectRectangle)_obj).getDimensions().x, ((LevelObjectRectangle)_obj).getDimensions().y, 1.0f);
		m_GL.glVertexPointer(2, GL10.GL_FLOAT, 0, m_RectangleVB);
		m_GL.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, m_RectangleIB);		
	}
	
	private void Draw(LevelObject _obj)
	{
		m_GL.glMatrixMode(GL10.GL_MODELVIEW);
		m_GL.glLoadIdentity();
		m_GL.glTranslatef(_obj.getPos().x, _obj.getPos().y, -1.0f);
		
		m_GL.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
		
		m_GL.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        m_GL.glFrontFace(GL10.GL_CW);
		
		switch(_obj.getType())
		{
		case Unknown:
			break;
		case Ball:
			m_GL.glScalef(((LevelObjectBall)_obj).getRad(), ((LevelObjectBall)_obj).getRad(), 1.0f);
			m_GL.glVertexPointer(2, GL10.GL_FLOAT, 0, m_CircleVB);
			m_GL.glDrawElements(GL10.GL_TRIANGLES, 18, GL10.GL_UNSIGNED_SHORT, m_CircleIB);
			break;
		case Board:
			DrawRectangle((LevelObjectRectangle)_obj);
			break;
		case Side:
			DrawRectangle((LevelObjectRectangle)_obj);
			break;
		case BottomSide:
			DrawRectangle((LevelObjectRectangle)_obj);
			break;
		case Brick:
			DrawRectangle((LevelObjectRectangle)_obj);
			break;
		default:
			break;
		}

		m_GL.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	public void setGL(GL10 _gl)
	{
		m_GL = _gl;
		m_GLSet = true;
	}
	
	public void setGL()
	{
		m_GLSet = false;
	}
	
	public GL10 m_GL;
	public boolean m_GLSet = false;

	private FloatBuffer m_RectangleVB;
	private FloatBuffer m_CircleVB;
	private ShortBuffer m_RectangleIB;
	private ShortBuffer m_CircleIB;
}
