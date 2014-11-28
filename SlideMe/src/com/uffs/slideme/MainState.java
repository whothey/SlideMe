package com.uffs.slideme;

import org.flixel.FlxG;
import org.flixel.FlxState;
import org.flixel.FlxText;

public class MainState extends FlxState 
{
	private FlxText logo;
	
	@Override
	public void create()
	{
		logo = new FlxText(0, 0, 200, "SlideMe");
		logo.setSize(20);
		logo.setAlignment("center");
		logo.x = FlxG.width / 2 - logo.width / 2;
		logo.y = FlxG.height / 2 - logo.height / 2;
		// logo.setAlpha(0); // Transparent
		
		FlxG.fade(0x000000, 3);
		
		add(logo);
	}
}
