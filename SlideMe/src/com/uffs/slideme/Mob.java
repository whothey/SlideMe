/**
 * Mob is an extension of FlxSprite and have the addons:
 * > Lives Handling (from 0 to an Maximum)
 * > Health Handling (from 0 to an Maximum)
 */

package com.uffs.slideme;

import org.flixel.FlxSprite;

public class Mob extends FlxSprite
{
	protected int maxHealth = 100;
	protected int health    = 100;
	protected int lives     = 1;
	protected int maxLives  = 1;
	
	public Mob(int x, int y, int maximumHealth, int currentHealth)
	{
		super(x, y);
		maxHealth = maximumHealth;
		health = currentHealth;
	}
	
	public Mob (int x, int y, int maximumHealth)
	{
		this(x, y, maximumHealth, maximumHealth);
	}
	
	public Mob (int x, int y) 
	{
		this(x, y, 100, 100);
	}
	
	// Health handling
	public int getHealth() { return health; }
	
	public void setHealth(int l) {
		health = l;
		if (health > maxHealth) health = maxHealth;
		if (health < 0 ) health = 0;
	}
	
	public void reduceHealth(int l) {
		health -= l;
		if (health < 0) health = 0;
	}

	public void boostHealth(int l) {
		health += l;
		if (health > maxHealth) health = maxHealth;
	}
	
	public int getMaxHealth() { return maxHealth; }
	
	public void setMaxHealth(int m) {
		maxHealth = m;
		if (maxHealth < 0) maxHealth = 0;
	}
	
	// Lives handling
	public int getMaxLives() { return maxLives; }
	
	public void setMaxLives(int m)
	{
		maxLives = m;
		if (maxLives < 0) maxLives = 0;
	}
	
	public void setLives(int l) {
		lives = l;
		if (lives > maxLives) lives = maxLives;
		if (lives < 0 ) lives = 0;
	}
	
	public void reduceLives(int l) {
		lives -= l;
		if (lives < 0) lives = 0;
	}

	public void boostLives(int l) {
		lives += l;
		if (lives> maxLives) lives = maxLives;
	}
	
	public int getLives() { return lives; }
}
