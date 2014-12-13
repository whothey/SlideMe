/**
 * Mob is an extension of FlxSprite and have the addons:
 * > Life Handling (from 0 to an Maximum)
 */

package com.uffs.slideme;

import org.flixel.FlxSprite;

public class Mob extends FlxSprite
{
	protected int maxLife = 100;
	protected int life = 100;
	
	public Mob (int x, int y, int maximumLife, int currentLife)
	{
		super(x, y);
		maxLife = maximumLife;
		life = currentLife;
	}
	
	public Mob (int x, int y, int maximumLife)
	{
		this(x, y, maximumLife, maximumLife);
	}
	
	public Mob (int x, int y) 
	{
		this(x, y, 100, 100);
	}
	
	// Life handling
	public int getLife() { return life; }
	
	public void setLife(int l) {
		life = l;
		if (life > maxLife) life = maxLife;
		if (life < 0 ) life = 0;
	}
	
	public void reduceLife(int l) {
		life -= l;
		if (life < 0) life = 0;
	}

	public void boostLife(int l) {
		life += l;
		if (life > maxLife) life = maxLife;
	}
	
	public int getMaxLife() { return maxLife; }
	
	public void setMaxLife(int m) {
		maxLife = m;
		if (maxLife < 0) maxLife = 0;
	}
}
