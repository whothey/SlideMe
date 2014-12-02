package com.uffs.slideme;

import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.ui.FlxVirtualPad;

public class PlayState extends FlxState
{
	private FlxVirtualPad _pad;
	private Fumiko player;
	
	@Override
	public void create()
	{
		_pad = new FlxVirtualPad(FlxVirtualPad.DPAD_FULL, FlxVirtualPad.A_B);
		player = new Fumiko(0, 0, _pad);
		
		add(player);
		add(_pad);
		_pad.setAlpha((float) 0.5);
	}
}