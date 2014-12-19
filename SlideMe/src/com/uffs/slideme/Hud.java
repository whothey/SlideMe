package com.uffs.slideme;

import org.flixel.FlxG;
import org.flixel.FlxPoint;
import org.flixel.FlxSprite;
import org.flixel.FlxGroup;
import org.flixel.FlxText;

public class Hud extends FlxGroup {
	
	private FlxSprite fumikoHud, healthBar;
	private FlxText score;
	private Heart[] hearts;
	private Fumiko player;
	
	public Hud(Fumiko player){
		
		this.player = player;
		
		// Image of Fumiko set on Right-top corner
		fumikoHud = new FlxSprite(2,2).loadGraphic("fumiko_hud.png");
		
		// Images of Hearts that represents the players's lives
		hearts = new Heart[player.getMaxLives()];
		for (int i=0; i<player.getMaxLives(); i++){
			hearts[i] = new Heart(fumikoHud.width+7 + 13*i,5);
			add(hearts[i]);
		}
		
		// Player's health bar
		healthBar = new FlxSprite(fumikoHud.width + 7, 13+10);
				
		// Score
		score = new FlxText(FlxG.width/4, 0, FlxG.width/2);
		score.setSize(20);
		score.setAlignment("center");
		
		add(healthBar);
		add(fumikoHud);
		add(score);
		setAll("scrollFactor", new FlxPoint(0,0));
	}
	
	public void updateHud(){
		// Update the score points shown
				score.setText(FlxG.score + "");
		
		// Update the health bar's size 
		healthBar.makeGraphic(player.getHealth()+1, 7, 0xffff0000);
				
		// Update the heart's image
		for (int i=player.getMaxLives(); player.getLives() < i; i--){
			hearts[i-1].reduceLive();
		}
	}
}
