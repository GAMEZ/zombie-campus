package com.gamez.actordef;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gamez.zombiecampus.Assets;

public class GAMEZPlayer extends Actor implements GAMEZActor
{
	protected Vector2 position;
	protected GAMEZTool[] tools;// 0 = head, 1 = body, 2 = feet, 3 = weapon
	protected Body body;
	
	private static float MAX_MOVE = 10f;
	private static int WEAPON = 3, SPR_WIDTH, SPR_HEIGHT;
	private int x, y, state, state_counter;
	private Vector2 last_force;
	
	private Texture texture;
	
	public GAMEZPlayer(Body body, Vector2 position, GAMEZTool[] tools)
	{
		this.body = body;
		this.position = position;
		this.body.setTransform(position, 0);
        
		this.tools = tools;
		if (tools == null)
		{
			this.tools = new GAMEZTool[4];
		}
		// Player Graphical Components
		texture = new Texture(Assets.PLAYER_SPRITE);

		SPR_HEIGHT = texture.getHeight();
		SPR_HEIGHT = SPR_HEIGHT / 2; // Player Sprites are half the height of the sprite sheet
		SPR_WIDTH = texture.getWidth();
		SPR_WIDTH = SPR_WIDTH / 8; // Player Sprites are 1/8 the width of the sprite sheet
		//set player physics Body shape
        FixtureDef fixDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(SPR_WIDTH, SPR_HEIGHT);// width, height
        fixDef.shape = shape;
        this.body.createFixture(fixDef);
		// initialize counters, iterators and states
		x = 0;
		y = 1;
		state = 0;
		state_counter = 0;
		last_force = new Vector2(0,0);
	}
	@Override
	public void move(float x, float y)
	{
		Vector2 dir = new Vector2(x,y);
		body.applyForceToCenter(dir);
		last_force = dir;
		state = 1;
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		batch.end();
		// check state
		switch (state)
		{
			case 0:
				// Cycle through sprite frames
				if (x > 6)
				{
					x = 0;
					if (y == 1)
						y = 0;
					else
						y = 1;
				}
				else
					x++;
				break;
			case 1: // jumping state
				if (state_counter <= 50)// ascending
				{
					body.applyForceToCenter(last_force);
					state_counter++;
				}
				else if(state_counter <= 100)// descending
				{
					state_counter++;
				}
				else
				{
					//body.applyForceToCenter(last_force.mul(0, -1));
					state = 0;
					state_counter = 0;
				}
				break;
		}
		// Update Sprite by Physics Body position
		Vector2 pos = body.getTransform().getPosition();
		setPosition(pos.x, pos.y);
		System.out.println("X " + pos.x + ", Y " + pos.y);
	
		TextureRegion region = new TextureRegion(texture, x*SPR_WIDTH, y*SPR_HEIGHT, SPR_WIDTH, SPR_HEIGHT);
		batch.begin();
		batch.draw(region, pos.x, pos.y);
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
	public int getState()
	{
		return state;
	}
}//end GAMEZPlayer
