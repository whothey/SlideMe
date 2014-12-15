package com.uffs.slideme;

import org.flixel.*;
import org.flixel.event.IFlxCollision;
import org.flixel.ui.FlxVirtualPad;



public class PlayState extends FlxState
{
	private FlxVirtualPad _pad;
	private FlxGroup _coins, bullets, hud, lives;
	private Fumiko player;
	private FlxSprite box, fumikoHud, lifeBar;
	private FlxText score;
	
	@Override
	public void create()
	{
		int i;
		
		// Setup Stuff
		_pad = new FlxVirtualPad(FlxVirtualPad.LEFT_RIGHT, FlxVirtualPad.A_B);
		_pad.setAlpha((float) 0.5);
		
		player = new Fumiko(0, 0, _pad);
		player.setMaxLives(3);
			
		box = new FlxSprite(0, FlxG.height - 30);
		box.makeGraphic(400, 30);
		box.immovable = true;
		
		_coins = new FlxGroup();
		Coin coin = (Coin) _coins.recycle(Coin.class);
		coin.reset(100, FlxG.height - 80);
		
		// Setup Camera and Screen Following
		FlxG.camera.follow(player);
		FlxG.camera.deadzone = new FlxRect(FlxG.width / 2, FlxG.height / 2, 0, 0);
		
		bullets = new FlxGroup();
		for (i=0; i<20; i++){
			bullets.add(new Pencil());
		}
		
		// Setup HUD
		hud = new FlxGroup();
		fumikoHud = new FlxSprite(2,2).loadGraphic("fumiko_hud.png");
		fumikoHud.scale = new FlxPoint(2,2);
		fumikoHud.setOriginToCorner();
		fumikoHud.centerOffsets();
		
		
		for (i = 0; i<player.getMaxLives(); i++){
//			hud.add(new Heart(fumikoHud.width+2 + (13*i), 3));
		}
		
		lifeBar = new FlxSprite((fumikoHud.width+2)*2, 20).makeGraphic(50, 10, 0xffff0000);
		
		score = new FlxText(FlxG.width/4,0, FlxG.width/2);
		score.setSize(20);
		score.setAlignment("center");
		
		hud.add(lifeBar);
		hud.add(lives);
		hud.add(fumikoHud);
		hud.add(score);
		hud.setAll("scrollFactor", new FlxPoint(0,0));
				
		add(bullets);
		add(box);
		add(_coins);
		add(player);
		add(hud);
				
		// Add the pad for last, so it will be in the top-most layer
		add(_pad);
	}

	@Override
	public void update()
	{
		super.update();
		
		FlxG.collide(player, box);
		
		// Collecting Coins
		FlxG.overlap(_coins, player, new IFlxCollision()
		{
			@Override
			public void callback(FlxObject coin, FlxObject player) {
				FlxG.score ++;
				coin.kill();
			}
		});
		
		// Hud stuff
		score.setText(FlxG.score + "0");
		
	}
	
	public FlxGroup getBullet(){
		return bullets;
	}
}