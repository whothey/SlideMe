package com.uffs.slideme;

import org.flixel.*;
import java.util.Random;
import org.flixel.event.IFlxCollision;
import org.flixel.ui.FlxVirtualPad;



public class PlayState extends FlxState
{
	private FlxVirtualPad _pad;
	private FlxGroup _coins, bullets, hud, lives;
	private Fumiko player;
	private FlxSprite box, fumikoHud, lifeBar, background;
	private FlxText score;
	private FlxGroup _level;
	private String _terrain = "terrain.png";
	
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
		
		_level = new FlxGroup();
		generateLevel();
		_level.setAll("immovable", true);
		
		// Setup Camera and Screen Following
		FlxG.camera.follow(player);
		// TOP_DOWN without "x" scrolling 
		FlxG.camera.deadzone = new FlxRect(0, FlxG.height / 2, FlxG.width, 0);
		
		bullets = new FlxGroup();
		for (i=0; i<20; i++){
			bullets.add(new Pencil());
		}
		
		background = new FlxSprite(0,0).loadGraphic("background.png");
		background.scrollFactor = new FlxPoint(0,0);
		
		// Setup HUD
		hud = new FlxGroup();
		fumikoHud = new FlxSprite(2,2).loadGraphic("fumiko_hud.png");
		fumikoHud.scale = new FlxPoint(2,2);
		fumikoHud.setOriginToCorner();
		fumikoHud.centerOffsets();
		
		
		for (i = 0; i<player.getMaxLives(); i++){
			// hud.add(new Heart((fumikoHud.width+2)*2 + (13*i), 3));
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
		// add(box);
		add(_coins);
		add(player);
		add(_level);
		add(hud);
				
		// Add the pad for last, so it will be in the top-most layer
		add(_pad);
	}

	@Override
	public void update()
	{
		super.update();
		
		FlxG.collide(player, box);
		FlxG.collide(player, _level);
		
		// Collecting Coins
		FlxG.overlap(_coins, player, new IFlxCollision()
		{
			@Override
			public void callback(FlxObject coin, FlxObject player) {
				if (coin instanceof Coin && player instanceof Fumiko) {
					FlxG.score ++;
					coin.kill();
				}
			}
		});
		
		// Hud stuff
		score.setText(FlxG.score + "0");
		
	}
	
	public FlxGroup getBullet(){
		return bullets;
	}
	
	protected void generateLevel()
	{
		generateStepLine(0, FlxG.height, 2);
	}
	
	/**
	 * We can put up to 12 tiles of step, so we need to generate where from these 12 tiles
	 * will have blank spaces
	 * 
	 * @param x
	 * @param y
	 */
	protected void generateStepLine(int x, int y, int emptySpaces)
	{
		Random rnd = new Random();
		int[] emptyPlaces = new int[emptySpaces];
		
		for (int i = 0; i < emptySpaces; i++)
			emptyPlaces[i] = rnd.nextInt(12);
		
		for (int i = 0; i < 12; i++) {
			boolean isEmptySpace = false;
			
			for (int j = 0; j < emptyPlaces.length; j++) {
				if (emptyPlaces[j] == i) isEmptySpace = true;
			}
			
			if (isEmptySpace) continue;
			
			FlxTileblock t = new FlxTileblock(x + (i * 32), y, 32, 32);
			t.loadTiles(_terrain);
			_level.add(t);
			
		}
		
	}
}