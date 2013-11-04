package com.gamez.zombiecampus;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

public class SplashScreen implements Screen
{
        private SpriteBatch spriteBatch;
        private AndroidGame game;
        OrthographicCamera camera;
        float alpha = 0.0f;
        private boolean fadein = true;
        private boolean fadeout = false;
        private boolean switchImage = false;
        private boolean launch = false;
        
        /**
         * Constructor for the splash screen
         * @param g Game which called this splash screen.
         */
        public SplashScreen(final AndroidGame g)
        {
                game = g;
                camera = new OrthographicCamera();
                camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

		@Override
		public void render(float delta) {
			// TODO Auto-generated method stub
			Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
            Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

            camera.update();
            game.batch.setProjectionMatrix(camera.combined);
            spriteBatch.begin();
            
            fadeScreens();
            
            
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, alpha);
            if(!switchImage)
            	spriteBatch.draw(new Texture(Gdx.files.internal(Assets.SPLASH_SCREEN)), 0, 0, camera.viewportWidth, camera.viewportHeight);
            else
            	spriteBatch.draw(new Texture(Gdx.files.internal(Assets.TITLE_SCREEN)), 0, 0, camera.viewportWidth, camera.viewportHeight);
            spriteBatch.end();
            		
		}
		
		// HANDLING OF FADE IN AND FADE OUT OF SPLASH AND TITLE SCREEN
		private void fadeScreens()
		{
            if(launch)
            	game.setScreen(new MainMenuScreen(game));
            else if(!switchImage && fadein)
            {
            	alpha = alpha + .075f;
            	if(alpha >= .95f)
            	{
            		fadeout = true;
            		fadein = false;
            	}
            }
            else if(!switchImage && fadeout)
            {
            	alpha = alpha - .075f;
            	if(alpha <= .05f)
            	{
            		switchImage = true;
            		fadeout = false;
            		fadein = true;
            	}
            }
            else if(switchImage && fadein)
            {
            	alpha = alpha + .075f;
            	if(alpha >= .95f)
            	{
            		fadeout = true;
            		fadein = false;
            	}
            }
            else if(switchImage && fadeout)
            {
            	alpha = alpha - .075f;
            	if(alpha <= .05f)
            	{
            		launch = true;
            	}
            }
		}

		@Override
		public void resize(int width, int height) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void show() {
			// TODO Auto-generated method stub
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