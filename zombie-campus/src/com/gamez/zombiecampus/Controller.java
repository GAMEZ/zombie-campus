package com.gamez.zombiecampus;

import com.gamez.actordef.GAMEZPlayer;
import com.badlogic.gdx.graphics.Pixmap;

public class Controller extends Actor{
	
    Player player;

    public Controller(GAMEZPlayer player)
    {
	this.player = player;
    }

    //call this function during PlayScreen render
    public void checkButtons(){
    // Check location of user touch input
    // jump button
   if(Touch.getButtonTouched(Gdx.input.getX(), Gdx.input.getY(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 100, 50, 25))
      player.move(0, 5);
   // fire button
   else if(Touch.getButtonTouched(Gdx.input.getX(), Gdx.input.getY(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 51, 0, 100, 75))
      player.getWeapon().fire();
   // pause button
   else if(Touch.getButtonTouched(Gdx.input.getX(), Gdx.input.getY(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 100, 100, 25))
      System.out.println("Pause everything here")
    }
}
