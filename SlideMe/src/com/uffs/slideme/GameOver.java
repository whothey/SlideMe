package com.uffs.slideme;

import org.flixel.FlxButton;
import org.flixel.FlxG;
import org.flixel.FlxState;
import org.flixel.FlxText;
import org.flixel.event.IFlxButton;

public class GameOver extends FlxState {

	private FlxText gameOver, message, score;
	private float time = 0, score2 = 0;
	private FlxButton btnMenu;
	
	@Override
	public void create() {
		gameOver = new FlxText(FlxG.width/4, FlxG.height/4, FlxG.width/2, "GAME OVER!");
		gameOver.setSize(50);
		gameOver.setAlignment("center");
				
		message = new FlxText(FlxG.width/4, FlxG.height/4, FlxG.width/2);
		message.setSize(20);
		message.setAlignment("center");
		
		score = new FlxText(FlxG.width/4, FlxG.height/2,  FlxG.width/2);
		score.setSize(30);
		score.setAlignment("center");
		
		btnMenu = new FlxButton(5,5,"Menu", new IFlxButton(){@Override public void callback(){ClickMenu();}});
		btnMenu.label.setAlignment("center");
				
		add(gameOver);
		add(message);
		add(score);
		add(btnMenu);
		
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
				score2 += FlxG.elapsed * 10;
			}else if (score2 >= FlxG.score){
				score.setText(FlxG.score+"");
				score.setSize(40);
				if (FlxG.mouse.pressed()){
					ClickMenu();
				}
			}
		}
	}
	
	private void ClickMenu(){
		FlxG.switchState(new MenuState());
	}
}
