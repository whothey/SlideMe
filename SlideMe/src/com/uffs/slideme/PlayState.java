package com.uffs.slideme;

import org.flixel.*;
import org.flixel.ui.FlxVirtualPad;

public class PlayState extends FlxState
{
	private FlxVirtualPad _pad;
	private Fumiko player;
	private FlxSprite box;
		
	private FlxGroup bullets;
	
	@Override
	public void create()
	{
		int i;
		
		_pad = new FlxVirtualPad(FlxVirtualPad.DPAD_FULL, FlxVirtualPad.A_B);
		_pad.setAlpha((float) 0.5);
		
		player = new Fumiko(0, 0, _pad);
			
		box = new FlxSprite(0, FlxG.height - 30);
		box.makeGraphic(400, 30);
		box.immovable = true;
		
		add(box);
		add(player);
		add(_pad);
		
		bullets = new FlxGroup();
		for (i=0; i<20; i++){
			bullets.add(new Pencil());
		}
		add(bullets);
	}
	
	@Override
	public void update()
	{
		super.update();
		
		FlxG.collide(player, box);
	}
	
	public FlxGroup getBullet(){
		return bullets;
	}
}