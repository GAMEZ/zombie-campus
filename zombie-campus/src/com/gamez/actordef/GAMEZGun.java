package com.gamez.actordef;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class GAMEZGun extends Actor implements GAMEZTool {
	
	private boolean uses_ammo;
	private int total_ammo, current_ammo, clip_size;

	public GAMEZGun(boolean useA, int clip, int c_ammo, int t_ammo)
	{
		uses_ammo = useA;
		total_ammo = t_ammo;
		current_ammo = c_ammo;
		clip_size = clip;
	}
	
	@Override
	public void use()
	{
		fire();
	}

	public void reload()
	{
		if (uses_ammo)
		{
			int diff = clip_size - current_ammo;
			if (diff != 0)//if we have ammo missing from clip
			{
				if (total_ammo - diff >= 0)//if we have enough ammo to reload
				{
				total_ammo -= diff;
				current_ammo += diff;
				}
				else//if we don't, add what's left
				{
					current_ammo += total_ammo;
					total_ammo = 0;
				}
			}
		}
	}
	
	public void fire()
	{
		if (!uses_ammo || current_ammo > 0)
		{
			System.out.println("gun goes boom!");
		}
		else
		{
			System.out.println("gun goes click!");
		}
	}
}
