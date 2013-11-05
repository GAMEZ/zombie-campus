package com.gamez.zombiecampus;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AndroidGame extends Game {
    
    SpriteBatch batch;
    BitmapFont font;

    public void create() {
    		Texture.setEnforcePotImages(false);
            batch = new SpriteBatch();
            //Use LibGDX's default Arial font.
            font = new BitmapFont();
            this.setScreen(new SplashScreen(this));
    }

    public void render() {
            super.render(); //important!
    }
    
    public void dispose() {
            batch.dispose();
            font.dispose();
    }
}