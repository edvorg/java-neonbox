package com.edvorg.neonbox;

import java.util.ArrayList;
import java.util.Iterator;

import javax.microedition.khronos.opengles.GL10;

public class Level
{
	public Level()
	{		
		m_Objects = new ArrayList<LevelObject>();	
		m_Balls = new ArrayList<LevelObjectBall>();
		m_Board = new LevelObjectBoard();
		
		m_Renderer = new LevelObjectRenderer();
		m_Solver = new LevelObjectSolver();
		
		m_LeftSide = new LevelObjectSide();
		m_TopSide = new LevelObjectSide();
		m_RightSide = new LevelObjectSide();
		m_BottomSide = new LevelObjectBottomSide();
		
		m_PlayerInfo = new PlayerInfo();		

		m_Solver.setObjects(m_Objects, m_Balls);
		
		updateSides();

		LevelObjectBall ball = new LevelObjectBall();
		ball.setRad(2.5f);
		
		m_Board.setPos(new vec2f(0.0f, -90.0f));
		m_Board.setBallPos(ball);
		m_Board.holdBall(ball);
		
		m_Objects.add(m_Board);
		m_Objects.add(ball);
		m_Objects.add(m_LeftSide);
		m_Objects.add(m_TopSide);
		m_Objects.add(m_RightSide);
		m_Objects.add(m_BottomSide);
		
		m_Balls.add(ball);
		
		loadBricks();
	}
	
	public void draw(GL10 _gl)
	{
		m_Renderer.setGL(_gl);		
		m_Renderer.Draw(_gl, m_Objects);
	}
	
	public void update()
	{		
		if (m_Paused) return;
		
		double curtime = System.currentTimeMillis();
		double dts = (curtime - m_LastUpdate) / 1000.0;	
		m_LastUpdate = curtime;
		
		m_Solver.step((float)dts);
		
		for (Iterator<LevelObjectBall> i = m_Balls.iterator(); i.hasNext();)
		{
			LevelObjectBall ball = i.next();
			if (ball.getPos().y < -100.0f)
			{
				m_PlayerInfo.subLive();
				m_Board.setBallPos(ball);
				m_Board.holdBall(ball);
			}
		}	

		for (Iterator<LevelObject> i = m_Objects.iterator(); i.hasNext();)
		{		
			LevelObject obj = i.next();
			if (obj.getType() == LevelObjectType.Brick)
				if (((LevelObjectBrick)obj).getHealth() == 0)
				{
					m_Solver.cleanContacts((LevelObjectBrick)obj);
					i.remove();
					m_BricksCount--;
				}
		}
		
		if (m_BricksCount == 0)
			pause();
	}
	
	private void updateSides()
	{
		m_LeftSide.setDimensions(new vec2f(2.5f, 100.0f));
		m_LeftSide.setPos(new vec2f(-100.0f + 2.5f, 0));
		
		m_TopSide.setDimensions(new vec2f(100.0f, 2.5f));
		m_TopSide.setPos(new vec2f(0, 100.0f));
		
		m_RightSide.setDimensions(new vec2f(2.5f, 100.0f));
		m_RightSide.setPos(new vec2f(100.0f - 2.5f, 0));
		
		m_BottomSide.setDimensions(new vec2f(100.0f, 2.5f));
		m_BottomSide.setPos(new vec2f(0, -100.0f));
	}
	
	public void start()
	{
		if (m_Paused)
		{
			m_LastUpdate = System.currentTimeMillis();
		}
		m_Paused = false;
	}
	
	public void pause()
	{
		m_Paused = true;
	}
	
	public void unholdBalls()
	{
		for (Iterator<LevelObjectBall> i = m_Balls.iterator(); i.hasNext();)
		{
			LevelObjectBall ball = i.next();
			if (ball.getHold())
			{
				m_Board.unholdBall(ball, m_PlayerInfo.getBallVelocity());
			}
		}
	}
	
	public void setBoardPos(float _x)
	{
		if (_x - m_Board.getDimensions().x < m_LeftSide.getRightBounds())
		{
			_x = m_Board.getDimensions().x + m_LeftSide.getRightBounds();
		}
		else if (_x + m_Board.getDimensions().x > m_RightSide.getLeftBounds())
		{
			_x = m_RightSide.getLeftBounds() - m_Board.getDimensions().x;
		}
			
		m_Board.setPos(new vec2f(_x, -90.0f));
	}
	
	public void loadBricks()
	{		
		final int m = 5;
		final int n = 6;
		
		m_BricksCount = m * n;
		
		final float width = 180 / (float)n * 0.7f;
		final float height = 80 / (float)m * 0.7f;
		final float stepx = 180 / (float)(n-1) * 0.3f;
		final float stepy = 80 / (float)(m-1) * 0.3f;
		
		for (int i=0; i<m; i++)
		{
			for (int j=0; j<n; j++)
			{
				LevelObjectBrick brick = new LevelObjectBrick();
				brick.setDimensions(new vec2f(width / 2.0f, height / 2.0f));
				brick.setPos(new vec2f(-90.0f + width / 2.0f + j * (width + stepx), i * (height + stepy)));
				brick.setHealth(1);
				m_Objects.add(brick);
			}
		}
	}

	private int m_BricksCount = 0;
	private double m_LastUpdate = 0.0f;
	private boolean m_Paused = true;

	private LevelObjectRenderer m_Renderer;
	private LevelObjectSolver m_Solver;
		
	private ArrayList<LevelObject> m_Objects;
	private ArrayList<LevelObjectBall> m_Balls;
	private LevelObjectBoard m_Board;
	
	private LevelObjectSide m_LeftSide;
	private LevelObjectSide m_TopSide;
	private LevelObjectSide m_RightSide;
	private LevelObjectBottomSide m_BottomSide;
	
	public PlayerInfo m_PlayerInfo;
}
