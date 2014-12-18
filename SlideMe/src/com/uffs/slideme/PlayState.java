package com.uffs.slideme;

import org.flixel.*;

import java.util.Random;
import org.flixel.event.IFlxCollision;
import org.flixel.ui.FlxVirtualPad;

public class PlayState extends FlxState
{
	private FlxVirtualPad _pad;
	private FlxGroup _coins, bullets, lives, _enemies;
	private Fumiko player;
	private FlxSprite fumikoHud, lifeBar, background;
	private FlxText score;
	private FlxSprite backGround;
	private Hud hud;
	private FlxGroup _level;
	private String _terrain = "terrain.png";
	
	// Level generating vars
	private int _lastGeneratedY;
	private int _lastGeneratedX;
	private int _lastBlockIteration;
	private int [] _nextBlankSpaces;
	
	@Override
	public void create()
	{
		int i;

		// Starts at bottom of the screen
		_lastGeneratedY = FlxG.height;
		_lastGeneratedX = 0;
		_nextBlankSpaces  = new int[] {128}; 
		
		// Setup Stuff
		_pad = new FlxVirtualPad(FlxVirtualPad.LEFT_RIGHT, FlxVirtualPad.A_B);
		_pad.setAlpha((float) 0.5);
		
		player = new Fumiko(0, 0, _pad);
		player.setMaxLives(3);
		
		// Generate level tiles, during update they will be placed in proper places
		_level = new FlxGroup(60);
		for (i = 0; i < 60; i++) {
			FlxTileblock t = new FlxTileblock(0, 0, 32, 32);
			t.loadTiles(_terrain);
			t.kill();
			_level.add(t);
		}
		
		_coins = new FlxGroup(30);
		
		// Setup Camera and Screen Following
		FlxG.camera.follow(player);
		// TOP_DOWN without "x" scrolling 
		FlxG.camera.deadzone = new FlxRect(-FlxG.width, 0, FlxG.width * 4, FlxG.height / 2);
		
		bullets = new FlxGroup();
		for (i=0; i<20; i++){
			bullets.add(new Pencil());
		}
		
		background = new FlxSprite(0,0).loadGraphic("background.png");
		background.scrollFactor = new FlxPoint(0,0);
		
		// Setup HUD
		hud = new Hud();
		
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

		add(backGround);
		add(bullets);
		add(_coins);
		add(_level);
		add(player);
		add(hud);
				
		// Add the pad for last, so it will be in the top-most layer
		add(_pad);
	}

	@Override
	public void update()
	{
		super.update();
		
		// Level generation
		killPassedBlocks();
		if (player.getScreenXY().y > FlxG.height / 4 && _level.countDead() > 0) {
			generateBlock(); // Generate a new block
			generateCoin(); // Generate random coins
		}		
		
		FlxG.collide(_level, player);
		FlxG.collide(_level, bullets, new IFlxCollision()
		{	
			@Override
			public void callback(FlxObject levelTile, FlxObject bullet)
			{
				bullet.kill();
			}
		});
		
		// Collecting Coins
		FlxG.overlap(_coins, player, new IFlxCollision()
		{
			@Override
			public void callback(FlxObject coin, FlxObject player)
			{
				if (coin instanceof Coin && player instanceof Fumiko) {
					FlxG.score ++;
					coin.kill();
				}
			}
		});		
		
		// Hud stuff
		score.setText(FlxG.score + "0");
		// Colliding with Enemy
		if( FlxG.collide(player, _enemies) && !player.getFlickering()){
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
	
	/**
	 * Iterates over update() and checks if a block is out of screen from the top, if so, kill it.
	 * We will be incrementing an iterator helper named '_lastBlockIteration' for each update until
	 * '_level' group reach its maximum.
	 * This method is faster than calling update() for each block in group. 
	 */
	protected void killPassedBlocks()
	{	
		if (++_lastBlockIteration == _level.getMaxSize()) _lastBlockIteration = 0;
		
		FlxTileblock t = (FlxTileblock) _level.members.get(_lastBlockIteration);
		if (t instanceof FlxTileblock && (t.getScreenXY().y + t.height < 0)) {
			t.kill();
		}
	}
	
	/**
	 * Try to generate a new line with tiles, and only generates if has any block in '_level' that is 'killed'.
	 */
	protected void generateBlock()
	{
		boolean isBlankSpace = false, newLine = false;
		
		// Check if current X should be a blank space
		for (int i = 0; i < _nextBlankSpaces.length; i++) {
			if (_lastGeneratedX == _nextBlankSpaces[i]) isBlankSpace = true;
		}
		
		if (!isBlankSpace) {
			// Load an available tile
			FlxTileblock d = (FlxTileblock) _level.getFirstAvailable();
			
			if (d instanceof FlxTileblock) { // d can be null if none is available
				d.reset(_lastGeneratedX, _lastGeneratedY); // Put in place
				
				// If reached end of line
				if ((_lastGeneratedX + 32) >= FlxG.width) {
					newLine = true;
					
					// Generate new random blank spaces
					Random rnd = new Random();
					_nextBlankSpaces = new int[rnd.nextInt(3) + 1]; // New random blank spaces quantity
					
					// Blank spaces positions
					for (int i = 0; i < _nextBlankSpaces.length; i++) {
						_nextBlankSpaces[i] = (int) (rnd.nextInt(12) * d.width);
					}
				}
			}
		}
		
		_lastGeneratedX += 32; // Add an offset
		
		if (newLine) {
			_lastGeneratedY += 32 * 2; // Add offset to Y
			_lastGeneratedX = 0; // Reset X offset
			FlxG.worldBounds.height = _lastGeneratedY;
		}
	}
	
	/**
	 * Generate a coin over the tiles
	 */
	protected void generateCoin()
	{
		int workspaceY = _lastGeneratedY - 32;
		Random rnd = new Random();
		
		boolean placeCoin = rnd.nextInt(100) >= 90; // 10%
		
		if (placeCoin) {
			Coin coin = (Coin) _coins.recycle(Coin.class);
			coin.reset(_lastGeneratedX, workspaceY);
		}
	}
}
