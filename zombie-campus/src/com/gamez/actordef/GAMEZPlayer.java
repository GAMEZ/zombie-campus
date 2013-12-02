package com.gamez.actordef;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GAMEZPlayer extends Actor implements GAMEZActor
{
	protected Vector2 position;
	protected GAMEZTool[] tools;// 0 = head, 1 = body, 2 = feet, 3 = weapon
	protected Body body;
	
	private static float MAX_MOVE = 10f;
	private static int WEAPON = 4;
	
	public GAMEZPlayer(Body body, Vector2 position, GAMEZTool[] tools)
	{
		this.body = body;
		this.position = position;
		this.tools = tools;
		if (tools == null)
		{
			this.tools = new GAMEZTool[4];
		}
	}
	@Override
	public void move(float x, float y)
	{
		Vector2 dir = new Vector2(x,y)
		//magnitude check
		float mag = (float) Math.sqrt(x*x + y*y); 
		if (mag <= MAX_MOVE)
		{
			body.applyForceToCenter(dir);
		}
		else //Move the maximum allowed
		{
			dir = dir.div(mag);
			dir.mul(MAX_MOVE);
			body.applyForceToCenter(dir);	
		}
		Vector2 pos = body.getTransform().getPosition();
		setPosition(pos.x, pos.y);
	}
	@Override
	public GAMEZTool equipTool(GAMEZTool tool, int slot)
	{
		GAMEZTool old_tool; 
		if (slot >= 0 && slot < tools.length)
		{
			old_tool = tools[slot];
			tools[slot] = tool;
		}
		else
		{
			old_tool = tool;
		}
		return old_tool;
	}
	@Override
	public Vector2 getPosition()
	{
		return position;
	}
	@Override
	public GAMEZTool[] getTools()
	{
		return tools;
	}
	public GAMEZTool getWeapon()
	{
		return tools[WEAPON];
	}
	@Override
	public Body getBody()
	{
		return body;
	}
}//end GAMEZPlayer
