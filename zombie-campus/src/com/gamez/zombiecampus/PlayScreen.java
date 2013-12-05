package com.gamez.zombiecampus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gamez.actordef.GAMEZPlatform;
import com.gamez.actordef.GAMEZPlayer;
import com.gamez.actordef.GAMEZGun;
import com.gamez.actordef.GAMEZTool;

public class PlayScreen implements Screen {

    final AndroidGame game;
    private float alpha = 0.0f;
    
    // Physics
    final World world;
    static final Vector2 GRAVITY = new Vector2(0.0f, -400.0f);
    static final Vector2 CENTER = new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
    static final Vector2 START_POS = new Vector2(Gdx.graphics.getWidth()/2, 100);
    static final float JUMP_FORCE = 1000;
    // Physics body definitions
    private final BodyDef active, inactive, bullet;
    
    // Graphics
    private final Stage stage;
    private final GAMEZPlayer player;
    private final GAMEZPlatform platform;
    private Texture background;
    private SpriteBatch bgndBatch;
    
    // Creates a background object initialized with time and weather condition
    private Background bg = new Background(new WUWeatherTime());

    public PlayScreen(final AndroidGame g) {
    	game = g;
    	// Creating camera object for stage
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        // Instatiating environments
        world = new World(GRAVITY, true);// physical
        stage = new Stage();// graphical
        stage.setCamera(camera);
        
        // define a Body for all active actors (Enemies and Player)
        active = new BodyDef();
        active.active = true;
        active.allowSleep = true;
        active.awake = true;
        active.type = BodyDef.BodyType.DynamicBody;
        // define a Body type for all bullets (projectiles produced)
        bullet = new BodyDef();
        bullet.bullet = true;
        bullet.active = true;
        bullet.awake = true;
        bullet.type = BodyDef.BodyType.DynamicBody;
        // define all obstacle body type
        inactive = new BodyDef();
        inactive.type = BodyDef.BodyType.StaticBody;
        inactive.awake = true;
        
        // Load player data (XML parsing?)
        GAMEZTool[] playerTools = new GAMEZTool[4];
        // Add player items here:
        GAMEZGun playerGun = null;
        
        if (playerGun == null)
        	playerGun = new GAMEZGun(false, 0, 0, 0);
        playerTools[3] = playerGun;

        // Set up Physics Bodies
        Body playerBody = world.createBody(active);
        Body platformBody = world.createBody(inactive);
        // Add Platform to stage
        platform = new GAMEZPlatform(platformBody, new Vector2(0,10), Gdx.graphics.getWidth());
        stage.addActor(platform);
        // Add player to stage
        player = new GAMEZPlayer(playerBody, START_POS, playerTools);
        stage.addActor(player);
        stage.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }
    
    @Override
    public void render(float delta) {
    	//Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        //Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    	world.step(delta, 1, 1);
    	stage.getCamera().update();
        game.batch.setProjectionMatrix(stage.getCamera().combined);
        bgndBatch.begin();
        
        if(alpha >= .95f)
        	alpha = 1.0f;
        else
        	alpha = alpha + .05f;
                        
        background = new Texture(bg.Draw());
        
        bgndBatch.setColor(1.0f, 1.0f, 1.0f, alpha);
        bgndBatch.draw(background, 0, 0, stage.getCamera().viewportWidth, stage.getCamera().viewportHeight);
        bgndBatch.end();
        
        // check for input
        if(Gdx.input.justTouched())
        {
        	checkButtons();
        }
        // draw stage after checking for inputs
        stage.act(delta);
        stage.draw();
        
        // release resources for graphical objects
        background.dispose();
    }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		bgndBatch = new SpriteBatch();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	public void checkButtons(){
		// Check location of user touch input
		// jump button
		if(Touch.getButtonTouched(Gdx.input.getX(), Gdx.input.getY(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 50, 40, 100))
		{
			if (player.getState() != 1)
			{ 
				player.move(0, JUMP_FORCE);
				System.out.println("Jump!");
			}
		}
		// fire button
		else if(Touch.getButtonTouched(Gdx.input.getX(), Gdx.input.getY(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 51, 100, 40, 100))
		{
			System.out.println("Pull trigger");
			player.getWeapon().use();
		}
		// pause button
		else if(Touch.getButtonTouched(Gdx.input.getX(), Gdx.input.getY(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 100, 0, 39))
			System.out.println("Pause everything here");
	}
}
