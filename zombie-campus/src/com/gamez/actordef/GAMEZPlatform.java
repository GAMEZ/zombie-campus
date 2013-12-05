package com.gamez.actordef;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gamez.zombiecampus.Assets;

public class GAMEZPlatform extends Actor
{
	protected Vector2 position;
	protected Body body;
	
	private Texture texture;
	
	private final int TEX_HEIGHT, TEX_WIDTH;
	
	public GAMEZPlatform(Body body, Vector2 position, int length)
	{
		this.body = body;
		this.position = position;
		this.body.setTransform(position, 0);

		// Player Graphical Components
		texture = new Texture(Assets.PLATFORM_TEX);

		TEX_HEIGHT = texture.getHeight();
		TEX_WIDTH = length;
		//set platform physics Body shape
        FixtureDef fixDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(TEX_WIDTH, TEX_HEIGHT);// width, height
        fixDef.shape = shape;
        this.body.createFixture(fixDef);
	}
	public void move(float x, float y)
	{
		Vector2 dir = new Vector2(x,y);
		body.applyForceToCenter(dir);
	}
	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		batch.end();
		// Update Sprite by Physics Body position
		Vector2 pos = body.getTransform().getPosition();
		setPosition(pos.x, pos.y);

		batch.begin();
		batch.draw(texture, pos.x, pos.y, TEX_WIDTH, TEX_HEIGHT);
	}
	public Vector2 getPosition()
	{
		return position;
	}
	public Body getBody()
	{
		return body;
	}
}//end GAMEZPlatform
