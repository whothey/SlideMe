package com.uffs.slideme;

import org.flixel.*;

public class Pencil extends FlxSprite {
	
	private float velocidade = 200;
	private static int number = 0;
	
	public Pencil(){
		number++;
		FlxG.log(number);
		
		loadGraphic("pencil_sheet.png", true, false, 45, 24);
				
		addAnimation("right", new int[] {0});
		addAnimation("left", new int[] {1});
		kill();
	}
	
	@Override
	public void update(){
		super.update();
		
		if (!onScreen()){
			kill();
		}
	}
		
	public void shoot(FlxPoint point, int direction){
		super.reset(point.x, point.y);
		setSolid(true);
		
		switch (direction) {
			case RIGHT: play("right"); velocity.x = velocidade; break;
			case LEFT: play("left"); velocity.x = -velocidade; break;
		}
		
	}
}
