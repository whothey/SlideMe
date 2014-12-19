package com.uffs.slideme;

import org.flixel.*;

import java.util.Random;
import org.flixel.event.IFlxCollision;
import org.flixel.ui.FlxVirtualPad;
import org.flixel.event.IFlxButton;

public class PlayState extends FlxState
{
	private FlxVirtualPad _pad;
	private FlxGroup _coins, bullets, _enemies;
	private Fumiko player;
	private FlxSprite background;
	private Hud hud;
	private FlxGroup _level;
	private String _terrain = "terrain.png";
	private FlxButton btnMenu;
		
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
		player.setMaxLives(4);
		player.setLives(player.getMaxLives());
			
		// Generate level tiles, during update they will be placed in proper places
		_level = new FlxGroup(60);
		for (i = 0; i < 60; i++) {
			FlxTileblock t = new FlxTileblock(0, 0, 32, 32);
			t.loadTiles(_terrain);
			t.kill();
			_level.add(t);
		}
		
		_coins = new FlxGroup(30);
		_enemies = new FlxGroup(30);
		
		// Setup Camera and Screen Following
		FlxG.camera.follow(player);
		// TOP_DOWN without "x" scrolling 
		FlxG.camera.deadzone = new FlxRect(-FlxG.width, 0, FlxG.width * 4, FlxG.height / 2);
		
		bullets = new FlxGroup();
		for (i=0; i<20; i++){
			bullets.add(new Pencil());
		}
		
		background = new FlxSprite(0,0).loadGraphic("background.png", false, false, FlxG.width, FlxG.height);
		background.scale = new FlxPoint(2,2);
		background.setOriginToCorner();
		
		// Setup HUD
		hud = new Hud(player);
		
		btnMenu = new FlxButton(0,5, "Menu", new IFlxButton(){ @Override public void callback(){ backMenu(); }});
		btnMenu.x = FlxG.width - btnMenu.width-5;
		btnMenu.scrollFactor = new FlxPoint(0,0);
				
		add(background);
		add(_coins);
		add(_enemies);
		add(bullets);
		add(_level);
		add(player);
		add(hud);
		add(btnMenu);
						
		// Add the pad for last, so it will be in the top-most layer
		add(_pad);
	}

	@Override
	public void update()
	{
		super.update();
		hud.updateHud();
		
		// Level generation
		killPassedBlocks();
		if (player.getScreenXY().y > FlxG.height / 4 && _level.countDead() > 0) {
			generateBlock(); // Generate a new block
			generateCoin(); // Generate random coins
			generateEnemies(); // Generate random enemies
		}		
		
		FlxG.collide(_level, player);
		FlxG.collide(_enemies, _level);
		
		// Don't let the bullet pass per ground 
		FlxG.overlap(_level, bullets, new IFlxCollision()
		{	
			@Override
			public void callback(FlxObject levelTile, FlxObject bullet)
			{
				bullet.kill();
			}
		});
		
		// Killing enemies
		FlxG.overlap(_enemies, bullets, new IFlxCollision()
		{
			@Override
			public void callback(FlxObject enemy, FlxObject bullet)
			{
				if (enemy instanceof Ant && bullet instanceof Pencil) {
					FlxG.score += 10;
					enemy.kill();
					bullet.kill();
				}
			}
		});
		
		// Collecting Coins
		FlxG.overlap(_coins, player, new IFlxCollision()
		{
			@Override
			public void callback(FlxObject coin, FlxObject player)
			{
				if (coin instanceof Coin && player instanceof Fumiko) {
					FlxG.score += 100;
					coin.kill();
				}
			}
		});		
		
		// Colliding with Enemy
		if(!player.getFlickering()){
			if (FlxG.collide(player, _enemies)){
                player.reduceHealth(10);
    			FlxG.score--;
            }
		}
		
		// Reducing Lives
		if (player.getHealth() == 0 ){
			player.reduceLives(1);
			player.kill();
			player.reset(player.x, player.y);
			player.setHealth(player.getMaxHealth());
			player.flicker(3);
			FlxG.vibrate(300);
		}
		
		if (player.getLives() == 0){
			player.kill();
			gameOver();
		}
	}
			
	public FlxGroup getBullet(){ return bullets; }
	
	public Fumiko getPlayer(){ return player; }
	
	private void gameOver(){
		FlxG.switchState(new GameOver());
	}
	
	private void backMenu(){
		FlxG.switchState(new MenuState());
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
	
	/**
	 * Generate a coin over the tiles
	 */
	protected void generateEnemies()
	{
		int workspaceY = _lastGeneratedY - 32;
		Random rnd = new Random();
		
		boolean placeEnemy = rnd.nextInt(100) >= 90; // 10%
		
		if (placeEnemy) {
			Ant enemy = (Ant) _enemies.recycle(Ant.class);
			enemy.reset(_lastGeneratedX, workspaceY);
		}
	}
}