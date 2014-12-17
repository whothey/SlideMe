package com.uffs.slideme;

import org.flixel.*;
import org.flixel.event.IFlxCollision;
import org.flixel.ui.FlxVirtualPad;

public class PlayState extends FlxState
{
	private FlxVirtualPad _pad;
	private FlxGroup _coins, bullets;
	private Fumiko player;
	private FlxSprite box, backGround;
	private Hud hud;
	private Ant enemy;
	
	
	@Override
	public void create()
	{
		int i;
		
		// Remove after
		enemy = new Ant(3,0);
		
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
		
		backGround = new FlxSprite(0,0).loadGraphic("background.png");
		backGround.scale = new FlxPoint(2,2);
		backGround.setOriginToCorner();
		backGround.scrollFactor = new FlxPoint(0,0);
		
		// Setup HUD
		hud = new Hud();
		
		add(backGround);
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
		
		// Colliding with Enemy
		if( FlxG.collide(player, enemy) && !player.getFlickering()){
			fumikoHurt(20);
		}
	}
	
	public FlxGroup getBullet(){ return bullets; }
	
	public Fumiko getPlayer(){ return player; }
	
	private void fumikoHurt(int damage){
		player.hurt(damage);
		
		if (player.getHealth() == 0 ){
			player.kill();
			player.reset(player.x, player.y);
			player.flicker(2);
						
			if (player.getLives() == 1)
				gameOver();
			else
				player.reduceLives(1);
		}
	}
	
	private void gameOver(){
		
	}
	
	public void generateLevel()
	{
		
	}
}