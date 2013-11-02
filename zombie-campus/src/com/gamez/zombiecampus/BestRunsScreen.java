package com.gamez.zombiecampus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BestRunsScreen implements Screen {

    final AndroidGame game;
    final Screen previousScreen;
    Texture background;
    private SpriteBatch spriteBatch;
    private float alpha = 0.0f;

    OrthographicCamera camera;

    public BestRunsScreen(final AndroidGame g, Screen preScreen) {
    	game = g;
    	previousScreen = preScreen;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
    
    @Override
    public void render(float delta) {
    	Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        
        if(alpha >= .95f)
        		alpha = 1.0f;
        else
        	alpha = alpha + .05f;
                    
        spriteBatch.setColor(1.0f, 1.0f, 1.0f, alpha);
        spriteBatch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
        spriteBatch.end();
        
        // check for input (back button pressed)
        if(Gdx.input.justTouched() && Touch.getButtonTouched(Gdx.input.getX(), Gdx.input.getY(), camera.viewportWidth, camera.viewportHeight, 64.4, 95.5, 79.3, 96.7))
        	game.setScreen(new MainMenuScreen(game));
        	
        
    }

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		background = new Texture(Gdx.files.internal(Assets.BEST_RUNS_SCREEN));
		spriteBatch = new SpriteBatch();
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
}

