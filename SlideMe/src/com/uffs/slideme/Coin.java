package com.uffs.slideme;

import org.flixel.FlxSprite;

public class Coin extends FlxSprite
{
	protected String _imgCoin = "coin.png";
	
	public Coin()
	{
		super();
		
		// I like to move it, move it
		loadGraphic(_imgCoin, true, true, 32);
		addAnimation("idle", new int[]{0, 1, 2, 3, 4, 5, 6, 7}, 16, true);
		play("idle");
		
		// We will be using as a FlxGroup. It will handle the Coin reappearing.
		kill();
	}
}
