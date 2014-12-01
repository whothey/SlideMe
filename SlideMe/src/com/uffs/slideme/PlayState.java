package com.uffs.slideme;

import org.flixel.FlxState;
import org.flixel.FlxText;

public class PlayState extends FlxState
{
	private Fumiko player;
	
	@Override
	public void create()
	{
		player = new Fumiko(0, 0);
		add(new FlxText(0, 0, 200, "Tiago have probleminhas"));
		add(player);
	}
}