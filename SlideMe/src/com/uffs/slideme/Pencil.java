package com.uffs.slideme;

import org.flixel.*;

public class Pencil extends FlxSprite {
	
	private float velocidade = 200;
	private static int number = 0;
	
	public Pencil(){
		number++;
		FlxG.log(number);
		
		loadGraphic("pencil.png", false, true, 45, 24);
		scale = new FlxPoint(0.5f, 0.5f);
		setOriginToCorner();
		centerOffsets();
				
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
		setFacing(direction);
		
		switch (getFacing()) {
			case RIGHT: velocity.x = velocidade; break;
			case LEFT: velocity.x = -velocidade; break;
		}
		
	}
}
