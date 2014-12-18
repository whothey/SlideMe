package com.uffs.slideme;

import org.flixel.FlxG;
import org.flixel.FlxPoint;
import org.flixel.FlxSprite;
import org.flixel.FlxGroup;
import org.flixel.FlxText;

public class Hud extends FlxGroup {
	
	private FlxSprite fumikoHud, healthBar;
	private FlxText score;
	private int lifes, lifesBefore;
	private float playerHealth;
	private Heart[] lives;
	
	public Hud(){
		// Image of Fumiko and setting on Right-top corner
		fumikoHud = new FlxSprite(2,2).loadGraphic("fumiko_hud.png");
		
		// Images of Hearts who represents the players's lives
		lifes = (int)((PlayState)FlxG.getState()).getPlayer().getMaxLives();
		lifesBefore = lifes;
		lives = new Heart[lifes];
		for (int i=0; i<lifes; i++){
			lives[i] = new Heart(fumikoHud.width+2 + 13*i,2);
			add(lives[i]);
		}
		
		// Bar to show the player's health
		playerHealth = (int)((PlayState)FlxG.getState()).getPlayer().getHealth();
		healthBar = new FlxSprite(fumikoHud.width + 2, 13+2);
				
		// Text to show the score
		score = new FlxText(FlxG.width/4, 0, FlxG.width/2);
		score.setSize(20);
		score.setAlignment("center");
		
		add(healthBar);
		add(fumikoHud);
		add(score);
		setAll("scrollFactor", new FlxPoint(0,0));
	}
	
	@Override
	public void update(){
		super.update();
		
		// Update the health bar's size 
		healthBar.makeGraphic((int)playerHealth, 7, 0xffff0000);
		
		// Update the score points shown
		score.setText(FlxG.score + "");
		
		// Update the heart's image
		lifes = (int)((PlayState)FlxG.getState()).getPlayer().getLives();
		for (int i=lifesBefore-1; lifes < i; i--){
			lives[i].reduceLives();
		}
	}
}
