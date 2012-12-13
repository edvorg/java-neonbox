package com.edvorg.neonbox;

import java.util.ArrayList;
import java.util.Iterator;

public class LevelObjectSolver {
	public LevelObjectSolver()
	{
		m_Contacts = new ArrayList<LevelObjectContact>();
	}
	
	public void setObjects(ArrayList<LevelObject> _objects, ArrayList<LevelObjectBall> _balls)
	{
		m_Contacts.clear();
		m_Objects = _objects;
		m_Balls = _balls;
		m_ObjectsSet = true;
	}
	
	public void setObjects()
	{
		m_ObjectsSet = false;
	}
	
	private LevelObjectContact getContact(LevelObjectBall _ball, LevelObject _obj)
	{
		for (Iterator<LevelObjectContact> i = m_Contacts.iterator(); i.hasNext();)
		{
			LevelObjectContact contact = i.next();
			
			if (contact.m_Ball == _ball && contact.m_Obj == _obj)
				return contact;
		}
		return null;
	}
	
	private boolean BallRectangleCollision(LevelObjectBall _ball, LevelObjectRectangle _rect)
	{
		if (_ball.getRightBounds() > _rect.getLeftBounds())
		{
			if (_ball.getLeftBounds() < _rect.getRightBounds())
			{
				if (_ball.getUpBounds() > _rect.getDownBounds())
				{
					if (_ball.getDownBounds() < _rect.getUpBounds())
					{
						return true;						
					}
				}
			}
		}
		
		return false;
	}
	
	private void detectCollisions(float _dt)
	{
		for (Iterator<LevelObjectContact> i = m_Contacts.iterator(); i.hasNext();)
		{
			LevelObjectContact contact = i.next();
			
			contact.m_Old = true;
			

			if (contact.m_Obj.getType() == LevelObjectType.Side || contact.m_Obj.getType() == LevelObjectType.Board || contact.m_Obj.getType() == LevelObjectType.Brick)
			{			
				if (!BallRectangleCollision(contact.m_Ball, (LevelObjectRectangle)contact.m_Obj))
				{	
					contact.m_Ends = true;
				}	
			}
		}
		
		for (Iterator<LevelObjectBall> i = m_Balls.iterator(); i.hasNext();)
		{
			LevelObjectBall ball = i.next();
			
			for (Iterator<LevelObject> j = m_Objects.iterator(); j.hasNext();)
			{
				LevelObject obj = j.next();

				if (obj.getType() == LevelObjectType.Side || obj.getType() == LevelObjectType.Board || obj.getType() == LevelObjectType.Brick)
				{
					LevelObjectRectangle rect = (LevelObjectRectangle)obj;
					
					if (BallRectangleCollision(ball, rect))
					{
						LevelObjectContact contact = getContact(ball, obj);
						
						if (contact == null)
						{
							vec2f normal = new vec2f();
							if (ball.getLastPos().x + ball.getRad() < rect.getPos().x - rect.getDimensions().x)
							{
								normal.set(-1.0f, 0.0f);
							}
							else if (ball.getLastPos().x - ball.getRad() > rect.getPos().x + rect.getDimensions().x)
							{
								normal.set(1.0f, 0.0f);
							}
							else if (ball.getLastPos().y + ball.getRad() < rect.getPos().y - rect.getDimensions().y)
							{
								normal.set(0.0f, -1.0f);
							}
							else if (ball.getLastPos().y - ball.getRad() > rect.getPos().y - rect.getDimensions().y)
							{
								normal.set(0.0f, 1.0f);
							}							
							m_Contacts.add(new LevelObjectContact(ball, obj, normal));
						}						
					}
				}
				else
				{
					
				}
			}
		}
	}
	
	public void solve()
	{
		for (Iterator<LevelObjectContact> i = m_Contacts.iterator(); i.hasNext();)
		{
			LevelObjectContact contact = i.next();
			
			if (contact.m_Old) continue;
			
			if (contact.m_Obj.getType() == LevelObjectType.Board)
			{
				((LevelObjectBoard)contact.m_Obj).computeBallVelocity(contact.m_Ball, contact.m_Ball.getVel().length());
			}
			else
			{	
				if (contact.m_Obj.getType() == LevelObjectType.Brick)
					((LevelObjectBrick)contact.m_Obj).setHealth(((LevelObjectBrick)contact.m_Obj).getHealth()-1);
				
				vec2f newvel = new vec2f(contact.m_Ball.getVel());
				vec2f tmp = new vec2f(contact.m_Normal);
				tmp.mul(2.0f * (newvel.x * contact.m_Normal.x + newvel.y * contact.m_Normal.y));
				newvel.sub(tmp);
				
				contact.m_Ball.setVel(newvel);
			}
		}
	}
	
	public void cleanContacts()
	{	
		for (Iterator<LevelObjectContact> i = m_Contacts.iterator(); i.hasNext();)
		{
			if (i.next().m_Ends)
				i.remove();
		}	
	}
	
	public void cleanContacts(LevelObjectBrick _brick)
	{
		for (Iterator<LevelObjectContact> i = m_Contacts.iterator(); i.hasNext();)
		{
			if (i.next().m_Obj == _brick)
				i.remove();
		}		
	}
	
	public void step(float _dt)
	{	
		if (m_ObjectsSet)
		{
			detectCollisions(_dt);
			solve();
			cleanContacts();
			
			for (int i=0; i<m_Objects.size(); i++)
			{
				if (m_Objects.get(i).getActivity() == LevelObjectActivity.Static) continue;
				
				if (m_Objects.get(i).getType() == LevelObjectType.Ball)
					if (((LevelObjectBall)m_Objects.get(i)).getHold())
						continue;
				
				vec2f newpos = new vec2f(m_Objects.get(i).getPos());
				newpos.x += m_Objects.get(i).getVel().x * _dt;
				newpos.y += m_Objects.get(i).getVel().y * _dt;
				m_Objects.get(i).setPos(newpos);
			}
		}
	}

	private ArrayList<LevelObject> m_Objects;
	private ArrayList<LevelObjectBall> m_Balls;
	private boolean m_ObjectsSet;
	
	private ArrayList<LevelObjectContact> m_Contacts;
}
