package com.uffs.slideme;

import org.flixel.FlxG;
import org.flixel.FlxState;
import org.flixel.FlxText;
import com.uffs.slideme.MenuState;

public class GameOver extends FlxState {

	private FlxText gameOver, message, score; // bScore, lScore;
	private float time = 0;
	private int score2 = 0;
		
	@Override
	public void create() {
		/* Doesn't work
		// Getting the last score saved
		lastScore = FlxG.scores.get(0);
		// Saving this score to the last score
		FlxG.scores.set(0, FlxG.score);
		// Setting the best score
		if (FlxG.scores.get(1) < FlxG.score){
			FlxG.scores.set(1, FlxG.score);
		}
	
		if (FlxG.saves.size > 0){ 
			FlxG.saves.get(0).data.put("scores", FlxG.scores);
			FlxG.saves.get(0).flush();
		}
		*/
		
		gameOver = new FlxText(FlxG.width/4, FlxG.height/4, FlxG.width/2, "GAME OVER!");
		gameOver.setSize(50);
		gameOver.setAlignment("center");
				
		message = new FlxText(FlxG.width/4, FlxG.height/4, FlxG.width/2);
		message.setSize(20);
		message.setAlignment("center");
		
		/*
		lScore = new FlxText(0, FlxG.height/2, FlxG.width/4);
		lScore.setSize(20);
		lScore.setAlignment("center");
		
		bScore = new FlxText(FlxG.width - FlxG.width/4, FlxG.height/2, FlxG.width/4);
		bScore.setSize(20);
		bScore.setAlignment("center");
		*/
		
		score = new FlxText(FlxG.width/4, FlxG.height/2,  FlxG.width/2);
		score.setSize(30);
		score.setAlignment("center");
		
		add(gameOver);
		add(message);
		add(score);
	}
	
	@Override
	public void update(){
		time += FlxG.elapsed;
		FlxG.log(time);
		
		if (time > 2){
			gameOver.kill();
			message.setText("YOUR SCORE:");
		}
		if (time > 3){
			score.setText(score2+"");
			if (score2 < FlxG.score){
				score2 += 1;
			}else if (score2 >= FlxG.score){
				score.setText(FlxG.score+"");
				score.setSize(40);
				if (FlxG.mouse.pressed()){
					returnMenu();
				}
			}
		}
	}
	
	private void returnMenu(){
		FlxG.switchState(new MenuState());
	}
}
