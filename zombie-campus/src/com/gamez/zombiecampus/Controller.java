package com.gamez.zombiecampus;

import com.gamez.actordef.GAMEZPlayer;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Controller {
	
Button jumpB, fireB, pauseB;
	
    public Controller(GAMEZPlayer player, Skin uiSkin)
    {
	jumpB = new Button(uiSkin);
	fireB = new Button(uiSkin);
	pauseB = new Button(uiSkin);
    }
	
    public void setJumpButtonPos(float x, float y)
    {
	jumpB.setPosition(x, y);
    }
        
    public void setFireButtonPos(float x, float y)
    {
	fireB.setPosition(x, y);
    }
        
    public void setPauseButtonPos(float x, float y)
    {
        pauseB.setPosition(x, y);
    }
}
