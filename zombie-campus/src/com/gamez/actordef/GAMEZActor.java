package com.gamez.actordef;

import com.badlogic.gdx.math.Vector2;
import com.sun.xml.internal.ws.wsdl.writer.document.soap12.Body;

/**
 * @interface GAMEZActor
 * 
 */

public interface GAMEZActor
{	
	/**
	 * @method move
	 * @param Vector2 dir (direction)
	 * @returns void
	 * This function will be given a force represented as a Vector2 and will translate the GAMEZActor using forces
	 */
	public void move(Vector2 dir);
	
	/**
	 * @method equipTool
	 * @param GAMEZTool tool
	 * @returns void
	 * This function will be given the GAMEZTool object (or implementing object type)
	 */
	public void equipTool(GAMEZTool tool);
	
	/**
	 * @method getPosition
	 * @returns Vector2 position
	 * This function returns a Vector2 representing the GAMEZActor's current position.
	 */
	public Vector2 getPosition();
	
	/**
	 * @method getTools
	 * @returns GAMEZTool[] tools
	 * This function returns an array of GAMEZTools representing all items "equipped" to the GAMEZActor
	 */
	public GAMEZTool[] getTools();
	
	/**
	 * @method getBody
	 * @returns Body body
	 * This function returns the physics body of the GAMEZActor
	 */
	public Body getBody();
}
