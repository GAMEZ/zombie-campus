package com.gamez.actordef;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gamez.zombiecampus.Assets;

public class GAMEZPlatform extends Actor
{
	protected Vector2 position, last_force;
	protected Body body;

	private Texture texture;
	
	public GAMEZPlatform(Body body, Vector2 position)
	{
		this.body = body;
		this.position = position;
		this.body.setTransform(position, 0);

		// Platform Graphical Components
		texture = new Texture(Assets.PLATFORM_TEX);
		last_force = new Vector2(0,0);
	}
	
	public void move(float x, float y)
	{
		Vector2 dir = new Vector2(x,y);
		body.applyForceToCenter(dir);
		last_force = dir;
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		batch.end();
		// Update Sprite by Physics Body position
		Vector2 pos = body.getTransform().getPosition();
		setPosition(pos.x, pos.y);
		System.out.println("X " + pos.x + ", Y " + pos.y);
		batch.begin();
		batch.draw(texture, pos.x, pos.y);
	}
	public Vector2 getPosition()
	{
		return position;
	}
	public Body getBody()
	{
		return body;
	}
}//end GAMEZPlayer
