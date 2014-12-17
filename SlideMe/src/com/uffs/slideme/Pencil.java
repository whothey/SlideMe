package com.uffs.slideme;

import org.flixel.*;

public class Pencil extends FlxSprite {
	
	private float velocidade = 200;
	
	public Pencil(){

		loadGraphic("pencil.png", false, true, 18, 4);
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