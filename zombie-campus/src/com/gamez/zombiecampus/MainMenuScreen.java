package com.gamez.zombiecampus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuScreen implements Screen {

        final AndroidGame game;
        private Texture background;
        private SpriteBatch spriteBatch;
        private float alpha = 0.0f;

        OrthographicCamera camera;

        public MainMenuScreen(final AndroidGame g) {
        	game = g;
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
            
            if(alpha >= .95f) // fade in screen
            		alpha = 1.0f;
            else
            	alpha = alpha + .05f;
                        
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, alpha);
            spriteBatch.draw(background, 0, 0, camera.viewportWidth, camera.viewportHeight);
            spriteBatch.end();
            
            // Check for user touch input
            if(Gdx.input.justTouched())
            {
	            checkButtonPressed();
            }
        }
        
        /**
         * Based on x,y coordinate position of user's touch (percentage of screen)
         * go to screen specified by button touched
         */
        private void checkButtonPressed()
        {
        	// play button
        	if(Touch.getButtonTouched(Gdx.input.getX(), Gdx.input.getY(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 5.7, 48.4, 55.5, 74))
    			game.setScreen(new PlayScreen(game));
        	// options button
        	else if(Touch.getButtonTouched(Gdx.input.getX(), Gdx.input.getY(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 5.7, 48.4, 78.7, 96.8))
    			game.setScreen(new OptionsScreen(game, this));
        	// best runs button
        	else if(Touch.getButtonTouched(Gdx.input.getX(), Gdx.input.getY(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 52.6, 95.5, 55.5, 74))
    			game.setScreen(new BestRunsScreen(game, this));
        	// exit game button
        	else if(Touch.getButtonTouched(Gdx.input.getX(), Gdx.input.getY(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 52.6, 95.5, 78.7, 96.8))
    			Gdx.app.exit();       	
        }

		@Override
		public void resize(int width, int height) {
			// TODO Auto-generated method stub		
		}

		@Override
		public void show() {
			// TODO Auto-generated method stub
			background = new Texture(Gdx.files.internal(Assets.MENU_MAIN));
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


        //...Rest of class omitted for succinctness.

}
