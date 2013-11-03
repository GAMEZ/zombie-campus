package com.gamez.actordef;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sun.xml.internal.ws.wsdl.writer.document.soap12.Body;

public class GAMEZPlayer extends Actor implements GAMEZActor
{
	protected Body body;
	protected Vector2 position;
	protected GAMEZTool[] tools;
	
	public GAMEZPlayer(Body body, Vector2 position, GAMEZTool[] tools)
	{
		this.body = body;
		this.position = position;
		this.tools = tools;
	}
	
	@Override
	public void move(Vector2 dir)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void equipTool(GAMEZTool tool)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector2 getPosition()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GAMEZTool[] getTools()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Body getBody()
	{
		// TODO Auto-generated method stub
		return null;
	}
}//end GAMEZPlayer
