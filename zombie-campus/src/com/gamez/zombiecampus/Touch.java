package com.gamez.zombiecampus;

public class Touch {
	
	/**
	 * Given the left, right, top, and bottom bounds (percentage of screen) of a button, will
	 * return whether the button was touched or not 
	 * 
	 * @param x : x coordinate of touch
	 * @param y : y coordinate of touch
	 * @param screenWidth : current screen width
	 * @param screenHeight : current screen height
	 * @param left : left percentage bounds of button
	 * @param right : right percentage bounds of button
	 * @param top : top percentage bounds of button
	 * @param bottom : bottom percentage bounds of button
	 * @return true if button pressed, false if button NOT pressed
	 */
	public static boolean getButtonTouched(int x, int y, float screenWidth, float screenHeight, double left, double right, double top, double bottom)
	{
		float xPerc = (float)((x/(screenWidth)) * 100.00);
		float yPerc = (float)((y/(screenHeight)) * 100.00);		
		
		if(left <= xPerc && xPerc <= right && top <= yPerc && yPerc <= bottom)
			return true;
		
		return false;
	}

}
