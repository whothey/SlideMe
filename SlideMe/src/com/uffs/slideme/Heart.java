package com.uffs.slideme;

import org.flixel.FlxSprite;

public class Heart extends FlxSprite {
	
	private String state;
	
	public Heart(float posX, float posY){
		super(posX, posY);
		loadGraphic("heart.png", true, false, 13, 11);
		addAnimation("full", new int[]{0});
		// Lives will not be represented by a half heart
		// addAnimation("half", new int[]{1});
		addAnimation("empty", new int[]{2});
		state = "full";
	}
	
	public String getState(){ return state; };
	
	public void reduceLive(){
		if (state == "full")
			// state = "half";
		// else if (state == "half")
			state = "empty";
	}
	
	@Override
	public void update(){
		super.update();
		play(state);
	}
}
