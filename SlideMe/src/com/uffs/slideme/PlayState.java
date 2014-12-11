package com.uffs.slideme;

import org.flixel.*;
import org.flixel.event.IFlxCollision;
import org.flixel.ui.FlxVirtualPad;

public class PlayState extends FlxState
{
	private FlxVirtualPad _pad;
	private FlxGroup _coins;
	private Fumiko player;
	private FlxSprite box;
	private FlxGroup _hud;
		
	private FlxGroup bullets;
	
	@Override
	public void create()
	{
		int i;
		
		// Setup Stuff
    	_pad = new FlxVirtualPad(FlxVirtualPad.LEFT_RIGHT, FlxVirtualPad.A_B);
		_pad.setAlpha((float) 0.5);
		
		player = new Fumiko(0, 0, _pad);
			
		box = new FlxSprite(0, FlxG.height - 30);
		box.makeGraphic(400, 30);
		box.immovable = true;
		
		_coins = new FlxGroup();
		Coin coin = (Coin) _coins.recycle(Coin.class);
		coin.reset(100, FlxG.height - 80);
		
		bullets = new FlxGroup();
		for (i=0; i<20; i++){
			bullets.add(new Pencil());
		}
		add(bullets);
		
		add(box);
		add(_coins);
		add(player);
		
		// Add the pad for last, so it will be in the top-most layer
		add(_pad);
	}
	
	@Override
	public void update()
	{
		super.update();
		
		FlxG.collide(player, box);
		
		// Collecting Coins
		FlxG.overlap(_coins, player, new IFlxCollision() {
			@Override
			public void callback(FlxObject coin, FlxObject player) {
				coin.kill();
			}
		});
	}
	
	public FlxGroup getBullet(){
		return bullets;
	}
}