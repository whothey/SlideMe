package com.uffs.slideme;

import org.flixel.FlxSprite;

public class Heart extends FlxSprite {
	
	private String state;
	
	public Heart(float posX, float posY){
		super(posX, posY);
		loadGraphic("heart.png", true, false, 13, 11);
		addAnimation("full", new int[]{0});
		addAnimation("half", new int[]{1});
		addAnimation("empty", new int[]{2});
	}
	
	public String getState(){ return state; };
	
	public void reduceLives(){
		if (state == "full")
			state = "half";
		else if (state == "half")
			state = "empty";
	}
	
	@Override
	public void update(){
		super.update();
		play(state);
	}
}
