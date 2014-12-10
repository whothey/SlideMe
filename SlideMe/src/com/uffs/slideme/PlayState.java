package com.uffs.slideme;

import org.flixel.FlxG;
import org.flixel.FlxSprite;
import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.ui.FlxVirtualPad;

public class PlayState extends FlxState
{
	private FlxVirtualPad _pad;
	private Fumiko player;
	private FlxSprite box;
	
	@Override
	public void create()
	{
		_pad = new FlxVirtualPad(FlxVirtualPad.LEFT_RIGHT, FlxVirtualPad.A_B);
		_pad.setAlpha((float) 0.5);
		
		player = new Fumiko(0, 0, _pad);
		
		box = new FlxSprite(0, FlxG.height - 30);
		box.makeGraphic(400, 30);
		box.immovable = true;
		
		add(box);
		add(player);
		add(_pad);
	}
	
	@Override
	public void update()
	{
		super.update();
		
		FlxG.collide(player, box);
	}
}