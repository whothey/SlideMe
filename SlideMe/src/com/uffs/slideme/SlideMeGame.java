package com.uffs.slideme;

import org.flixel.FlxGame;
import org.flixel.FlxG;

public class SlideMeGame extends FlxGame
{
	public SlideMeGame()
	{
		super(240, 400, MainState.class, 2, 50, 50, false);
		FlxG.debug = true;
	}
}
